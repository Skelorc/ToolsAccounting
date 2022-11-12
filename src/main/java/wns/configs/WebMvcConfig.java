package wns.configs;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wns.converters.*;

@Configuration
@NoArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${filepath}")
    private String filePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/estimates-files/**")
                .addResourceLocations("file:"+filePath);
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("authorization.html");
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToLongConverter());
        registry.addConverter(new StringToIntConverter());
        registry.addConverter(new StringToDateTimeConverter());
        registry.addConverter(new StringToSetPhotos());
        registry.addConverter(new StringToDateConverter());
    }
}
