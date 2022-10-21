package wns.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToLongConverter implements Converter<String, Long> {
    @Override
    public Long convert(String source) {
        if(source.isEmpty() || source.equals("undefined"))
            return 0L;
        return Long.parseLong(source);
    }
}
