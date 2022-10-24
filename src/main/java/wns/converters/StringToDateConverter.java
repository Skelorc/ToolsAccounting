package wns.converters;

import net.bytebuddy.asm.Advice;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class StringToDateConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        if(source.isEmpty())
            return LocalDateTime.now();
        String replace = source.replace("T", " ");
        return LocalDateTime.parse(replace);
    }
}
