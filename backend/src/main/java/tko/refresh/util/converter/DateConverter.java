package tko.refresh.util.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConverter {

    public static String format(LocalDateTime date, String format ){
        return date.format(DateTimeFormatter.ofPattern(format));
    }
}
