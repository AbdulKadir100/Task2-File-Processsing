package com.sita.test.task.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.IntegrationFlows.ChannelsFunction;
import org.springframework.messaging.support.ErrorMessage;

@Configuration
public class ErrorFileHandlingConfig {
	
	
	@Value("${error.directory}")
    private String errorDir;
	
	// Configuring the integration flow for handling error messages
    @Bean
    public IntegrationFlow errorHandlingFlow() {
        return IntegrationFlows.from((ChannelsFunction) MessageChannels.flux()) // Incoming error messages
                .handle((GenericHandler<ErrorMessage>) (errorMessage, headers) -> { // Handle error message
                    File originalFile = (File) errorMessage.getOriginalMessage().getHeaders().get(errorDir);
                    File errorFile = new File(errorDir + originalFile.getName() + ".ERROR");
                    originalFile.renameTo(errorFile); // Rename original file to error file
                    return null;
                })
                .get();
    }

}
