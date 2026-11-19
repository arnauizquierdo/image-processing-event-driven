package com.imageprocessing.api.config;

import com.imageprocessing.core.config.RabbitMessageConverterConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RabbitMessageConverterConfig.class)
public class RabbitMQConfig { }