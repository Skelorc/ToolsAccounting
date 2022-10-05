package wns.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class StringToSetPhotos  implements Converter<String, HashSet<String>> {
    @Override
    public HashSet<String> convert(String source) {
        String[] split = source.split(",");
        return new HashSet<>(Arrays.asList(split));
    }
}

