package com.mrthaw.inventory_frontend;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Maps the URL '/user-accounts' directly to your deep HTML file path!
        registry.addViewController("/")
                .setViewName("index");

        registry.addViewController("/home")
                .setViewName("index");

        registry.addViewController("/user-accounts")
                .setViewName("index");

        registry.addViewController("/user-roles")
                .setViewName("index");

        registry.addViewController("/customers")
                .setViewName("index");

        registry.addViewController("/suppliers")
                .setViewName("index");

        registry.addViewController("/batches")
                .setViewName("index");

        registry.addViewController("/units")
                .setViewName("index");
    }
}
