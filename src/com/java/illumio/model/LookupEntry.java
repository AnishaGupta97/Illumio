package com.java.illumio.model;

/**
 * Represents an entry in the lookup table with destination port, protocol, and tag.
 */
public class LookupEntry {
    // Destination port for the lookup entry
    private int dstPort;
    // Protocol associated with the entry, stored in lowercase
    private String protocol;
    // Tag assigned to this lookup entry
    private String tag;

    /**
     * Constructs a LookupEntry object with the specified destination port, protocol, and tag.
     * @param dstPort the destination port
     * @param protocol the protocol (converted to lowercase)
     * @param tag the tag associated with this entry
     */
    public LookupEntry(int dstPort, String protocol, String tag) {
        this.dstPort = dstPort;
        this.protocol = protocol.toLowerCase();
        this.tag = tag;
    }

    /**
     * Gets the destination port of this lookup entry.
     * @return the destination port
     */
    public int getDstPort() {
        return dstPort;
    }

    /**
     * Gets the protocol associated with this lookup entry.
     * @return the protocol in lowercase
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Gets the tag assigned to this lookup entry.
     * @return the tag
     */

    public String getTag() {
        return tag;
    }
}