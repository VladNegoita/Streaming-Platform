package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Movie;
import entities.User;
import fileio.ActionInput;
import fileio.Output;
import lombok.Getter;
import lombok.Setter;
import main.Helpers;
import main.State;

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
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        Movie movie = state.getVisibleMovies().get(0);
        if (!state.getCurrentUser().getWatchedMovies().contains(movie)) {
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        if (this.rate > MAXRATE || this.rate < MINRATE) {
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        if (movie.getRatings().containsKey(state.getCurrentUser().getCredentials().getName())) {
            int oldRate = movie.getRatings().get(state.getCurrentUser().getCredentials().getName());
            movie.setSumRatings(movie.getSumRatings() + this.rate - oldRate);
            movie.getRatings().put(state.getCurrentUser().getCredentials().getName(), this.rate);
            movie.setRating((double) movie.getSumRatings() / movie.getNumRatings());
            return new Output.OutputBuilder()
                    .addMovies(Helpers.getDeepCopyMovies(state.getVisibleMovies()))
                    .addUser(new User(state.getCurrentUser())).build().transform();
        }

        movie.setSumRatings(movie.getSumRatings() + this.rate);
        movie.setNumRatings(movie.getNumRatings() + 1);
        movie.setRating((double) movie.getSumRatings() / movie.getNumRatings());
        movie.getRatings().put(state.getCurrentUser().getCredentials().getName(), this.rate);
        state.getCurrentUser().getRatedMovies().add(movie);

        return new Output.OutputBuilder()
                .addMovies(Helpers.getDeepCopyMovies(state.getVisibleMovies()))
                .addUser(new User(state.getCurrentUser())).build().transform();
    }
}
