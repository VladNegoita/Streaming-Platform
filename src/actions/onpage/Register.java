package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Credentials;
import entities.User;
import fileio.ActionInput;
import fileio.OutputFormatter;
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
        if (State.getSTATE().getCurrentPage() != State.Page.REGISTER) {
            State.emptyState();
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        if (State.getSTATE().getDataBase().getUsers().stream().anyMatch(
                user -> user.getCredentials().getName().equals(this.credentials.getName()))) {
            State.emptyState();
            return OutputFormatter.getOutput("Error", new ArrayList<>(), null);
        }

        UserInput userInput = new UserInput(this.credentials);
        User user = new User(userInput);
        State.getSTATE().getDataBase().getUsers().add(user);
        State.getSTATE().setCurrentUser(user);
        State.getSTATE().setCurrentPage(State.Page.HOME_AUTH);
        State.getSTATE().setVisibleMovies(new ArrayList<>());
        return OutputFormatter.getOutput(null, new ArrayList<>(), new User(user));
    }
}
