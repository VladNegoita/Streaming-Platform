package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionInput;
import fileio.OutputFormatter;
import lombok.Getter;
import lombok.Setter;
import main.State;

import java.util.ArrayList;

public final class BuyTokens extends Action {
    @Getter
    @Setter
    private int count;
    public BuyTokens(final ActionInput actionInput) {
        super(actionInput);
        this.count = Integer.parseInt(actionInput.getCount());
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();
        if (state.getCurrentPage() != State.Page.UPGRADES) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        if (state.getCurrentUser().getCredentials().getBalance() < this.count) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        state.getCurrentUser().setTokensCount(state.getCurrentUser().getTokensCount() + this.count);
        state.getCurrentUser().getCredentials().setBalance(state
                .getCurrentUser().getCredentials().getBalance() - this.count);

        return null;
    }
}
