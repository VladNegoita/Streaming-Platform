package actions.other;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Movie;
import entities.Notification;
import entities.User;
import fileio.ActionInput;
import fileio.OutputFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import main.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Recommend extends Action {

    @AllArgsConstructor
    public final class Genre {
        @Getter
        private final String genre;

        @Getter
        private final int likes;
    };

    public Recommend(final ActionInput actionInput) {
        super(actionInput);
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();
        if (state.getCurrentUser() == null) {
            return null;
        }

        if (!state.getCurrentUser().getCredentials().getAccountType().equals("premium")) {
            return null;
        }

        Map<String, Integer> genres = new HashMap<>();
        for (Movie movie : state.getCurrentUser().getLikedMovies()) {
            for (String genre : movie.getGenres()) {
                if (genres.containsKey(genre)) {
                    genres.put(genre, genres.get(genre) + 1);
                } else {
                    genres.put(genre, 1);
                }
            }
        }

        ArrayList<Genre> sortedGenres = new ArrayList<>();
        genres.forEach((genre, value) -> sortedGenres.add(new Genre(genre, value)));
        sortedGenres.sort((genre1, genre2) -> {
            if (genre1.getLikes() != genre2.getLikes()) {
                return genre2.getLikes() - genre1.getLikes();
            }
            return genre1.getGenre().compareTo(genre2.getGenre());
        });

        state.getDataBase().getMovies()
                .sort((movie1, movie2) -> movie2.getNumLikes() - movie1.getNumLikes());

        for (Genre genre : sortedGenres) {
            for (Movie movie : state.getDataBase().getMovies()) {
                if (movie.getCountriesBanned().contains(state
                        .getCurrentUser().getCredentials().getCountry())) {
                    continue;
                }
                if (!movie.getGenres().contains(genre.getGenre())) {
                    continue;
                }
                if (state.getCurrentUser().getWatchedMovies().stream()
                        .anyMatch(movie1 -> movie1.getName().equals(movie.getName()))) {
                    continue;
                }

                state.getCurrentUser().getNotifications()
                        .add(new Notification(movie.getName(), "Recommendation"));
                return OutputFormatter.getOutput(null, null, new User(state.getCurrentUser()));
            }
        }
        state.getCurrentUser().getNotifications()
                .add(new Notification("No recommendation", "Recommendation"));
        return OutputFormatter.getOutput(null, null, new User(state.getCurrentUser()));
    }
}
