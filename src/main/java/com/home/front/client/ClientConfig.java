package com.home.front.client;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.home.front.properties.VideoProperties;

@Configuration
@EnableConfigurationProperties(VideoProperties.class)
public class ClientConfig {

    @Bean
    public VideoClient videoClient(final RestClient.Builder builder, final VideoProperties videoProperties) {
        var restClient = builder
                .baseUrl(videoProperties.getUrl())
                .requestInterceptor((request, body, execution) -> {
                    // Log minden kimenő kérést
                    System.out.println("=== OUTGOING REQUEST DEBUG ===");
                    System.out.println("URL: " + request.getURI());
                    System.out.println("Method: " + request.getMethod());
                    System.out.println("Headers: " + request.getHeaders());
                    return execution.execute(request, body);
                })
                .build();
        var factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(VideoClient.class);
    }
}
