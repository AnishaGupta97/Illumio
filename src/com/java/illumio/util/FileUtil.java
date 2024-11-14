package com.java.illumio.util;

import com.java.illumio.model.FlowLogEntry;
import com.java.illumio.model.LookupEntry;

import java.io.*;
import java.util.*;

public class FileUtil {

    /**
     * Reads lookup entries from a specified file and returns a list of LookupEntry objects.
     * @param filePath the path of the file containing lookup entries
     * @return a list of LookupEntry objects parsed from the file
     * @throws IOException if an I/O error occurs
     */
    public static List<LookupEntry> readLookupEntries(String filePath) throws IOException {
        // List to store each parsed LookupEntry
        List<LookupEntry> lookupEntries = new ArrayList<>();


        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        // Skip header
        bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            // Parse the line into a LookupEntry object
            LookupEntry entry = ParserUtil.parseLookupEntry(line);

            // If the entry is valid, add it to the list
            if (entry != null) {
                lookupEntries.add(entry);
            }
        }

        // Close the BufferedReader to release resources
        bufferedReader.close();

        // Return the list of parsed lookup entries
        return lookupEntries;
    }

    /**
     * Reads flow log entries from a specified file and returns a list of FlowLogEntry objects.
     * @param filePath the path of the file containing flow log entries
     * @return a list of FlowLogEntry objects parsed from the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static List<FlowLogEntry> readFlowLogEntries(String filePath) throws IOException {
        // List to store each parsed FlowLogEntry
        List<FlowLogEntry> flowLogEntries = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        // Read each line of the file
        while ((line = bufferedReader.readLine()) != null) {
            // Skip empty lines
            if (line.trim().isEmpty()) continue;

            // Parse the line into a FlowLogEntry object
            FlowLogEntry entry = ParserUtil.parseFlowLogEntry(line);

            // If the entry is valid, add it to the list
            if (entry != null) {
                flowLogEntries.add(entry);
            }
        }
        bufferedReader.close();
        return flowLogEntries;
    }

    /**
     * Writes output to a specified file, including tag counts and port/protocol combination counts.
     * @param filePath the path of the output file to write
     * @param tagCounts a map containing tag names and their counts
     * @param portProtocolCounts a map containing port/protocol combinations and their counts
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public static void writeOutput(String filePath, Map<String, Integer> tagCounts,
                                   Map<String, Integer> portProtocolCounts) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        // Write Tag Counts
        bufferedWriter.write("Tag Counts:\n");
        bufferedWriter.write("Tag,Count\n");

        // Write each tag and its count to the output file
        for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
            bufferedWriter.write(entry.getKey() + "," + entry.getValue() + "\n");
        }
        bufferedWriter.write("\n");
        // Write Port/Protocol Combination Counts
        bufferedWriter.write("Port/Protocol Combination Counts:\n");
        bufferedWriter.write("Port,Protocol,Count\n");

        // Write each port/protocol combination and its count to the output file
        for (Map.Entry<String, Integer> entry : portProtocolCounts.entrySet()) {
            bufferedWriter.write(entry.getKey() + "," + entry.getValue() + "\n");
        }
        bufferedWriter.close();
    }

    public static void createDirectoryIfNotExists(String directoryPath) {
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                System.out.println("Output directory created: " + directoryPath);
            } else {
                System.err.println("Failed to create output directory: " + directoryPath);
            }
        }
    }
}
