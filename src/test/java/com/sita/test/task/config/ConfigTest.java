package com.sita.test.task.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
//import org.springframework.integration.dsl.context.IntegrationFlowContext.IntegrationFlowRegistration;
import org.springframework.integration.dsl.context.IntegrationFlowContext.IntegrationFlowRegistrationBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@ExtendWith(MockitoExtension.class)
public class ConfigTest {

    @Mock
    private MessageChannel fileChannel;

    @Mock
    private MessageChannel errorChannel;

    @Mock
    private MessageChannel processedChannel;

    @Mock
    private File inputDir;

    @Mock
    private File outputDir;

    @Mock
    private File errorDir;

    @Mock
    private File processedDir;

    @InjectMocks
    private FilesConfig integrationFlowConfiguration;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFileProcessingFlow() {
    	FileProcessingConfig fpc = new FileProcessingConfig();
        IntegrationFlow fileProcessingFlow = fpc.fileProcessingFlow();
        IntegrationFlowContext flowContext = mock(IntegrationFlowContext.class);
        IntegrationFlowRegistrationBuilder flowRegistration = mock(IntegrationFlowRegistrationBuilder.class);
        when(flowContext.registration(fileProcessingFlow)).thenReturn(flowRegistration);
        
        // Mock necessary components and dependencies

        // Simulate sending a message to the fileChannel
        Message<Integer> message = MessageBuilder.withPayload(10).build();
        when(fileChannel.send(any(Message.class))).thenReturn(true);

        // Verify that the message is processed correctly
        verify(fileChannel).send(message);
        // Add more verifications as needed
    }

    @Test
    public void testErrorHandlingFlow() {
    	ErrorFileHandlingConfig efh = new ErrorFileHandlingConfig();
        IntegrationFlow errorHandlingFlow = efh.errorHandlingFlow();
        IntegrationFlowContext flowContext = mock(IntegrationFlowContext.class);
        IntegrationFlowRegistrationBuilder flowRegistration = mock(IntegrationFlowRegistrationBuilder.class);
        when(flowContext.registration(errorHandlingFlow)).thenReturn(flowRegistration);
        
        // Mock necessary components and dependencies

        // Simulate sending an error message to the errorChannel
        Message<File> errorMessage = MessageBuilder.withPayload(new File("dummyFile.txt")).build();
        when(errorChannel.send(any(Message.class))).thenReturn(true);

        // Verify that the error message is handled correctly
        verify(errorChannel).send(errorMessage);
        // Add more verifications as needed
    }

    @Test
    public void testProcessedFileHandlingFlow() {
    	ProcessedFileHandlingConfig pfh = new ProcessedFileHandlingConfig();
        IntegrationFlow processedFileHandlingFlow = pfh.processedFileHandlingFlow();
        IntegrationFlowContext flowContext = mock(IntegrationFlowContext.class);
        IntegrationFlowRegistrationBuilder flowRegistration = mock(IntegrationFlowRegistrationBuilder.class);
        when(flowContext.registration(processedFileHandlingFlow)).thenReturn(flowRegistration);
        
        // Mock necessary components and dependencies

        // Simulate processing of a file
        File inputFile = new File("outputDir/dummyFile.OUTPUT");
        when(outputDir.listFiles()).thenReturn(new File[]{inputFile});

        // Verify that the file is processed correctly
        verify(processedChannel).send(any(Message.class));
        // Add more verifications as needed
    }
}
