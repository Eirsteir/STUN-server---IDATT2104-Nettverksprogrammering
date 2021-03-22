package com.nettverksprog.stun.message;

import java.util.List;

import com.nettverksprog.stun.header.MessageClass;
import com.nettverksprog.stun.header.MessageHeader;
import com.nettverksprog.stun.attribute.Attribute;
import com.nettverksprog.stun.header.MessageMethod;
import lombok.ToString;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

@ToString
public class Message {

    private MessageHeader messageHeader;
    private List<Attribute> attributes;

    public Message(MessageHeader messageHeader, Attribute... attributes) {
        this.messageHeader = messageHeader;
        this.attributes = List.of(attributes);
        setMessageHeaderLength();
    }

    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(byteOut);

        dataOut.write(messageHeader.getBytes());
        writeAttributesTo(dataOut);

        return byteOut.toByteArray();
    }

    private void writeAttributesTo(DataOutputStream dataOut) throws IOException {
        for (Attribute attribute : attributes) {
            dataOut.writeShort(attribute.getType().getBits());
            dataOut.writeShort(attribute.getLength());
            dataOut.write(attribute.getBytes());
        }
    }

    public int getLength() {
        int length = 0;
        for (Attribute attribute : attributes) {
            length += Attribute.HEADER_LENGTH;
            length += attribute.getLength();
        }

        return length;
    }

    private void setMessageHeaderLength(){
        this.messageHeader.setLength(getLength());
    }

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public boolean isRequest() {
        return messageHeader.getMessageClass() == MessageClass.REQUEST;
    }

    public boolean isBinding() {
        return messageHeader.getMessageMethod() == MessageMethod.BINDING;
    }

    public MessageMethod getMessageMethod() {
        return messageHeader.getMessageMethod();
    }
}
