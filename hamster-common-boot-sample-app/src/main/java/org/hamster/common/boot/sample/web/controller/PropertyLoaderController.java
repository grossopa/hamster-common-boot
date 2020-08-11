package org.hamster.common.boot.sample.web.controller;

import org.hamster.common.boot.core.property.helper.DefaultSpringPropertyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Loads all properties
 *
 * @author Jack Yin
 * @since 1.0
 */
@RestController
@RequestMapping
public class PropertyLoaderController {

    @Autowired
    ConfigurableEnvironment environment;

    @GetMapping("/properties")
    public Map<String, Object> getProperties() {
        return new DefaultSpringPropertyReader().readAllProperties(environment);
    }
}
