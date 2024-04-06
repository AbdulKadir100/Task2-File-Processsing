package com.sita.test.task.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageChannel;

import java.io.File;
import java.util.List;

@Configuration
public class FileProcessingConfig {
	
	@Value("${output.directory}")
    private String outputDir;
	
	 @Bean
	    public MessageChannel finalChannel() {
	        return new DirectChannel(); // DirectChannel is a simple implementation of MessageChannel
	    }

	// Configuring the integration flow for processing files from fileChannel
	@Bean
	public IntegrationFlow fileProcessingFlow() {
        return IntegrationFlows.from(finalChannel()) // Incoming messages from fileChannel
                .transform(Files.toStringTransformer()) // Convert file content to String
                .transform(String.class, Integer::parseInt)
                .aggregate(aggregator -> aggregator.outputProcessor(group -> { // Aggregate integers into sum
                    int sum = 0;
                    for (Integer number : group.getMessages().<Integer>getPayload()) {
                        sum += number;
                    }
                    return sum;
                }))
                .transform(Object::toString) // Convert sum back to String
                .handle(Files.outboundAdapter(new File(outputDir)) // Write processed files to output directory
                        .autoCreateDirectory(true) // Automatically create the output directory if it doesn't exist
                        .fileExistsMode(FileExistsMode.REPLACE) // Replace existing files
                        .fileNameGenerator(message -> { // Generate output file name
                            String originalFilename = message.getHeaders().get(FileHeaders.FILENAME, String.class);
                            return originalFilename + ".OUTPUT";
                        }))
                .get();
    }
}
