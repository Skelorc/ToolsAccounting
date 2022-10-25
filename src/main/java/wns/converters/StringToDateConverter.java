package wns.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StringToDateConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        if(source.isEmpty())
            return LocalDateTime.now();
        return LocalDateTime.parse(source);
    }
}
