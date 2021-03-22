package com.nettverksprog.stun.attribute;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Inet4Address;
import java.net.Inet6Address;


/**
 *   Attribute implementation representing a MAPPED-ADDRESS attribute as defined in RFC 5389.
 *
 *   The format of the MAPPED-ADDRESS attribute is:
 *
 *        0                   1                   2                   3
 *        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *       |0 0 0 0 0 0 0 0|    Family     |           Port                |
 *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *       |                                                               |
 *       |                 Address (32 bits or 128 bits)                 |
 *       |                                                               |
 *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */
public class MappedAddress implements Attribute {

    private static final int IPV4_LENGTH = 8; // TODO: byte?
    private static final int IPV6_LENGTH = 20; // TODO: byte?
    private static final int IPV4_FAMILY = 0x01;
    private static final int IPV6_FAMILY = 0x02;

    private InetSocketAddress address;
    private int length;
    private int addressFamily;

    public MappedAddress(InetSocketAddress address) {
        this.address = address;
        this.length = resolveLength(address);
        this.addressFamily = resolveAddressFamily(address);
    }

    private int resolveLength(InetSocketAddress address) {
        if (address.getAddress() instanceof Inet4Address)
            return IPV4_LENGTH;
        else if (address.getAddress() instanceof Inet6Address)
            return IPV6_LENGTH;

        throw new IllegalArgumentException("Unknown IP address class: " + address.getAddress().getClass());
    }

    private int resolveAddressFamily(InetSocketAddress address) {
        if (address.getAddress() instanceof Inet4Address)
            return IPV4_FAMILY;
        else if (address.getAddress() instanceof Inet6Address)
            return IPV6_FAMILY;

        throw new IllegalArgumentException("Unknown IP address class: " + address.getAddress().getClass());
    }

    @Override
    public AttributeType getType() {
        return AttributeType.MAPPED_ADDRESS;
    }

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(byteOut);

        int leadingZeroes = 0x00;
        dataOut.writeByte(leadingZeroes);
        dataOut.writeByte(addressFamily);
        dataOut.writeShort(address.getPort());
        dataOut.write(address.getAddress().getAddress());

        return byteOut.toByteArray();
    }
}
