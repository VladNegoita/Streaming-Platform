package actions.database;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionInput;

public final class DeleteDataBase extends Action {
    private final String movie;
    public DeleteDataBase(ActionInput actionInput) {
        super(actionInput);
        this.movie = actionInput.getDeletedMovie();
    }

    @Override
    public ObjectNode apply() {
        return null;
    }
}
