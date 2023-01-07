package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Movie;
import entities.User;
import fileio.ActionInput;
import fileio.Output;
import main.Helpers;
import main.State;

public final class Like extends Action {
    public Like(final ActionInput actionInput) {
        super(actionInput);
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

        movie.setNumLikes(movie.getNumLikes() + 1);
        state.getCurrentUser().getLikedMovies().add(movie);
        return new Output.OutputBuilder()
                .addMovies(Helpers.getDeepCopyMovies(state.getVisibleMovies()))
                .addUser(new User(state.getCurrentUser())).build().transform();
    }
}
