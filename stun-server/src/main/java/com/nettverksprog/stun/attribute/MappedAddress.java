package com.nettverksprog.stun.attribute;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;


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
public class MappedAddress extends Address {

    /**
     * Defines each part of the mapped address
     * @param address
     */
    public MappedAddress(InetSocketAddress address) {
        super(address);
    }

    @Override
    public AttributeType getType() {
        return AttributeType.MAPPED_ADDRESS;
    }

    /**
     * Method to return the mapped address bytes
     * @return byte array if the mapped address
     * @throws IOException
     */
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
