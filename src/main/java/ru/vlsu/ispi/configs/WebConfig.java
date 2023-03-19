package ru.vlsu.ispi.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry){
        viewControllerRegistry.addViewController("/").setViewName("mycatalog");
        viewControllerRegistry.addViewController("/hello").setViewName("profile");
        viewControllerRegistry.addViewController("/account/login").setViewName("loginForm");
        viewControllerRegistry.addViewController("/account/register").setViewName("registerForm");
    }
}
