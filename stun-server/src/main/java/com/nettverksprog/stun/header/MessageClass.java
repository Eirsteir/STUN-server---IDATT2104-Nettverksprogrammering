package com.nettverksprog.stun.header;

import java.util.stream.Stream;

/**
 * MessageClass enum
 * Contains several different message class types
 */
public enum MessageClass {
    REQUEST(0x000),

    SUCCESS_RESPONSE(0x0100),

    FAILURE_RESPONSE(0x0110),

    INDICATION(0x0010);

    private int bits;

    MessageClass(int bits) {
        this.bits = bits;
    }

    /**
     * Returns the bits corresponding to the class type
     * if the argument is equal to bits in class
     * @param bits
     * @return class bits
     */
    public static MessageClass fromBits(int bits) {
        return Stream.of(MessageClass.values())
                .filter(c -> c.getBits() == bits)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public int getBits() {
        return bits;
    }
}
