package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Movie;
import entities.User;
import fileio.ActionInput;
import fileio.OutputFormatter;
import main.Helpers;
import main.State;

import java.util.ArrayList;

public final class Watch extends Action {
    public Watch(final ActionInput actionInput) {
        super(actionInput);
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();
        if (state.getCurrentPage() != State.Page.SEE_DETAILS) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        Movie movie = state.getVisibleMovies().get(0);
        if (!state.getCurrentUser().getPurchasedMovies().contains(movie)) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        state.getCurrentUser().getWatchedMovies().add(movie);
        return OutputFormatter.getOutput(null, Helpers
                .getDeepCopyMovies(state.getVisibleMovies()), new User(state.getCurrentUser()));
    }
}
