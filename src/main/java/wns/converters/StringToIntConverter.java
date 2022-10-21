package wns.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToIntConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String source) {
        if(source.isEmpty())
            return 0;
        return Integer.parseInt(source);
    }
}
