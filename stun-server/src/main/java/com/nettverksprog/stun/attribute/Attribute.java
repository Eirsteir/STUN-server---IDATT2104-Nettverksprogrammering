package com.nettverksprog.stun.attribute;

import java.io.IOException;


/**
 *  STUN attributes are the message payload following the STUN message header.
 *  A message can have zero or more attributes.
 *  Each attribute MUST be TLV encoded, with a 16-bit type, 16-bit length, and value.
 *
 *  The format is as follows:
 *        0                   1                   2                   3
 *        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *       |         Type                  |            Length             |
 *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *       |                         Value (variable)                ....
 *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */
public interface Attribute {

    int HEADER_LENGTH = 4;

    AttributeType getType();

    int getLength();

    byte[] getBytes() throws IOException;

}
