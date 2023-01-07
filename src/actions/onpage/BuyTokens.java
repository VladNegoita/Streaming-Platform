package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionInput;
import fileio.Output;
import lombok.Getter;
import lombok.Setter;
import main.State;

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
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        if (state.getCurrentUser().getCredentials().getBalance() < this.count) {
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        state.getCurrentUser().setTokensCount(state.getCurrentUser().getTokensCount() + this.count);
        state.getCurrentUser().getCredentials().setBalance(state
                .getCurrentUser().getCredentials().getBalance() - this.count);

        return null;
    }
}
