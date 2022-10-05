package wns.utils;

import net.bytebuddy.asm.Advice;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StringToDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        if(source.isEmpty())
            return LocalDate.now();
        return LocalDate.parse(source);
    }
}
