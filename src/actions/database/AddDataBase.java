package actions.database;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Movie;
import fileio.ActionInput;
import fileio.Output;
import main.State;

public final class AddDataBase extends Action {

    private final Movie movie;
    public AddDataBase(final ActionInput actionInput) {
        super(actionInput);
        movie = new Movie(actionInput.getAddedMovie());
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();

        if (state.getDataBase().getMovies().stream()
                .anyMatch(movie1 -> movie1.getName().equals(this.movie.getName()))) {
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        state.getDataBase().addMovie(this.movie);
        return null;
    }
}
