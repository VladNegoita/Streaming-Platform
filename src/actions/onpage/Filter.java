package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Movie;
import entities.User;
import fileio.ActionInput;
import fileio.OutputFormatter;
import lombok.Getter;
import lombok.Setter;
import main.Helpers;
import main.State;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public final class Filter extends Action {
    @Getter
    @Setter
    private ActionInput.Filters filters;

    public Filter(final ActionInput actionInput) {
        super(actionInput);
        this.filters = actionInput.getFilters();
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();
        if (state.getCurrentPage() != State.Page.MOVIES) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }
        if (this.filters.getContains() != null) {
            State.getSTATE().getVisibleMovies().removeIf(toBeRemoved());
        }
        if (this.filters.getSort() != null) {
            State.getSTATE().getVisibleMovies().sort(sortCriteria()::apply);
        }

        return OutputFormatter.getOutput(null, Helpers
                .getDeepCopyMovies(state.getVisibleMovies()), new User(state.getCurrentUser()));
    }

    private Predicate<Movie> toBeRemoved() {
        return (movie -> {
            ActionInput.Contains contains = this.getFilters().getContains();
            if (contains.getActors() != null) {
                for (String actor : contains.getActors()) {
                    if (!movie.getActors().contains(actor)) {
                        return true;
                    }
                }
            }

            if (contains.getGenre() != null) {
                for (String genre : contains.getGenre()) {
                    if (!movie.getGenres().contains(genre)) {
                        return true;
                    }
                }
            }

            return false;
        });
    }

    private BiFunction<Movie, Movie, Integer> sortCriteria() {
        return ((movie1, movie2) -> {
            ActionInput.Sort sortOrder = this.getFilters().getSort();
            if (sortOrder.getRating() == null) {
                if (sortOrder.getDuration().equals("decreasing")) {
                    return movie2.getDuration() - movie1.getDuration();
                }
                return movie1.getDuration() - movie2.getDuration();
            }

            if (sortOrder.getDuration() == null) {
                if (sortOrder.getRating().equals("decreasing")) {
                    return Double.compare(movie2.getRating(), movie1.getRating());
                }
                return Double.compare(movie1.getRating(), movie2.getRating());
            }

            if (movie1.getDuration() != movie2.getDuration()
                    && sortOrder.getDuration().equals("decreasing")) {
                return Integer.compare(movie2.getDuration(), movie1.getDuration());
            } else if (movie1.getDuration() != movie2.getDuration()) {
                return Integer.compare(movie1.getDuration(), movie2.getDuration());
            } else if (sortOrder.getRating().equals("decreasing")) {
                return Double.compare(movie2.getRating(), movie1.getRating());
            } else {
                return Double.compare(movie1.getRating(), movie2.getRating());
            }
        });
    }
}
