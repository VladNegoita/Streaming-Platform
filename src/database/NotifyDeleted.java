package database;

import entities.Notification;
import entities.User;
import main.State;

import java.util.Observable;
import java.util.Observer;

public final class NotifyDeleted implements Observer {

    @Override
    public void update(final Observable o, final Object arg) {
        String movieName = (String) arg;
        State state = State.getSTATE();

        if (state.getDataBase().getMovies().stream()
                .anyMatch(movie1 -> movie1.getName().equals(movieName))) {
            return;
        }

        for (User user : state.getDataBase().getUsers()) {

            if (user.getPurchasedMovies().stream()
                    .noneMatch(movie -> movie.getName().equals(movieName))) {
                continue;
            }

            if (user.getCredentials().getAccountType().equals("premium")) {
                user.setNumFreePremiumMovies(user.getNumFreePremiumMovies() + 1);
            } else {
                user.setTokensCount(user.getTokensCount() + 2);
            }

            user.getNotifications().add(new Notification(movieName, "DELETE"));
            user.getPurchasedMovies().removeIf(movie -> movie.getName().equals(movieName));
            user.getWatchedMovies().removeIf(movie -> movie.getName().equals(movieName));
            user.getRatedMovies().removeIf(movie -> movie.getName().equals(movieName));
            user.getLikedMovies().removeIf(movie -> movie.getName().equals(movieName));

            if (state.getVisibleMovies() != null) {
                state.getVisibleMovies().removeIf(movie -> movie.getName().equals(movieName));
            }
        }
    }
}
