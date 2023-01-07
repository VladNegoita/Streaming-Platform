package main;

import entities.Movie;
import entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Stack;

public final class State {
    private static final State STATE = new State();

    private State() {
    }

    public static State getSTATE() {
        return STATE;
    }

    public enum Page {
        HOME_NO_AUTH,
        LOGIN,
        REGISTER,
        HOME_AUTH,
        MOVIES,
        SEE_DETAILS,
        UPGRADES,
    }

    @Getter
    @Setter
    private User currentUser;

    @Getter
    @Setter
    private ArrayList<Movie> visibleMovies;

    @Getter
    @Setter
    private Page currentPage;

    @Getter
    @Setter
    private Stack<Page> previousPages;

    @Getter
    @Setter
    private Movie lastMovie;

    @Getter
    @Setter
    private DataBase dataBase;

    @Getter
    @Setter
    private Accessibility accessibility;

    /**
     * Clears the STATE
     *  -> any user should be logged out
     *  -> there are no visible movies anymore
     *  -> the current page becomes Homepage Unauthenticated
     */
    public static void emptyState() {
        State.getSTATE().setCurrentPage(Page.HOME_NO_AUTH);
        State.getSTATE().setVisibleMovies(new ArrayList<>());
        State.getSTATE().setCurrentUser(null);
        State.getSTATE().setPreviousPages(new Stack<>());
    }
}
