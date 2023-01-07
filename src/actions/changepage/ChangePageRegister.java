package actions.changepage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionInput;
import fileio.Output;
import main.State;

public final class ChangePageRegister extends Action {
    private static final int PAGENUMBER = 2;
    public ChangePageRegister(final ActionInput actionInput) {
        super(actionInput);
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();
        if (!state.getAccessibility().getAdjacent().get(state
                .getCurrentPage().ordinal()).get(PAGENUMBER)) {
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        state.setCurrentPage(State.Page.REGISTER);
        return null;
    }
}
