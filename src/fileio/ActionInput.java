package fileio;

import entities.Credentials;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public final class ActionInput {
    @Getter
    @Setter
    private String type, page, feature, count, startsWith, movie, subscribedGenre, deletedMovie;

    @Getter
    @Setter
    private MovieInput addedMovie;

    @Getter
    @Setter
    private int rate;

    @Getter
    @Setter
    private Credentials credentials;

    @Getter
    @Setter
    private Filters filters;

    public static final class Sort {
        @Getter
        @Setter
        private String rating, duration;
    }

    public static final class Contains {
        @Getter
        @Setter
        private ArrayList<String> actors;

        @Getter
        @Setter
        private ArrayList<String> genre;
    }

    public static final class Filters {
        @Getter
        @Setter
        private Sort sort;

        @Getter
        @Setter
        private Contains contains;
    }
}
