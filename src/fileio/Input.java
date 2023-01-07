package fileio;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public final class Input {
    @Getter
    @Setter
    private ArrayList<UserInput> users;

    @Getter
    @Setter
    private ArrayList<MovieInput> movies;

    @Getter
    @Setter
    private ArrayList<ActionInput> actions;
}
