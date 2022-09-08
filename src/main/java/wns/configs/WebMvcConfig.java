package wns.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wns.utils.StringToIntConverter;
import wns.utils.StringToLongConverter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final StringToLongConverter stringToLongConverter;
    private final StringToIntConverter stringToIntConverter;

    public WebMvcConfig(StringToLongConverter stringToLongConverter, StringToIntConverter stringToIntConverter) {
        this.stringToLongConverter = stringToLongConverter;
        this.stringToIntConverter = stringToIntConverter;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static")
                .addResourceLocations("classpath:/static/**");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("authorization.html");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToLongConverter);
        registry.addConverter(stringToIntConverter);
    }
}
