package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Movie;
import entities.User;

import java.util.ArrayList;

public final class OutputFormatter {
    private OutputFormatter() {
    }

    /**
     *
     * @param error error message that should be printed
     * @param movies array of movies that should be printed
     * @param user user information that should be printed
     * @return objectNode for printing purposes
     */
    public static ObjectNode getOutput(final String error,
                                       final ArrayList<Movie> movies,
                                       final User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode output = objectMapper.createObjectNode();

        output.put("error", error);
        output.putPOJO("currentMoviesList", movies);
        output.putPOJO("currentUser", user);

        return output;
    }
}
