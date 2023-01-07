package actions.database;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionInput;
import fileio.Output;
import main.State;

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
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        state.getDataBase().deleteMovie(this.movieName);
        return null;
    }
}
