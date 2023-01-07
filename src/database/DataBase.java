package database;

import entities.Movie;
import entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Observable;

@AllArgsConstructor
public final class DataBase extends Observable {
    @Getter
    @Setter
    private ArrayList<User> users;

    @Getter
    @Setter
    private ArrayList<Movie> movies;

    public void addMovie(Movie movie) {
        movies.add(movie);
        this.setChanged();
        this.notifyObservers(movie.getName());
    }

    public void deleteMovie(String movieName) {
        movies.removeIf(movie1 -> movie1.getName().equals(movieName));
        this.setChanged();
        this.notifyObservers(movieName);
    }
}
