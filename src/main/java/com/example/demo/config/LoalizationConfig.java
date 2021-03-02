package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;
@Configuration
public class LoalizationConfig implements WebMvcConfigurer {
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver =new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.US);
        cookieLocaleResolver.setCookieName("language");
        return cookieLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("local");
        return lci;

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( localeChangeInterceptor());
    }
}
