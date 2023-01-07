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

        /**
         *
         * @param error - the desired message foe output
         * @return the same instance of builder for chaining purposes
         */
        public OutputBuilder addError(final String errorMessage) {
            this.error = errorMessage;
            return this;
        }

        /**
         *
         * @param movies - the list of visible movies (deep copied)
         * @return the same instance of builder for chaining purposes
         */
        public OutputBuilder addMovies(final ArrayList<Movie> movieArrayList) {
            this.movies = movieArrayList;
            return this;
        }

        /**
         *
         * @param user - the current user (deep copied)
         * @return the same instance of builder for chaining purposes
         */
        public OutputBuilder addUser(final User currentUser) {
            this.user = currentUser;
            return this;
        }

        /**
         *
         * @return the output object with the desired traits
         */
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

    /**
     *
     * @return transforms this Output object into an ObjectNode (for printing)
     */
    public ObjectNode transform() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode output = objectMapper.createObjectNode();

        output.put("error", this.error);
        output.putPOJO("currentMoviesList", this.movies);
        output.putPOJO("currentUser", this.user);

        return output;
    }
}
