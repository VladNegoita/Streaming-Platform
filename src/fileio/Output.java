package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Movie;
import entities.User;
import lombok.Getter;

import java.util.ArrayList;

public final class Output {
    @Getter
    private String error;

    @Getter
    private ArrayList<Movie> movies;

    @Getter
    private User user;

    public static final class OutputBuilder {

        private String error = null;
        private ArrayList<Movie> movies = new ArrayList<>();
        private User user = null;

        public OutputBuilder() {

        }

        public OutputBuilder addError(final String error) {
            this.error = error;
            return this;
        }

        public OutputBuilder addMovies(final ArrayList<Movie> movies) {
            this.movies = movies;
            return this;
        }

        public OutputBuilder addUser(final User user) {
            this.user = user;
            return this;
        }

        public Output build() {
            return new Output(this);
        }
    }

    private Output(final OutputBuilder outputBuilder) {
        this.error = outputBuilder.error;
        this.user = outputBuilder.user;
        this.movies = outputBuilder.movies;
    }

    private Output() {
    }

    public ObjectNode transform() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode output = objectMapper.createObjectNode();

        output.put("error", this.error);
        output.putPOJO("currentMoviesList", this.movies);
        output.putPOJO("currentUser", this.user);

        return output;
    }
}
