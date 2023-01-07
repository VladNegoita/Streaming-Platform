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

public final class Purchase extends Action {
    public Purchase(final ActionInput actionInput) {
        super(actionInput);
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();
        if (state.getCurrentPage() != State.Page.SEE_DETAILS) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        Movie movie = state.getVisibleMovies().get(0);

        if (state.getCurrentUser().getPurchasedMovies().stream()
                .anyMatch(movie1 -> movie1.getName().equals(movie.getName()))) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        if (state.getCurrentUser().getCredentials()
                .getAccountType().equals("premium")
                && state.getCurrentUser().getNumFreePremiumMovies() > 0) {
            state.getCurrentUser().setNumFreePremiumMovies(state
                    .getCurrentUser().getNumFreePremiumMovies() - 1);
            state.getCurrentUser().getPurchasedMovies().add(movie);
            return OutputFormatter.getOutput(null, Helpers
                    .getDeepCopyMovies(state.getVisibleMovies()), new User(state.getCurrentUser()));
        }

        if (state.getCurrentUser().getTokensCount() >= 2) {
            state.getCurrentUser().setTokensCount(state.getCurrentUser().getTokensCount() - 2);
            state.getCurrentUser().getPurchasedMovies().add(movie);
            return OutputFormatter.getOutput(null, Helpers
                    .getDeepCopyMovies(state.getVisibleMovies()), new User(state.getCurrentUser()));
        }

        return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
    }
}
