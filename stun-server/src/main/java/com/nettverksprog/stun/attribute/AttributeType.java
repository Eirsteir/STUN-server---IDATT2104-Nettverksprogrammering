package com.nettverksprog.stun.attribute;


/**
 * Specification of STUN attributes which gets encoded in the STUN Attribute Header to
 * distinguish the different attributes as defined in RFC 5389.
 */
public enum AttributeType {

    MAPPED_ADDRESS(0x0001),
    XOR_MAPPED_ADDRESS(0x0020),
    USERNAME(0x0006),
    MESSAGE_INTEGRITY(0x0008),
    ERROR_CODE(0x0009),
    UNKNOWN_ATTRIBUTES(0x000A),
    NONCE(0x0015),
    CHANGE_REQUEST(0x0003),
    REALM(0x0014),
    SOFTWARE(0x8022);

    private int bits;

    AttributeType(int bits) {
        this.bits = bits;
    }

    public int getBits() {
        return this.bits;
    }
}
