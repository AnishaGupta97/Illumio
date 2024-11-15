package com.java.illumio.util;

import com.java.illumio.model.FlowLogEntry;
import com.java.illumio.model.LookupEntry;

/**
 * Utility class for parsing different types of entries.
 */
public class ParserUtil {
    /**
     * Parses a line from the lookup CSV file into a LookupEntry object.
     *
     * @param line the CSV line to parse
     * @return a LookupEntry object or null if parsing fails
     */
    public static LookupEntry parseLookupEntry(String line) {
        if (line.trim().isEmpty()) {
            // Skip empty lines without logging a warning
            return null;
        }
        String[] parts = line.split(",");
        if (parts.length == 3) {
            try {
                int dstPort = Integer.parseInt(parts[0].trim());
                String protocol = parts[1].trim().toLowerCase();
                String tag = parts[2].trim();
                return new LookupEntry(dstPort, protocol, tag);
            } catch (NumberFormatException e) {
                // Handle the exception or log it
                System.err.println("Invalid number format in lookup entry: " + line);
            }
        } else {
            System.err.println("Invalid lookup entry: " + line);
        }
        return null;
    }

    /**
     * Parses a line from the flow logs into a FlowLogEntry object.
     *
     * @param line the flow log line to parse
     * @return a FlowLogEntry object or null if parsing fails
     */
    public static FlowLogEntry parseFlowLogEntry(String line) {
        try {
            return new FlowLogEntry(line);
        } catch (Exception e) {
            // Handle the exception or log it
            System.err.println("Invalid flow log entry: " + line);
            return null;
        }
    }
}

