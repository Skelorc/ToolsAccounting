package wns.configs;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wns.converters.StringToDateConverter;
import wns.converters.StringToIntConverter;
import wns.converters.StringToLongConverter;
import wns.converters.StringToSetPhotos;

@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final StringToLongConverter stringToLongConverter;
    private final StringToIntConverter stringToIntConverter;
    private final StringToDateConverter stringToDateConverter;
    private final StringToSetPhotos stringToSetPhotos;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/estimates-files/**")
                .addResourceLocations("file:estimates/");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("authorization.html");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToLongConverter);
        registry.addConverter(stringToIntConverter);
        registry.addConverter(stringToDateConverter);
        registry.addConverter(stringToSetPhotos);
    }
}
