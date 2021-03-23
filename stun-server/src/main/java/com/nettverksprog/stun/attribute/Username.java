package com.nettverksprog.stun.attribute;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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

    public int getUsernameLength(){
        return this.username.length;
    }

    public byte[] getUsername() {
        return username;
    }

    public String getUsernameString() throws UnsupportedEncodingException {
        return new String(this.username, ENCODING);
    }

    private int calculatePadding(){
        return MAX_LENGTH - username.length;
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
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(byteOut);
        byte[] padding = new byte[calculatePadding()];
        dataOut.write(padding);
        dataOut.write(username);

        return byteOut.toByteArray();
    }
}
