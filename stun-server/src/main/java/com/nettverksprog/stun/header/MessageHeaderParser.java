package com.nettverksprog.stun.header;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class MessageHeaderParser {

    private static final int MESSAGE_CLASS_MASK = 0x0110;

    public MessageHeader parseMessageHeader(byte[] headerBytes) throws IOException {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(headerBytes);
        DataInputStream dataIn = new DataInputStream(byteIn);

        int leading32Bits = dataIn.readInt();
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
        int leadingZeroes = (leading32Bits & MessageHeader.MESSAGE_TYPE_MASK) >> MessageHeader.MESSAGE_TYPE_SHIFT;
        int messageTypeBits = (leading32Bits & MessageHeader.MESSAGE_TYPE_MASK) >> MessageHeader.MESSAGE_TYPE_SHIFT;
        int messageClassBits = messageTypeBits & MessageHeader.MESSAGE_CLASS_MASK;
        int messageMethodBits = messageTypeBits & MessageHeader.MESSAGE_METHOD_MASK;
        MessageClass messageClass = MessageClass.fromBits(messageClassBits);
        MessageMethod messageMethod = MessageMethod.fromBits(messageMethodBits);

        int length = leading32Bits & MessageHeader.MESSAGE_LENGTH_MASK;

        int magicCookie = dataIn.readInt();
        checkMagicCookie(magicCookie);

        byte[] transactionId = new byte[MessageHeader.TRANSACTION_ID_LENGTH];
        dataIn.readFully(transactionId);

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
