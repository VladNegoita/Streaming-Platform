package actions.changepage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionInput;
import fileio.OutputFormatter;
import main.State;

import java.util.ArrayList;

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
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        state.setCurrentPage(State.Page.REGISTER);
        return null;
    }
}