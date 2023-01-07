package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class Notification {

    @Getter
    private String movieName;

    @Getter
    private String message;
}
