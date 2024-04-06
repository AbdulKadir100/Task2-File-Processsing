package com.sita.test.task.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.IntegrationFlows.ChannelsFunction;
import org.springframework.integration.file.dsl.Files;

@Configuration
public class ProcessedFileHandlingConfig {
	
	@Value("${processed.directory}")
    private String processedDir;
	
	@Value("${output.directory}")
    private String outputDir;
	
	
	
	// Configuring the integration flow for handling processed files
	@Bean
    public IntegrationFlow processedFileHandlingFlow() {
        return IntegrationFlows.from((ChannelsFunction) Files.inboundAdapter(new File(outputDir)) // Inbound adapter for reading files from outputDir
                .autoCreateDirectory(true) // Automatically create the output directory if it doesn't exist
                .preventDuplicates(true) // Prevent processing duplicate files
                .patternFilter("*.OUTPUT")) // Filter files with .OUTPUT extension
                .handle((GenericHandler<File>) (file, headers) -> { // Handle processed files
                    File processedFile = new File(processedDir + file.getName() + ".PROCESSED");
                    file.renameTo(processedFile); // Rename processed file
                    return null;
                })
                .get();
    }

}
