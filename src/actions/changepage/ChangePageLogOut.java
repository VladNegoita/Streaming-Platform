package actions.changepage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionInput;
import fileio.OutputFormatter;
import main.State;

import java.util.ArrayList;
import java.util.Stack;

public final class ChangePageLogOut extends Action {
    private static final int PAGENUMBER = 0;
    public ChangePageLogOut(final ActionInput actionInput) {
        super(actionInput);
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();
        if (!state.getAccessibility().getAdjacent()
                .get(state.getCurrentPage().ordinal()).get(PAGENUMBER)) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        state.setCurrentPage(State.Page.HOME_NO_AUTH);
        state.setCurrentUser(null);
        state.setVisibleMovies(new ArrayList<>());
        state.setLastMovie(null);
        state.setPreviousPages(new Stack<>());
        return null;
    }
}
