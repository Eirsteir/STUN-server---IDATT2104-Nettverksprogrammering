package com.nettverksprog.stun.attribute;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;

/**
 * Username attribute class as defined in RFC 5389
 */
public class Username implements Attribute {
    private InetSocketAddress address;
    private static final int MAX_LENGTH = 512; //per RFC 5389
    private final byte[] username;
    private final String ENCODING = "UTF-8";
    private int password; //sløyfes?

    public Username(InetSocketAddress address, String username) throws UnsupportedEncodingException {
        this.address = address;
        this.username = encodeUsername(username);
    }

    public byte[] encodeUsername(String username) throws UnsupportedEncodingException {
        return username.getBytes(ENCODING);
    }

    @Override
    public AttributeType getType() {
        return AttributeType.USERNAME;
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return new byte[0];
    }
}
