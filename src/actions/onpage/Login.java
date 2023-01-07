package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.User;
import fileio.ActionInput;
import fileio.Output;
import lombok.Getter;
import lombok.Setter;
import database.DataBase;
import main.State;

import java.util.ArrayList;

public final class Login extends Action {
    @Getter
    @Setter
    private String name, password;

    public Login(final ActionInput actionInput) {
        super(actionInput);
        this.name = actionInput.getCredentials().getName();
        this.password = actionInput.getCredentials().getPassword();
    }

    @Override
    public ObjectNode apply() {
        if (State.getSTATE().getCurrentPage() != State.Page.LOGIN) {
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        DataBase dataBase = State.getSTATE().getDataBase();
        if (dataBase.getUsers().stream().noneMatch(user -> user.getCredentials()
                .getName().equals(this.name)
                && user.getCredentials().getPassword().equals(this.password))) {
            State.emptyState();
            return new Output.OutputBuilder().addError("Error").build().transform();
        }

        for (User user : dataBase.getUsers()) {
            if (user.getCredentials().getName().equals(this.name)
                    && user.getCredentials().getPassword().equals(this.password)) {
                State.getSTATE().setCurrentUser(user);
                State.getSTATE().setVisibleMovies(new ArrayList<>());
                State.getSTATE().setCurrentPage(State.Page.HOME_AUTH);
                return new Output.OutputBuilder().addUser(new User(user)).build().transform();
            }
        }

        return null;
    }
}
