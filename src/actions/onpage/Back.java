package actions.onpage;

import actions.Action;
import actions.changepage.ChangePageMovies;
import actions.changepage.ChangePageSeeDetails;
import actions.changepage.ChangePageUpgrades;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionInput;
import fileio.OutputFormatter;
import main.State;

import java.util.ArrayList;

public final class Back extends Action {
    public Back(ActionInput actionInput) {
        super(actionInput);
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();
        if (state.getCurrentUser() == null)
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);

        if (state.getPreviousPages().size() == 0)
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);

        switch (state.getPreviousPages().peek()) {
            case REGISTER:
            case LOGIN:
            case HOME_NO_AUTH:
                return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        state.setCurrentPage(state.getPreviousPages().peek());
        state.getPreviousPages().pop();

        ActionInput actionInput = new ActionInput();
        Action action;

        switch (state.getCurrentPage()) {
            case MOVIES:
                action = new ChangePageMovies(actionInput);
            case SEE_DETAILS:
                actionInput.setMovie(state.getLastMovie().getName());
                action = new ChangePageSeeDetails(actionInput);
            default:
                action = new ChangePageUpgrades(actionInput);
        }
        return action.apply();
    }
}
