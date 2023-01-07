package actions.database;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Movie;
import fileio.ActionInput;

public final class AddDataBase extends Action {

    private final Movie movie;
    public AddDataBase(ActionInput actionInput) {
        super(actionInput);
        movie = new Movie(actionInput.getAddedMovie());
    }

    @Override
    public ObjectNode apply() {
        // TODO
        return null;
    }
}
