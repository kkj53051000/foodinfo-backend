package com.kp.foodinfo.config;

import com.kp.foodinfo.service.FileService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MockitoConfig {
    @Bean
    public FileService fileServiceMock() {
        return Mockito.mock(FileService.class);
    }
}
