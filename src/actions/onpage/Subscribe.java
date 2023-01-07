package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionInput;
import fileio.Output;
import lombok.Getter;
import main.State;

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
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        if (!state.getVisibleMovies().get(0).getGenres().contains(this.subscribedGenre)) {
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        if (state.getCurrentUser().getSubscribedGenres().contains(this.subscribedGenre)) {
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        state.getCurrentUser().getSubscribedGenres().add(this.subscribedGenre);
        return null;
    }
}
