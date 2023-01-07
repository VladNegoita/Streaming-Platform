package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.UserInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
public final class User {
    public User(final User other) {
        this.credentials = new Credentials(other.credentials);
        this.tokensCount = other.tokensCount;
        this.numFreePremiumMovies = other.numFreePremiumMovies;

        this.purchasedMovies = new ArrayList<>();
        for (Movie movie : other.purchasedMovies) {
            this.purchasedMovies.add(new Movie(movie));
        }

        this.watchedMovies = new ArrayList<>();
        for (Movie movie : other.watchedMovies) {
            this.watchedMovies.add(new Movie(movie));
        }

        this.likedMovies = new ArrayList<>();
        for (Movie movie : other.likedMovies) {
            this.likedMovies.add(new Movie(movie));
        }

        this.ratedMovies = new ArrayList<>();
        for (Movie movie : other.ratedMovies) {
            this.ratedMovies.add(new Movie(movie));
        }

        this.notifications = new ArrayList<>();
        this.notifications.addAll(other.getNotifications());

        this.subscribedGenres = new ArrayList<>();
        this.subscribedGenres.addAll(other.getSubscribedGenres());
    }

    @Getter
    @Setter
    private Credentials credentials;

    @Getter
    @Setter
    private int tokensCount, numFreePremiumMovies;

    @Getter
    @Setter
    private ArrayList<Movie> purchasedMovies;

    @Getter
    @Setter
    private ArrayList<Movie> watchedMovies;

    @Getter
    @Setter
    private ArrayList<Movie> likedMovies;

    @Getter
    @Setter
    private ArrayList<Movie> ratedMovies;

    @Getter
    @Setter
    private ArrayList<Notification> notifications;

    @Getter
    @Setter
    @JsonIgnore
    private ArrayList<String> subscribedGenres;

    private static final int FREEMOVIES = 15;
    public User(final UserInput userInput) {
        this(userInput.getCredentials(), 0, FREEMOVIES,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
    }
}
