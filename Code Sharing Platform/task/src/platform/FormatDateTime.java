package platform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatDateTime {

    private static final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss:SSS";

    public static String formatTime(LocalDateTime date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        String formatDateTime = date.format(formatter);

        return formatDateTime;
    }
}
