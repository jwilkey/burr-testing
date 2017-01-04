package com.tentkeep.burrtesting;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:../test/application.properties")
@SpringBootApplication
public class TestApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TestApplication.class);
    }

    @Bean
    public MockViewProviderDialect viewResolverDialect() {
        return new MockViewProviderDialect();
    }

    @Bean
    public MockMapBuilderDialect mapBuilderDialect() {
        return new MockMapBuilderDialect();
    }

}
