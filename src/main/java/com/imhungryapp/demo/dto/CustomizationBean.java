package com.imhungryapp.demo.dto;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomizationBean
  implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
  
    @Override
    public void customize(ConfigurableServletWebServerFactory container) {
        container.setContextPath("/imhungryapp");
    }
}
