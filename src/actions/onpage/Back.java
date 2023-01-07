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
                break;
            case SEE_DETAILS:
                actionInput.setMovie(state.getLastMovie().peek().getName());
                state.getLastMovie().pop();
                action = new ChangePageSeeDetails(actionInput);
                break;
            case UPGRADES:
                action = new ChangePageUpgrades(actionInput);
                break;
            default:
                state.setVisibleMovies(new ArrayList<>());
                return null;
        }

        return action.apply();
    }
}
