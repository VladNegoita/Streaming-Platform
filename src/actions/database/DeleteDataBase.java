package actions.database;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionInput;
import fileio.OutputFormatter;
import main.State;

import java.util.ArrayList;

public final class DeleteDataBase extends Action {
    private final String movieName;
    public DeleteDataBase(final ActionInput actionInput) {
        super(actionInput);
        this.movieName = actionInput.getDeletedMovie();
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();

        if (state.getDataBase().getMovies().stream()
                .noneMatch(movie1 -> movie1.getName().equals(this.movieName))) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        state.getDataBase().deleteMovie(this.movieName);
        return null;
    }
}
