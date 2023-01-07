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

public final class RateMovie extends Action {
    private static final int MINRATE = 1, MAXRATE = 5;
    @Getter
    @Setter
    private int rate;
    public RateMovie(final ActionInput actionInput) {
        super(actionInput);
        this.rate = actionInput.getRate();
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();
        if (state.getCurrentPage() != State.Page.SEE_DETAILS) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        Movie movie = state.getVisibleMovies().get(0);
        if (!state.getCurrentUser().getWatchedMovies().contains(movie)) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        if (this.rate > MAXRATE || this.rate < MINRATE) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        if (movie.getRatings().containsKey(state.getCurrentUser().getCredentials().getName())) {
            int oldRate = movie.getRatings().get(state.getCurrentUser().getCredentials().getName());
            movie.setSumRatings(movie.getSumRatings() + this.rate - oldRate);
            movie.getRatings().put(state.getCurrentUser().getCredentials().getName(), this.rate);
            movie.setRating((double) movie.getSumRatings() / movie.getNumRatings());
            return OutputFormatter.getOutput(null, Helpers
                    .getDeepCopyMovies(state.getVisibleMovies()), new User(state.getCurrentUser()));
        }

        movie.setSumRatings(movie.getSumRatings() + this.rate);
        movie.setNumRatings(movie.getNumRatings() + 1);
        movie.setRating((double) movie.getSumRatings() / movie.getNumRatings());
        movie.getRatings().put(state.getCurrentUser().getCredentials().getName(), this.rate);
        state.getCurrentUser().getRatedMovies().add(movie);

        return OutputFormatter.getOutput(null, Helpers
                .getDeepCopyMovies(state.getVisibleMovies()), new User(state.getCurrentUser()));
    }
}
