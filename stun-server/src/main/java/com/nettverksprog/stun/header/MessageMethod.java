package com.nettverksprog.stun.header;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * Message method enum
 */
@Getter
public enum MessageMethod {
    BINDING(0x0001);

    private final int bits;

    MessageMethod(int bits) {
        this.bits = bits;
    }

    /**
     * Returns the BINDING bits if the argument is a BINDING method
     * @param bits
     * @return binding bits
     */
    public static MessageMethod fromBits(int bits) {
        return Stream.of(MessageMethod.values())
                .filter(c -> c.getBits() == bits)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
