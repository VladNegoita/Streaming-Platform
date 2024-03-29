package actions.changepage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Movie;
import entities.User;
import fileio.ActionInput;
import fileio.Output;
import main.Helpers;
import main.State;

import java.util.ArrayList;

public final class ChangePageMovies extends Action {
    private static final int PAGENUMBER = 4;
    public ChangePageMovies(final ActionInput actionInput) {
        super(actionInput);
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();
        if (!state.getAccessibility().getAdjacent().get(state
                .getCurrentPage().ordinal()).get(PAGENUMBER)) {
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        if (state.getCurrentPage() != State.Page.MOVIES) {
            state.getPreviousPages().push(state.getCurrentPage());
            if (state.getCurrentPage() == State.Page.SEE_DETAILS) {
                state.getLastMovie().push(state.getVisibleMovies().get(0));
            }
        }

        state.setCurrentPage(State.Page.MOVIES);
        state.setVisibleMovies(new ArrayList<>());
        for (Movie movie : state.getDataBase().getMovies()) {
            if (!movie.getCountriesBanned().contains(state
                    .getCurrentUser().getCredentials().getCountry())) {
                state.getVisibleMovies().add(movie);
            }
        }

        return new Output.OutputBuilder()
                .addMovies(Helpers.getDeepCopyMovies(state.getVisibleMovies()))
                .addUser(new User(state.getCurrentUser())).build().transform();
    }
}
