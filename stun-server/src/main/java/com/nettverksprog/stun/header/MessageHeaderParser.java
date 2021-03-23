package com.nettverksprog.stun.header;

import com.nettverksprog.stun.message.MessageFormatException;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class MessageHeaderParser {

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
        checkHeaderLength(headerBytes);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(headerBytes);
        DataInputStream dataIn = new DataInputStream(byteIn);

        int leading32Bits = dataIn.readInt(); //is the first int of the header bytes
        int leadingZeroes = (leading32Bits & MessageHeader.MESSAGE_TYPE_MASK) >> MessageHeader.MESSAGE_TYPE_SHIFT;
        checkLeadingZeroes(leadingZeroes);

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
     * Checks that the header received has the correct length,
     * throws error if something doesnt match.
     * @param encodedHeader
     * @throws IOException
     */
    private void checkHeaderLength(byte[] encodedHeader) {
        if (encodedHeader == null)
            throw new NullPointerException();
        else if (encodedHeader.length != MessageHeader.HEADER_LENGTH)
            throw new MessageFormatException("This header's length does not match the expected value.");
    }

    /**
     * Make sure the message contains the two leading zeroes of a STUN message.
     * @param leadingZeroes
     */
    private void checkLeadingZeroes(int leadingZeroes) {
        if (leadingZeroes != 0)
            throw new MessageFormatException("The first two bits of the message are not 0, was: " + leadingZeroes);
    }

    /**
     * Checks that the Magic Cookie in the MessageHeader matches with the expected value.
     * @param magicCookie variable to check
     */
    private void checkMagicCookie(int magicCookie) {
        if (magicCookie != MessageHeader.MAGIC_COOKIE)
            throw new MessageFormatException("Wrong magic cookie, go get some other drugs" + magicCookie + " != " + MessageHeader.MAGIC_COOKIE);
    }


}
