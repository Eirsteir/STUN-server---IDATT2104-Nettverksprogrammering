package com.nettverksprog.stun.attribute;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;

/**
 * Username attribute class as defined in RFC 5389
 * This Username class is incomplete, and will need further work before implementation
 */
public class Username implements Attribute {
    private InetSocketAddress address;
    private static final int MAX_LENGTH = 512; //per RFC 5389
    private final byte[] username;
    private final String ENCODING = "UTF-8";
    private int password; //sløyfes?

    /**
     * A Username is an encoded String, which is defined at creation
     * The username has a maximum length of 512 bytes
     * @param username
     * @throws UnsupportedEncodingException
     */
    public Username(String username) throws UnsupportedEncodingException {
        this.username = encodeUsername(username);
    }

    /**
     * Uses UTF-8 to encode the username String, returning the byte array
     * @param username
     * @return
     * @throws UnsupportedEncodingException
     */
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

    /**
     * Method that returns the remaining nr of bytes
     * @return padding
     */
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

    /**
     * Method that returns the total 512 bytes
     * The username byte arrays at the back
     * and the calculated padding at the front
     * @return
     * @throws IOException
     */
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
