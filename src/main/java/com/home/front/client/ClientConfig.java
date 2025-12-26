package com.home.front.client;

import com.home.front.properties.TunnelProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@EnableConfigurationProperties(TunnelProperties.class)
public class ClientConfig {

  @Bean
  RestClient.Builder restClientBuilder() {
    return RestClient.builder();
  }

  @Bean
  public VideoClient videoClient(
      final RestClient.Builder builder, final TunnelProperties videoProperties) {
    var restClient = builder.baseUrl(videoProperties.getUrl()).build();
    var factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
    return factory.createClient(VideoClient.class);
  }
}
