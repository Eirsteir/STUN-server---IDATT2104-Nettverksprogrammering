package com.nettverksprog.stun.header;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class MessageHeaderParser {

    private static final int MESSAGE_CLASS_MASK = 0x0110;

    /**
     * The message header parser is provided the header bytes
     * The different contents of the header is read with datainputstream
     * Find each part of the messageheader within the provided bytes
     * Sets each value based on the order of bytes
     * Creates a messageheader
     * @param headerBytes
     * @return message header
     * @throws IOException
     */
    public MessageHeader parseMessageHeader(byte[] headerBytes) throws IOException {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(headerBytes);
        DataInputStream dataIn = new DataInputStream(byteIn);

        /*
             0                   1                   2                   3
              0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
         *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
         *  |0 0|     STUN Message Type     |         Message Length        |
         *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
         *  |                         Magic Cookie                          |
         *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
         *  |                                                               |
         *  |                     Transaction ID (96 bits)                  |
         *  |                                                               |
         *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        */
        int leading32Bits = dataIn.readInt(); //is the first int of the header bytes
        int leadingZeroes = (leading32Bits & MessageHeader.MESSAGE_TYPE_MASK) >> MessageHeader.MESSAGE_TYPE_SHIFT;
        int messageTypeBits = (leading32Bits & MessageHeader.MESSAGE_TYPE_MASK) >> MessageHeader.MESSAGE_TYPE_SHIFT; //type is bit 3-16
        int messageClassBits = messageTypeBits & MessageHeader.MESSAGE_CLASS_MASK;
        int messageMethodBits = messageTypeBits & MessageHeader.MESSAGE_METHOD_MASK;
        MessageClass messageClass = MessageClass.fromBits(messageClassBits);
        MessageMethod messageMethod = MessageMethod.fromBits(messageMethodBits);

        int length = leading32Bits & MessageHeader.MESSAGE_LENGTH_MASK;

        int magicCookie = dataIn.readInt(); //magic cookie is the following 16 bits found with read int
        checkMagicCookie(magicCookie); //we need to check if the magic cookie is its constant value

        byte[] transactionId = new byte[MessageHeader.TRANSACTION_ID_LENGTH]; //byte array of transactionId is defined with its constant length
        dataIn.readFully(transactionId); //reads the rest of the datainputstream into the transaction id

        return new MessageHeader(messageMethod, messageClass, length, transactionId);
    }

    /**
     * Checks that the Magic Cookie in the MessageHeader matches with the expected value.
     * @param checkThis variable to check
     * @throws IOException
     */
    private void checkMagicCookie(int checkThis) throws IOException {
        if (checkThis != MessageHeader.MAGIC_COOKIE) {
            throw new IOException("Wrong magic cookie, go get some other drugs" + checkThis + " != " + MessageHeader.MAGIC_COOKIE);
        }
    }

    /**
     * Checks that the header recieved has the correct length,
     * throws error if something doesnt match.
     * @param encodedHeader
     * @throws IOException
     */
    private void checkHeaderLength(byte[] encodedHeader) throws IOException {
        if (encodedHeader == null) {
            throw new NullPointerException();
        } else if (encodedHeader.length != MessageHeader.HEADER_LENGTH) {
            throw new IOException("This header's length does not match the expected value.");
        }
    }

}
