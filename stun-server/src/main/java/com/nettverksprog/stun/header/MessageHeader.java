package com.nettverksprog.stun.header;

import lombok.ToString;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;


@ToString
public class MessageHeader {
    /*
	 0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 = 32 bits
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |0 0|    STUN Message Type (14) |       Message Length (16)     |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                      Magic Cookie (32)                        | 0x2112A442
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      |                     Transaction ID (96 bits)                  |
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     */

    public final static int HEADER_LENGTH = 20;
    public final static int TRANSACTION_ID_LENGTH = 12;
    protected static final int MAGIC_COOKIE = 0x2112A442;
    public static final int LEADING_ZEROS_SHIFT = 0x1E;
    public static final int MESSAGE_TYPE_SHIFT = 0x10;
    public static final int MESSAGE_TYPE_MASK = 0x3FFF0000;
    public static final int MESSAGE_METHOD_MASK = 0x3EEF;
    public static final int MESSAGE_LENGTH_MASK = 0x0000FFFF;
    public static final int MESSAGE_CLASS_MASK = 0x0110;

    private MessageMethod messageMethod;
    private MessageClass messageClass;
    private int length;
    private byte[] transactionId;

    public MessageHeader(MessageMethod messageMethod, MessageClass messageClass, int length, byte[] transactionId) {
        this.messageMethod = messageMethod;
        this.messageClass = messageClass;
        this.length = length;
        this.transactionId = transactionId;
    }

    public MessageHeader(MessageMethod messageMethod, MessageClass messageClass, byte[] transactionId){
        this.messageMethod = messageMethod;
        this.messageClass = messageClass;
        this.transactionId = transactionId;
        this.length = 0;
    }

    public void setTransactionId(byte[] id) {
        System.arraycopy(id, 0, this.transactionId, 0, 12);
    }

    public byte[] getTransactionId() {
        return transactionId;
    }

    public boolean compareId(MessageHeader messageHeader){
        return messageHeader.getTransactionId() == this.transactionId;
    }

    public byte[] getBytes() throws IOException{
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(byteOut);

        int methodBits = messageMethod.getBits();
        int messageClassBits = messageClass.getBits();

        int leading32bits = (0x0 << LEADING_ZEROS_SHIFT)
                | ((messageClassBits + methodBits) << MESSAGE_TYPE_SHIFT) | ((short) this.length);
        dataOut.writeInt(leading32bits);
        dataOut.writeInt(MessageHeader.MAGIC_COOKIE);
        dataOut.write(this.transactionId);

        return byteOut.toByteArray();
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public MessageMethod getMessageMethod() {
        return messageMethod;
    }

    public MessageClass getMessageClass() {
        return messageClass;
    }

}
