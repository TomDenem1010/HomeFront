package com.home.front.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "home.tunnel")
@Data
public class TunnelProperties {

  private String url;
}
