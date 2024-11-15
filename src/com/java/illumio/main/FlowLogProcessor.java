package com.java.illumio.main;

import com.java.illumio.model.FlowLogEntry;
import com.java.illumio.model.LookupEntry;
import com.java.illumio.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Anisha Gupta
 * The FlowLogProcessor class processes flow log data by matching entries with a lookup table,
 * generating tag counts and port/protocol combination counts, and writing the output to a file.
 */
public class FlowLogProcessor {
    // Map to store lookup entries based on destination port and protocol
    private Map<String, LookupEntry> lookupMap = new HashMap<>();
    // Map to store counts of tags found in the flow logs
    private Map<String, Integer> tagCounts = new HashMap<>();
    // Map to store counts of port/protocol combinations found in the flow logs
    private Map<String, Integer> portProtocolCounts = new HashMap<>();

    /**
     * Loads the lookup table from a specified file and populates the lookupMap.
     * @param lookupFilePath the file path for the lookup table
     * @throws IOException if an I/O error occurs while reading the file
     */
    public void loadLookupTable(String lookupFilePath) throws IOException {
        // Read lookup entries from file
        List<LookupEntry> lookupEntries = FileUtil.readLookupEntries(lookupFilePath);
        // Populate the lookupMap with entries using "dstPort-protocol" as the key
        for (LookupEntry entry : lookupEntries) {
            String key = entry.getDstPort() + "-" + entry.getProtocol();
            lookupMap.put(key, entry);
        }
    }

    /**
     * Processes flow log entries from a specified file, updating tagCounts and portProtocolCounts
     * based on matches with the lookup table.
     * @param flowLogFilePath the file path for the flow log data
     * @throws IOException if an I/O error occurs while reading the file
     */
    public void processFlowLogs(String flowLogFilePath) throws IOException {
        // Read flow log entries from file
        List<FlowLogEntry> flowLogEntries = FileUtil.readFlowLogEntries(flowLogFilePath);
        // Process each flow log entry
        for (FlowLogEntry entry : flowLogEntries) {
            String key = entry.getDstPort() + "-" + entry.getProtocol();
            LookupEntry lookupEntry = lookupMap.get(key);
            String tag = (lookupEntry != null) ? lookupEntry.getTag() : "Untagged";

            // Update tag counts
            tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);

            // Update port/protocol counts
            String portProtocolKey = entry.getDstPort() + "," + entry.getProtocol();
            portProtocolCounts.put(portProtocolKey, portProtocolCounts.getOrDefault(portProtocolKey, 0) + 1);
        }
    }

    public String generateOutput(String outputDirectory) throws IOException {
        // Create output directory if it doesn't exist
        FileUtil.createDirectoryIfNotExists(outputDirectory);

        // Generate timestamped filename
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String outputFilePath = outputDirectory + File.separator + "output_" + timestamp + ".txt";

        // Write to the output file
        FileUtil.writeOutput(outputFilePath, tagCounts, portProtocolCounts);
        return outputFilePath;
    }

    /**
     * Main method to execute the flow log processing steps:
     * loading the lookup table, processing the flow logs, and generating output.
     * @param args command-line arguments (not used here)
     */
    public static void main(String[] args) {
        try {
            FlowLogProcessor processor = new FlowLogProcessor();
            // Load lookup table from specified file
            processor.loadLookupTable("data/lookup.csv");
            // Process flow log entries from specified file
            processor.processFlowLogs("data/flowlogs.txt");
            // Generate output file with results
            String outputFilePath = processor.generateOutput("output");
            System.out.println("Processing complete. Output file generated at: " + outputFilePath);
        } catch (Exception e) {
            // Print stack trace in case of errors
            e.printStackTrace();
        }
    }
}
