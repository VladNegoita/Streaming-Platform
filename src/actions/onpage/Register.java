package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Credentials;
import entities.User;
import fileio.ActionInput;
import fileio.Output;
import fileio.UserInput;
import lombok.Getter;
import lombok.Setter;
import main.State;

import java.util.ArrayList;

public final class Register extends Action {
    @Getter
    @Setter
    private Credentials credentials;
    public Register(final ActionInput actionInput) {
        super(actionInput);
        this.credentials = actionInput.getCredentials();
    }

    @Override
    public ObjectNode apply() {
        State state = State.getSTATE();
        if (state.getCurrentPage() != State.Page.REGISTER) {
            State.emptyState();
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        if (state.getDataBase().getUsers().stream().anyMatch(
                user -> user.getCredentials().getName().equals(this.credentials.getName()))) {
            State.emptyState();
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        UserInput userInput = new UserInput(this.credentials);
        User user = new User(userInput);
        state.getDataBase().getUsers().add(user);
        state.setCurrentUser(user);
        state.setCurrentPage(State.Page.HOME_AUTH);
        state.setVisibleMovies(new ArrayList<>());
        return new Output.OutputBuilder()
                .addUser(new User(state.getCurrentUser())).build().transform();
    }
}
