package com.nettverksprog.stun.message;

import com.nettverksprog.stun.header.MessageHeader;
import com.nettverksprog.stun.header.MessageHeaderParser;

import java.io.*;

/**
 * MessageParser class
 * Class to handle data in byte array
 * Parsing it to a message with a header
 */
public class MessageParser {

    private MessageHeaderParser messageHeaderParser;

    /**
     * Creates a new messageHeaderParser for parsing header specifically
     */
    public MessageParser() {
        messageHeaderParser = new MessageHeaderParser();
    }

    /**
     * Parses the message with data provided as parameter
     * The header is created with the messsage header parser
     * @param data
     * @return Message with correct header
     * @throws IOException
     */
    public Message parse(byte[] data) throws IOException {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
        DataInputStream inputStream = new DataInputStream(byteIn);

        byte[] headerBytes = new byte[MessageHeader.HEADER_LENGTH];
        inputStream.readFully(headerBytes);
        MessageHeader header = messageHeaderParser.parseMessageHeader(headerBytes);
        return new Message(header); // TODO: attributes?
    }

}
