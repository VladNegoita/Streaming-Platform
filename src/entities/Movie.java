package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import fileio.MovieInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@AllArgsConstructor
public final class Movie {
    public Movie(final Movie other) {
        this.name = other.name;
        this.year = other.year;
        this.duration = other.duration;

        this.genres = new ArrayList<>();
        this.genres.addAll(other.genres);

        this.actors = new ArrayList<>();
        this.actors.addAll(other.actors);

        this.countriesBanned = new ArrayList<>();
        this.countriesBanned.addAll(other.countriesBanned);

        this.numLikes = other.numLikes;
        this.rating = other.rating;
        this.numRatings = other.numRatings;
        this.sumRatings = other.sumRatings;

        this.ratings = new HashMap<>();
        this.ratings.putAll(other.ratings);
    }

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @JsonSerialize(using = ToStringSerializer.class)
    private int year;

    @Getter
    @Setter
    private int duration;

    @Getter
    @Setter
    private ArrayList<String> genres;

    @Getter
    @Setter
    private ArrayList<String> actors;

    @Getter
    @Setter
    private ArrayList<String> countriesBanned;

    @Getter
    @Setter
    private int numLikes;

    @Getter
    @Setter
    private double rating;

    @Getter
    @Setter
    private int numRatings;

    @Getter
    @Setter
    @JsonIgnore
    private int sumRatings;

    @Getter
    @Setter
    @JsonIgnore
    private Map<String, Integer> ratings;

    public Movie(final MovieInput movieInput) {
        this(movieInput.getName(), movieInput.getYear(), movieInput.getDuration(),
                movieInput.getGenres(), movieInput.getActors(), movieInput.getCountriesBanned(),
                0, 0, 0, 0, new HashMap<>());
    }
}
