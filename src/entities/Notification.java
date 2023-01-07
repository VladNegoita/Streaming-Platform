package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class Notification {

    @Getter
    private final String movieName;

    @Getter
    private final String message;
}
