package helper;

import java.time.LocalDateTime;

@FunctionalInterface
public interface DateTimeFormat {
    String formatter(LocalDateTime ldt);
}
