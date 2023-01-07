import actions.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import entities.Movie;
import fileio.ActionInput;
import fileio.Input;
import fileio.MovieInput;
import fileio.UserInput;
import main.Accessibility;
import main.DataBase;
import main.State;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class Main {
    private Main() {}

    /**
     *
     * @param args -> input and output files
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {

        String inputFile = null, outputFile = null;
        try {
            inputFile = args[0];
            outputFile = args[1];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops! You should run Test!");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(inputFile), Input.class);
        ArrayNode output = objectMapper.createArrayNode();

        ArrayList<User> users = new ArrayList<>();
        for (UserInput userInput : inputData.getUsers()) {
            users.add(new User(userInput));
        }

        ArrayList<Movie> movies = new ArrayList<>();
        for (MovieInput movieInput : inputData.getMovies()) {
            movies.add(new Movie(movieInput));
        }

        DataBase dataBase = new DataBase(users, movies);

        final String configName = "src/main/accessibility.json";
        Accessibility accessibility = Accessibility.load(configName);

        State state = State.getSTATE();
        State.emptyState();
        state.setDataBase(dataBase);
        state.setAccessibility(accessibility);

        for (ActionInput actionInput : inputData.getActions()) {
            Action action = Action.createAction(actionInput);
            ObjectNode actionOutput = action.apply();
            if (actionOutput != null) {
                output.add(actionOutput);
            }
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(outputFile), output);
    }
}
