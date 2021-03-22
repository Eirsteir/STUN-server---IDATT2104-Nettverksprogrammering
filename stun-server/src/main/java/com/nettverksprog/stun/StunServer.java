package com.nettverksprog.stun;

import com.nettverksprog.stun.attribute.MappedAddress;
import com.nettverksprog.stun.header.MessageClass;
import com.nettverksprog.stun.header.MessageHeader;
import com.nettverksprog.stun.header.MessageMethod;
import com.nettverksprog.stun.message.Message;
import com.nettverksprog.stun.attribute.Attribute;
import com.nettverksprog.stun.message.MessageParser;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to start he STUN server
 * The STUN server is created based on the configurations defined in RFC5389
 * https://tools.ietf.org/html/rfc5389#section-18.4
 */
@Slf4j
@Component
public class StunServer {

    static final int PORT = 3478; //port is set to 3478 which is used for STUN
    private final int SECONDARY_PORT = PORT + 1; //the secondary port is simply set with our primary port + 1
    private final InetSocketAddress primaryAddress;
    private final InetSocketAddress secondaryAddress;

    private List<StunServerThread> listeners;

    public StunServer() {
        //primary and secondary address are set with primary and secodary port
        this.primaryAddress = new InetSocketAddress("localhost", PORT);
        this.secondaryAddress = new InetSocketAddress("localhost", SECONDARY_PORT);
        this.listeners = new ArrayList<>();
    }

    /**
     * Start method that adds to new StunServerThreads to our listeners list
     * the two threads are created with the primary- and secondary InetSocketAddress respectively
     * @throws SocketException
     */
    public void start() throws SocketException {
        log.debug("Starting STUN server");
        listeners.add(new StunServerThread(primaryAddress));
        listeners.add(new StunServerThread(secondaryAddress));

        listeners.forEach(Thread::start); //start each server thread in the list
    }

    /**
     * StunServerThread class extending Thread
     * Containing methods for handling messages on the stun server
     */
    private static class StunServerThread extends Thread {

        private final DatagramSocket receiverSocket;
        private DatagramPacket packet;

        /**
         * Initializes a thread, given an address
         * Creates a new DatagramSocket with this address
         * @param ipAddress
         * @throws SocketException
         */
        private StunServerThread(InetSocketAddress ipAddress) throws SocketException {
            receiverSocket = new DatagramSocket(ipAddress);
            //receiverSocket.bind(ipAddress);
        }

        /**
         * Run the thread, catches IOException
         */
        @Override
        public void run() {
            while (true) { // receiverSocket.isConnected() && !receiverSocket.isClosed()
                try {
                    Message receivedMessage = receiveMessage();
                    log.debug("Received message at {}:{}, message: {}", receiverSocket.getLocalAddress(), receiverSocket.getLocalPort(), receivedMessage);
                    handleMessage(receivedMessage);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            //log.debug("Socket is closed");
        }

        /**
         * Receives a message with a DatagramPacket, with a buffer length of 256 bytes
         * Parses this method with with the MessageParser class
         * @return Parsed message
         * @throws IOException
         */
        private Message receiveMessage() throws IOException {
            byte[] buffer = new byte[256];
            packet = new DatagramPacket(buffer, buffer.length);
            receiverSocket.receive(packet);
            return new MessageParser().parse(packet.getData());
        }

        /**
         * Handles binding request if and only if the message class is a request, and message method is binding
         * The method checks this with isRequest() and isBinding() methods in Message class
         * @param receivedMessage
         * @throws IOException
         */
        private void handleMessage(Message receivedMessage) throws IOException {
            if (receivedMessage.isRequest() && receivedMessage.isBinding())
                handleBindingRequest(receivedMessage);
            else
                log.debug("Cannot handle message of method {}", receivedMessage.getMessageMethod());
        }

        /**
         * If our message is a binding request
         * Uses buildSuccessResponse, these bytes are used in a new Datagram packet
         * The Datagram packet consists of the length, address and port from our success response
         * This packet is then sent through our socket
         * @param receivedMessage
         * @throws IOException
         */
        private void handleBindingRequest(Message receivedMessage) throws IOException {
            log.debug("Handling request message of method {}", receivedMessage.getMessageMethod());

            Message message = buildSuccessResponse(receivedMessage);
            byte[] messageBytes = message.getBytes();

            packet = new DatagramPacket(messageBytes,
                    messageBytes.length,
                    packet.getAddress(),
                    packet.getPort());

            receiverSocket.send(packet);
        }

        /**
         * Builds a resonse with the transaction ID from the message received
         * The success response are given the BINDING method and SUCCESS_RESPONSE class
         * The message header and mapped address are used to create our success response
         * @param receivedMessage
         * @return Success response
         */
        private Message buildSuccessResponse(Message receivedMessage) {
            Attribute mappedAddress = getMappedAddressAttributeFromClient();

            MessageHeader messageHeader = new MessageHeader(MessageMethod.BINDING,
                    MessageClass.SUCCESS_RESPONSE,
                    receivedMessage.getMessageHeader().getTransactionId());

            return new Message(messageHeader, mappedAddress);
        }

        /**
         * Sets the destination address to that of the client
         * The destination port is set to that of the client
         * MappedAddress is a new InetSocketAddress with the clients address and port
         * @return MappedAddress
         */
        private Attribute getMappedAddressAttributeFromClient() {
            InetAddress destinationAddress = packet.getAddress();
            int destinationPort = packet.getPort();
            InetSocketAddress primaryAddress = new InetSocketAddress(destinationAddress, destinationPort);

            return new MappedAddress(primaryAddress);
        }

        /**
         * Closes the socket
         */
        private void close() {
            receiverSocket.close();
        }

        /**
         * checks if reciever socket is closed
         * @return if receiver socket is closed
         */
        private boolean isClosed() {
            return receiverSocket.isClosed();
        }
    }

    /**
     * Stop method that closes all running threads
     */
    @PreDestroy
    public void stop() {
        log.debug("Shutting down STUN server");
        for (StunServerThread thread : listeners) {
            if (thread.isClosed())
                return;

            thread.close();
        }
    }
}



