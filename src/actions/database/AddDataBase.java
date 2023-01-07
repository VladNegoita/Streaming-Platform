package actions.database;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Movie;
import fileio.ActionInput;
import fileio.OutputFormatter;
import main.State;

import java.util.ArrayList;

public final class AddDataBase extends Action {

    private final Movie movie;
    public AddDataBase(ActionInput actionInput) {
        super(actionInput);
        movie = new Movie(actionInput.getAddedMovie());
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();

        if (state.getDataBase().getMovies().stream().anyMatch(movie1 -> movie1.getName().equals(this.movie.getName()))) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        state.getDataBase().addMovie(this.movie);
        return null;
    }
}
