package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionInput;
import fileio.OutputFormatter;
import lombok.Getter;
import main.State;

import java.util.ArrayList;

public final class Subscribe extends Action {

    @Getter
    private final String subscribedGenre;
    public Subscribe(final ActionInput actionInput) {
        super(actionInput);
        this.subscribedGenre = actionInput.getSubscribedGenre();
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();
        if (state.getCurrentPage() != State.Page.SEE_DETAILS) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        if (!state.getVisibleMovies().get(0).getGenres().contains(this.subscribedGenre)) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        if (state.getCurrentUser().getSubscribedGenres().contains(this.subscribedGenre)) {
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        state.getCurrentUser().getSubscribedGenres().add(this.subscribedGenre);
        return null;
    }
}
