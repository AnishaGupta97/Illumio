package com.java.illumio.model;

/**
 * Represents a flow log entry with details such as version, account ID, source/destination
 * addresses and ports, and protocol.
 */
public class FlowLogEntry {
    // Version of the flow log entry
    private int version;
    // Account ID associated with the flow log
    private String accountId;
    // Interface ID from which the log entry originated
    private String interfaceId;
    // Source IP address
    private String srcAddr;
    // Destination IP address
    private String dstAddr;
    // Source port number
    private int srcPort;
    // Destination port number
    private int dstPort;
    // Protocol (e.g., TCP, UDP, ICMP) associated with the flow log entry
    private String protocol;

    /**
     * Constructs a FlowLogEntry object by parsing a line from a flow log file.
     * @param line a line from the flow log file containing space-separated values
     * @throws IllegalArgumentException if the line has fewer than the expected 14 fields
     */
    public FlowLogEntry(String line) {
        // Split the input line by whitespace and store each part in an array
        String[] parts = line.trim().split("\\s+");
        // Check if the line contains the minimum number of fields required
        if (parts.length < 14) {
            throw new IllegalArgumentException("Invalid flow log entry: " + line);
        }
        // Parse each field from the array to populate the FlowLogEntry attributes
        this.version = Integer.parseInt(parts[0]);
        this.accountId = parts[1];
        this.interfaceId = parts[2];
        this.srcAddr = parts[3];
        this.dstAddr = parts[4];
        this.srcPort = Integer.parseInt(parts[5]);
        this.dstPort = Integer.parseInt(parts[6]);
        this.protocol = getProtocolName(parts[7]);
    }

    /**
     * Converts a protocol number to its human-readable protocol name.
     * @param protocolNumber the protocol number as a String (e.g., "6" for TCP)
     * @return the protocol name ("tcp", "udp", "icmp", or "other" for unknown)
     */
    private String getProtocolName(String protocolNumber) {
        switch (protocolNumber) {
            case "6":
                return "tcp";
            case "17":
                return "udp";
            case "1":
                return "icmp";
            default:
                return "other";
        }
    }

    /**
     * Gets the destination port number.
     * @return the destination port
     */
    public int getDstPort() {
        return dstPort;
    }

    /**
     * Gets the protocol name associated with this flow log entry.
     * @return the protocol name (e.g., "tcp", "udp", "icmp", or "other")
     */
    public String getProtocol() {
        return protocol;
    }
}

