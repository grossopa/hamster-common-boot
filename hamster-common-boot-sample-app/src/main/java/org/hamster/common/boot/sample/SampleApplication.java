package org.hamster.common.boot.sample;

import org.hamster.common.boot.sample.config.ApplicationConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * An example web application
 *
 * @author Jack Yin
 * @since 1.0
 */
@SpringBootApplication
public class SampleApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ApplicationConfig.class);
        builder.build().run(args);
    }
}
