package actions.changepage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionInput;
import fileio.Output;
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
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        state.setCurrentPage(State.Page.HOME_NO_AUTH);
        state.setCurrentUser(null);
        state.setVisibleMovies(new ArrayList<>());
        state.setLastMovie(new Stack<>());
        state.setPreviousPages(new Stack<>());
        return null;
    }
}
