package actions.changepage;

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

public final class ChangePageSeeDetails extends Action {
    private static final int PAGENUMBER = 5;
    @Getter
    @Setter
    private String movie;

    public ChangePageSeeDetails(final ActionInput actionInput) {
        super(actionInput);
        this.movie = actionInput.getMovie();
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();

        if (state.getCurrentPage() == State.Page.SEE_DETAILS) {
            return null;
        }

        if (state.getCurrentPage() != State.Page.MOVIES) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        Movie movieTarget = null;
        for (Movie testMovie : state.getVisibleMovies()) {
            if (testMovie.getName().equals(this.movie)) {
                movieTarget = testMovie;
            }
        }

        if (movieTarget == null) {
            state.setCurrentPage(State.Page.MOVIES);
            state.setVisibleMovies(new ArrayList<>());
            for (Movie movieDataBase : state.getDataBase().getMovies()) {
                if (!movieDataBase.getCountriesBanned().contains(state
                        .getCurrentUser().getCredentials().getCountry())) {
                    state.getVisibleMovies().add(movieDataBase);
                }
            }
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        state.setVisibleMovies(new ArrayList<>());
        state.getVisibleMovies().add(movieTarget);
        state.setLastMovie(movieTarget);
        state.setCurrentPage(State.Page.SEE_DETAILS);
        return OutputFormatter.getOutput(null, Helpers
                .getDeepCopyMovies(state.getVisibleMovies()), new User(state.getCurrentUser()));
    }
}
