package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionInput;
import fileio.Output;
import main.State;

public final class BuyPremiumAccount extends Action {
    private static final int PREMIUMPRICE = 10;
    public BuyPremiumAccount(final ActionInput actionInput) {
        super(actionInput);
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();
        if (state.getCurrentPage() != State.Page.UPGRADES) {
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        if (state.getCurrentUser().getTokensCount() < PREMIUMPRICE) {
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        if (state.getCurrentUser().getCredentials().getAccountType().equals("premium")) {
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        state.getCurrentUser().getCredentials().setAccountType("premium");
        state.getCurrentUser().setTokensCount(state
                .getCurrentUser().getTokensCount() - PREMIUMPRICE);

        return null;
    }
}
