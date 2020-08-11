package org.hamster.common.boot.sample.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The main application configuration.
 *
 * @author Jack Yin
 * @since 1.0
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "org.hamster.common.boot.sample")
public class ApplicationConfig {

}
