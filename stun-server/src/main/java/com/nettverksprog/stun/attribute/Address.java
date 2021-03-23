package com.nettverksprog.stun.attribute;

import lombok.ToString;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetSocketAddress;

/**
 * Abstract class Address provides subclasses
 * MappedAddress and XorAddress with shared methods and variables
 */
@ToString
public abstract class Address implements Attribute {
    protected static final int IPV4_LENGTH = 8; // TODO: byte?
    protected static final int IPV6_LENGTH = 20; // TODO: byte?
    protected static final int IPV4_FAMILY = 0x01;
    protected static final int IPV6_FAMILY = 0x02;
    protected InetSocketAddress address;
    protected int length;
    protected int addressFamily;

    /**
     * Address is defined at creation
     * Uses methods resolveLength and resolveAddressFamily with address
     * to define length and addressFamily
     * @param address
     */
    public Address(InetSocketAddress address) {
        this.address = address;
        this.length = resolveLength(address);
        this.addressFamily = resolveAddressFamily(address);
    }

    /**
     * Checks the class type with getAddress to determine the length of the
     * @param address
     * @return
     */
    protected int resolveLength(InetSocketAddress address) {
        if (address.getAddress() instanceof Inet4Address)
            return IPV4_LENGTH;
        else if (address.getAddress() instanceof Inet6Address)
            return IPV6_LENGTH;

        throw new IllegalArgumentException("Unknown IP address class: " + address.getAddress().getClass());
    }

    /**
     * Checks class type with getAddress to determine the address family of the mapepd address
     * @param address
     * @return
     */
    protected int resolveAddressFamily(InetSocketAddress address) {
        if (address.getAddress() instanceof Inet4Address)
            return IPV4_FAMILY;
        else if (address.getAddress() instanceof Inet6Address)
            return IPV6_FAMILY;

        throw new IllegalArgumentException("Unknown IP address class: " + address.getAddress().getClass());
    }

    protected boolean isIPv4(){
        return address.getAddress() instanceof Inet4Address;
    }

    protected boolean isIPv6(){
        return address.getAddress() instanceof Inet6Address;
    }

    @Override
    public abstract AttributeType getType();

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public abstract byte[] getBytes() throws IOException;
}
