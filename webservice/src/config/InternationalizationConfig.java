package com.hoaxify.webservice.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class InternationalizationConfig {

    // Accept-Language header'a göre locale belirler
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH); // default dili EN yap
        return resolver;
    }

    // Mesaj kaynaklarını validator ile bağlar
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    // Mesaj dosyalarını yükler
    @Bean
    public MessageSource messageSource() {
        org.springframework.context.support.ReloadableResourceBundleMessageSource messageSource =
                new org.springframework.context.support.ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages"); // messages.properties dosyaları
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
