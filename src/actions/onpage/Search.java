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

import java.util.ArrayList;

public final class Search extends Action {
    @Getter
    @Setter
    private String startsWith;

    public Search(final ActionInput actionInput) {
        super(actionInput);
        this.startsWith = actionInput.getStartsWith();
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();
        if (state.getCurrentPage() != State.Page.MOVIES) {
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        ArrayList<Movie> visibleMovies = new ArrayList<>();
        for (Movie movie : state.getVisibleMovies()) {
            if (movie.getName().startsWith(this.startsWith)) {
                visibleMovies.add(movie);
            }
        }

        if (visibleMovies.size() == 0) {
            return new Output.OutputBuilder()
                    .addUser(new User(state.getCurrentUser())).build().transform();
        }

        state.setVisibleMovies(visibleMovies);
        return new Output.OutputBuilder()
                .addMovies(Helpers.getDeepCopyMovies(state.getVisibleMovies()))
                .addUser(new User(state.getCurrentUser())).build().transform();
    }
}
