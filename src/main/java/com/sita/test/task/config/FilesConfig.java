package com.sita.test.task.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.IntegrationFlows.ChannelsFunction;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;

import java.io.File;

@Configuration
public class FilesConfig {

    // Injecting values from properties
    @Value("${input.directory}")
    private String inputDir;


    
    @Bean
    public MessageChannel finalChannel() {
        return new DirectChannel(); // DirectChannel is a simple implementation of MessageChannel
    }

    // Configuring the integration flow for reading files from the input directory
    @Bean
    public IntegrationFlow fileIntegrationFlow() {
        return IntegrationFlows.from(Files.inboundAdapter(new File(inputDir)) // Inbound adapter for reading files from inputDir
                .autoCreateDirectory(true)  // Automatically create the input directory if it doesn't exist
                .preventDuplicates(true)    // Prevent processing duplicate files
                .patternFilter("*.txt"),    // Filter files with .txt extension
                e -> e.poller(p -> p.fixedRate(5000))) // Poll files at fixed rate of 5000 milliseconds
                .channel(finalChannel()) // Send messages to fileChannel
                .get();
    }
}
