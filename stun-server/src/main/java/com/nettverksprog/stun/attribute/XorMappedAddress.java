package com.nettverksprog.stun.attribute;

import com.nettverksprog.stun.header.MessageHeader;
import lombok.ToString;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;


/**
 *    The format of the XOR-MAPPED-ADDRESS is as follows according to RFC 5389:
 *
 *       0                   1                   2                   3
 *       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |x x x x x x x x|    Family     |         X-Port                |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |                X-Address (Variable)
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */
@ToString(callSuper = true)
public class XorMappedAddress extends Address {

    private int transactionId;

    public XorMappedAddress(InetSocketAddress address, int transactionId) {
        super(address);
        this.transactionId = transactionId;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.XOR_MAPPED_ADDRESS;
    }

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(byteOut);

        int leadingZeroes = 0x00;
        dataOut.writeByte(leadingZeroes);
        dataOut.writeByte(addressFamily);
        dataOut.writeShort(getXorPort());
        dataOut.writeInt(getXorAddress());

        return byteOut.toByteArray();
    }

    /**
     * Creates XorCookie with the address' ports first 16 significant bits
     * @return port xor xorCookie
     */
    private int getXorPort() {
        int shift16bits = 0x10;
        int mostSignificant16bits = 0xFFFF0000;
        int xorCookie = (MessageHeader.MAGIC_COOKIE & mostSignificant16bits) >> shift16bits;
        return this.address.getPort() ^ xorCookie;
    }

    private int getXorAddress() {
        int addressBits = ByteBuffer.wrap(address.getAddress().getAddress()).getInt();

        if (isIPv4())
            return addressBits ^ MessageHeader.MAGIC_COOKIE;
        else if (isIPv6())
            return addressBits ^ (MessageHeader.MAGIC_COOKIE | transactionId);

        throw new IllegalArgumentException("Unknown IP address class: " + address.getAddress().getClass());
    }
}
