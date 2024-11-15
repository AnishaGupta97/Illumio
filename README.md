# Illumio Assessment - Flow Log Processor

## Overview
This program reads a flow log file containing AWS VPC flow log data and a lookup CSV file containing port, protocol, and tag mappings. It processes the flow logs, maps each entry to a tag based on the destination port and protocol, and generates an output file containing:
- Count of matches for each tag
- Count of matches for each port/protocol combination
The output files are stored in a separate directory with filenames that include the current date and time to prevent overwriting previous results.

## Assumptions
- **Flow Log Format**: 
  - The program supports only the default log format.
  - Only version 2 of the flow log format is supported.
  - The flow logs do not contain a header line.
  - Each flow log entry is expected to have at least 14 space-separated fields.
- **Lookup Table Format**: CSV with columns:
  - The lookup CSV file has a header line and contains exactly three columns: `dstport`, `protocol`, and `tag`.
  - Ports and protocol numbers are parsed as integers.
  - Protocols are expected to be `tcp`, `udp`, or `icmp`, but the program can handle others as `other`.
- **Case Insensitivity::
  - Matches for protocols and tags are case-insensitive.
- **Protocol Mapping**: Protocol numbers in the flow logs are mapped as follows:
  - `1` → `icmp`
  - `6` → `tcp`
  - `17` → `udp`
  - Any other protocol numbers are mapped to `other`.
- **Default Tags**: If a flow log entry does not match any entry in the lookup table, it is tagged as `Untagged`.
- **File Sizes**:
  - The flow log file size can be up to 10 MB.
  - The lookup file can have up to 10,000 mappings.
- **Dependencies**:
  - The program uses only standard Java libraries.
  - No external libraries or frameworks (e.g., Hadoop, Spark, Pandas) are used.

## Project Structure
- **data/**
  - `flowlogs.txt` &mdash; Flow log file to be processed.
  - `lookup.csv` &mdash; Lookup table containing `dstport`, `protocol`, and `tag`.
    
- **output/** &mdash; Generated output file.
  
- **src/com/java/illumio/**
  -  **Main/**
    - `FlowLogProcessor.java` &mdash; Main entry point for the program.
  
  - **Model/**
    - `FlowLogEntry.java` &mdash; Flow log entry model
    - `LookupEntry.java` &mdash; Lookup entry model.
  
  - **util/**
    - `FileUtil.java` &mdash; File Utilities.
    - `ParserUtil.java` &mdash; Parser Utilities.
  
## Instructions to Compile and Run

### Prerequisites
- **Java Development Kit (JDK) 8+**
- An IDE like `IntelliJ IDEA` or `Eclipse` is recommended but not necessary.
- Command-line tools like `javac` and `java` are needed if running from the terminal.

### Clone the Repository
First, clone the repository to your local machine using Git:

**git clone https://github.com/AnishaGupta97/illumio.git**

### Compile the Program
1. Open Terminal
2. Navigate to the project root directory `illumio`.
3. Ensure the Input Files Are in Place:
   - Place your `flowlogs.txt` and `lookup.csv` files in the data directory.
   - Sample files are provided for testing.
4. Create a bin directory where the compiled class files will be stored.
   ```bash
    mkdir bin
    ```
4. Compile the program by:
   - For macOS/Linux/Powershell/Git Bash
   ```bash
    javac -d bin src/com/java/illumio/**/*.java
    ```
    - For Windows
   ```bash
    javac -d bin src\com\java\illumio\main\FlowLogProcessor.java src\com\java\illumio\model\FlowLogEntry.java src\com\java\illumio\model\LookupEntry.java src\com\java\illumio\util\FileUtil.java src\com\java\illumio\util\ParserUtil.java
    ```
   
### Run the Program
1. Execute the program with:
    ```bash
    java -cp bin com.java.illumio.main.FlowLogProcessor
    ```
3. The program will generate the output files in the output directory, which will be created if it doesn't exist. The output will be in the form of `output_YYYYMMDD_HHmmss.txt`

## Testing

### Test Cases
- Untagged Entries: Test with flow log entries that do not match any lookup entries to ensure they are tagged as Untagged.
- Case Insensitivity: Verify that protocol names and tags are matched case-insensitively
- Invalid Entries: The program skips invalid entries and logs a warning.
- Lookup Table: Include multiple mappings for the same tag to test aggregation.
- Flow Logs: Verify that each log entry is correctly parsed and processed.

### How to Test
1. Place the flow log (`logs.txt`) and lookup table (`lookup.csv`) in the `data/` directory.
2. Run the program as described above.
3. Verify the output in the `output/` directory matches expected results.
---
