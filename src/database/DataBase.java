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

    /**
     * adds the desired movie into the database
     * notifies the observers in order to alert the users about the new movie
     * @param movie
     */
    public void addMovie(final Movie movie) {
        movies.add(movie);
        this.setChanged();
        this.notifyObservers(movie.getName());
    }

    /**
     * similarly, this method removes the movie from the database and notifies the users
     * @param movieName the name of the movie that shall be deleted
     */
    public void deleteMovie(final String movieName) {
        movies.removeIf(movie1 -> movie1.getName().equals(movieName));
        this.setChanged();
        this.notifyObservers(movieName);
    }
}
