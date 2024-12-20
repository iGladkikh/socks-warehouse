package com.igladkikh.warehouse.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Sock warehouse Api",
                description = "API склада носков",
                version = "1.0.0",
                contact = @Contact(
                        name = "Igor Gladkikh",
                        email = "ig.gladkikh@gmail.com"
                )
        )
)
public class OpenApiConfig {
}
