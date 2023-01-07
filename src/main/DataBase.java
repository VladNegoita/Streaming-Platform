package main;

import entities.Movie;
import entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@AllArgsConstructor
public final class DataBase {
    @Getter
    @Setter
    private ArrayList<User> users;

    @Getter
    @Setter
    private ArrayList<Movie> movies;
}
