package com.nettverksprog.stun.message;

import com.nettverksprog.stun.header.MessageHeader;
import com.nettverksprog.stun.header.MessageHeaderParser;

import java.io.*;

public class MessageParser {

    private MessageHeaderParser messageHeaderParser;

    public MessageParser() {
        messageHeaderParser = new MessageHeaderParser();
    }

    public Message parse(byte[] data) throws IOException {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
        DataInputStream inputStream = new DataInputStream(byteIn);

        byte[] headerBytes = new byte[MessageHeader.HEADER_LENGTH];
        inputStream.readFully(headerBytes);
        MessageHeader header = messageHeaderParser.parseMessageHeader(headerBytes);
        return new Message(header); // TODO: attributes?
    }

}
