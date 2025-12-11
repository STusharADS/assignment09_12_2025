package org.example.rideshare.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI rideshareOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8081");
        localServer.setDescription("Local Development Server");

        Info info = new Info()
                .title("Rideshare API Documentation")
                .version("1.0.0")
                .description("Complete API documentation for Rideshare Application. " +
                        "Includes Authentication, Rides, and Analytics APIs.");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}
