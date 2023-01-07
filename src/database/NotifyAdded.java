package database;

import entities.Movie;
import entities.Notification;
import entities.User;
import main.State;

import java.util.Observable;
import java.util.Observer;

public final class NotifyAdded implements Observer {
    @Override
    public void update(final Observable o, final Object arg) {
        String movieName = (String) arg;
        State state = State.getSTATE();

        if (state.getDataBase().getMovies().stream()
                .noneMatch(movie1 -> movie1.getName().equals(movieName))) {
            return;
        }

        Movie recentlyAddedMovie = null;
        for (Movie movie : state.getDataBase().getMovies()) {
            if (movie.getName().equals(movieName)) {
                recentlyAddedMovie = movie;
                break;
            }
        }

        assert recentlyAddedMovie != null;

        for (User user : state.getDataBase().getUsers()) {

            if (recentlyAddedMovie.getCountriesBanned()
                    .contains(user.getCredentials().getCountry())) {
                continue;
            }

            for (String genre : recentlyAddedMovie.getGenres()) {
                if (user.getSubscribedGenres().contains(genre)) {
                    user.getNotifications().add(new Notification(movieName, "ADD"));
                    break;
                }
            }
        }
    }
}
