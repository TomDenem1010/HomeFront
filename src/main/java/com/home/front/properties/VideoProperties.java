package com.home.front.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "home.video")
@Data
public class VideoProperties {

  private String url;
}
