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


@Slf4j
@Component
public class StunServer {

    static final int PORT = 3478;
    private final int SECONDARY_PORT = PORT + 1;
    private final InetSocketAddress primaryAddress;
    private final InetSocketAddress secondaryAddress;

    private List<StunServerThread> listeners;

    public StunServer() {
        this.primaryAddress = new InetSocketAddress("localhost", PORT);
        this.secondaryAddress = new InetSocketAddress("localhost", SECONDARY_PORT);
        this.listeners = new ArrayList<>();
    }

    public void start() throws SocketException {
        log.debug("Starting STUN server");
        listeners.add(new StunServerThread(primaryAddress));
        listeners.add(new StunServerThread(secondaryAddress));

        listeners.forEach(Thread::start);
    }

    private static class StunServerThread extends Thread {

        private final DatagramSocket receiverSocket;
        private DatagramPacket packet;

        private StunServerThread(InetSocketAddress ipAddress) throws SocketException {
            receiverSocket = new DatagramSocket(ipAddress);
            //receiverSocket.bind(ipAddress);
        }

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

        private Message receiveMessage() throws IOException {
            byte[] buffer = new byte[256];
            packet = new DatagramPacket(buffer, buffer.length);
            receiverSocket.receive(packet);
            return new MessageParser().parse(packet.getData());
        }

        private void handleMessage(Message receivedMessage) throws IOException {
            if (receivedMessage.isRequest() && receivedMessage.isBinding())
                handleBindingRequest(receivedMessage);
            else
                log.debug("Cannot handle message of method {}", receivedMessage.getMessageMethod());
        }

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

        private Message buildSuccessResponse(Message receivedMessage) {
            Attribute mappedAddress = getMappedAddressAttributeFromClient();

            MessageHeader messageHeader = new MessageHeader(MessageMethod.BINDING,
                                                            MessageClass.SUCCESS_RESPONSE,
                                                            receivedMessage.getMessageHeader().getTransactionId());

            return new Message(messageHeader, mappedAddress);
        }

        private Attribute getMappedAddressAttributeFromClient() {
            InetAddress destinationAddress = packet.getAddress();
            int destinationPort = packet.getPort();
            InetSocketAddress primaryAddress = new InetSocketAddress(destinationAddress, destinationPort);

            return new MappedAddress(primaryAddress);
        }


        private void close() {
            receiverSocket.close();
        }

        private boolean isClosed() {
            return receiverSocket.isClosed();
        }
    }

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

