package com.nettverksprog.stun.attribute;

import com.nettverksprog.stun.message.Message;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class AttributeBuilder {

    private static List<Attribute> attributes;

    private AttributeBuilder(){
        attributes = new ArrayList<>();
    }

    public static AttributeBuilder builder() {
        return new AttributeBuilder();
    }

    public AttributeBuilder mappedAddress(DatagramPacket packet) {
        InetAddress destinationAddress = packet.getAddress();
        int destinationPort = packet.getPort();
        InetSocketAddress primaryAddress = new InetSocketAddress(destinationAddress, destinationPort);

        attributes.add(new MappedAddress(primaryAddress));
        return this;
    }

    /**
     * Sets the destination address to that of the client
     * The destination port is set to that of the client
     * MappedAddress is a new InetSocketAddress with the clients address and port
     * @return MappedAddress
     */
    public AttributeBuilder xorMappedAddress(DatagramPacket packet, Message message) {
        InetAddress destinationAddress = packet.getAddress();
        int destinationPort = packet.getPort();
        InetSocketAddress primaryAddress = new InetSocketAddress(destinationAddress, destinationPort);
        int transactionId = ByteBuffer.wrap(message.getMessageHeader().getTransactionId()).getInt();

        attributes.add(new XorMappedAddress(primaryAddress, transactionId));
        return this;
    }

    public List<Attribute> build() {
        return attributes;
    }

}
