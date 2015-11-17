package ru.interview.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import ru.interview.controller.RequestController;

/**
 * Created by novikam on 16.11.15.
 */

@Configuration
public class JerseyConfig extends ResourceConfig{

    public JerseyConfig() {
        register(RequestController.class);
    }
}
