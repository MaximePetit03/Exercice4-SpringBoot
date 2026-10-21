package com.formation.exercice4.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CleApiInterceptor cleApiInterceptor;
    private final DureeInterceptor dureeInterceptor;

    public WebConfig(CleApiInterceptor cleApiInterceptor, DureeInterceptor dureeInterceptor) {
        this.cleApiInterceptor = cleApiInterceptor;
        this.dureeInterceptor = dureeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dureeInterceptor);

        registry.addInterceptor(cleApiInterceptor)
                .addPathPatterns("/api/v1/produits/**");
    }
}