package com.home.front.client;

import com.home.front.properties.VideoProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@EnableConfigurationProperties(VideoProperties.class)
@Slf4j(topic = "FRONT")
public class ClientConfig {

  @Bean
  RestClient.Builder restClientBuilder() {
    return RestClient.builder();
  }

  @Bean
  public VideoClient videoClient(
      final RestClient.Builder builder, final VideoProperties videoProperties) {
    var restClient =
        builder
            .baseUrl(videoProperties.getUrl())
            .requestInterceptor(
                (request, body, execution) -> {
                  log.debug("URL: {}", request.getURI());
                  log.debug("Method: {}", request.getMethod());
                  log.debug("Headers: {}", request.getHeaders());
                  return execution.execute(request, body);
                })
            .build();
    var factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
    return factory.createClient(VideoClient.class);
  }
}
