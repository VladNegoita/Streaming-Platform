package fileio;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public final class MovieInput {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int year;

    @Getter
    @Setter
    private int duration;

    @Getter
    @Setter
    private ArrayList<String> genres;

    @Getter
    @Setter
    private ArrayList<String> actors;

    @Getter
    @Setter
    private ArrayList<String> countriesBanned;
}
