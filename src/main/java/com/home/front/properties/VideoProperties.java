package com.home.front.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;

import lombok.Data;

@ConfigurationProperties(prefix = "home.video")
@Data
public class VideoProperties {

    @NonNull
    private String url;
}
