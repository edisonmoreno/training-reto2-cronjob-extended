package com.edisonmoreno.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.Objects;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Configuration
public class RestServiceConfig {

    @Value("${adapter.rest-consumer.timeout}")
    private int timeout;
    @Value("${adapter.rest-consumer.ms-queries}")
    private String urlQueries;
    @Value("${adapter.rest-consumer.ms-commands}")
    private String urlCommands;

    @Bean("webClientQueries")
    public WebClient getWebClientQueries() {
        return WebClient.builder()
                .baseUrl(urlQueries)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(getClientHttpConnector())
                .build();
    }

    @Bean("webClientCommands")
    public WebClient getWebClientCommands() {
        return WebClient.builder()
                .baseUrl(urlCommands)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(getClientHttpConnector())
                .build();
    }

    private ClientHttpConnector getClientHttpConnector() {
        SslContext sslContext = null;
        try {
            sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SslContext finalSslContext = sslContext;
        return new ReactorClientHttpConnector(HttpClient.create()
                .secure(sslContextSpec -> sslContextSpec.sslContext(Objects.requireNonNull(finalSslContext)))
                .compress(true)
                .keepAlive(true)
                .option(CONNECT_TIMEOUT_MILLIS, timeout)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(timeout, MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(timeout, MILLISECONDS));
                }));
    }

}
