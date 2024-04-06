# SITA_TEST_TASK Project

## Description

SITA_TEST_TASK is a Spring Boot project designed for handling file integration using Spring Integration. It includes configurations for reading files, processing them, handling errors, and managing processed files.

## Project Structure

- **Group ID:** `com.sita.test.task`
- **Artifact ID:** `SITA_TEST_TASK`
- **Version:** `0.0.1-SNAPSHOT`
- **Packaging:** `WAR`

## Dependencies

The project depends on the following libraries:

- **Spring Boot Starter Integration:** Provides integration support for Spring Boot applications.
- **Spring Boot Starter Web:** Provides basic web support for Spring Boot applications.
- **Spring Integration HTTP:** Provides HTTP support for Spring Integration.
- **Spring Boot Starter Tomcat:** Provides embedded Tomcat server support for Spring Boot applications.
- **Spring Boot Starter Test:** Provides testing support for Spring Boot applications.
- **Spring Integration Test:** Provides testing support for Spring Integration applications.
- **Project Lombok:** Provides annotation-based code generation for Java applications.
- **Spring Integration Java DSL:** Provides a Java-based DSL for configuring Spring Integration flows.
- **Spring Integration Core:** Core library for Spring Integration.
- **Spring Integration File:** Provides file integration support for Spring Integration.

## Configuration

The project includes configuration classes for handling file integration. These configurations can be found in the `FilesConfig` class.

### File Integration Flow

- Reads files from the specified input directory.
- Automatically creates the input directory if it doesn't exist.
- Prevents processing of duplicate files.
- Filters files with the `.txt` extension.
- Polls files at a fixed rate of 5000 milliseconds.

### File Processing Flow

- Processes files received from the file integration flow.
- Converts file content to string and parses it into integers.
- Aggregates integers into a sum.
- Converts the sum back to a string.
- Writes the processed output to the specified output directory.
- Automatically creates the output directory if it doesn't exist.
- Replaces existing files in the output directory.
- Generates output file names with the `.OUTPUT` extension.

### Error Handling Flow

- Handles error messages that occur during file processing.
- Renames the original file to an error file and moves it to the error directory.

### Processed File Handling Flow

- Handles processed files received from the file processing flow.
- Moves processed files to the specified processed directory.
- Renames processed files with the `.PROCESSED` extension.


