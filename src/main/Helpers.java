package main;

import entities.Movie;

import java.util.ArrayList;

public final class Helpers {
    private Helpers() {

    }

    /**
     *
     * @param toBeCopied arrayList of movies that should be copied (for output)
     * @return deep copy of the given array
     */
    public static ArrayList<Movie> getDeepCopyMovies(final ArrayList<Movie> toBeCopied) {
        ArrayList<Movie> newMovieList = new ArrayList<>();
        for (Movie movie : toBeCopied) {
            newMovieList.add(new Movie(movie));
        }

        return newMovieList;
    }
}
