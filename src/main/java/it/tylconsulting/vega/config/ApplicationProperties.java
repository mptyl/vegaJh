package it.tylconsulting.vega.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * Properties specific to Vega Jh.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@ComponentScan({ "ch.ralscha.extdirectspring"})
public class ApplicationProperties {
    // jhipster-needle-application-properties-property
    // jhipster-needle-application-properties-property-getter
    // jhipster-needle-application-properties-property-class
}
