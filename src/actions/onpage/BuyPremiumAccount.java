package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionInput;
import fileio.OutputFormatter;
import main.State;

import java.util.ArrayList;

public final class BuyPremiumAccount extends Action {
    private static final int PREMIUMPRICE = 10;
    public BuyPremiumAccount(final ActionInput actionInput) {
        super(actionInput);
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();
        if (state.getCurrentPage() != State.Page.UPGRADES) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        if (state.getCurrentUser().getTokensCount() < PREMIUMPRICE) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        if (state.getCurrentUser().getCredentials().getAccountType().equals("premium")) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        state.getCurrentUser().getCredentials().setAccountType("premium");
        state.getCurrentUser().setTokensCount(state
                .getCurrentUser().getTokensCount() - PREMIUMPRICE);

        return null;
    }
}
