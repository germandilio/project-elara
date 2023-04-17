package ru.hse.elarateam.login.configs;

import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import io.prometheus.client.exemplars.tracer.otel.OpenTelemetrySpanContextSupplier;
import io.prometheus.client.exemplars.tracer.otel_agent.OpenTelemetryAgentSpanContextSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfig {
    @Bean
    public Capability capability(MeterRegistry registry) {
        return new MicrometerCapability(registry);
    }
}
