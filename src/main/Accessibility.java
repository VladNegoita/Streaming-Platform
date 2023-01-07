package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class Accessibility {

    @Getter
    @Setter
    private ArrayList<ArrayList<Boolean>> adjacent;

    /**
     *
     * @param fileName the name of the configuration file
     * @return an object containing the adjacency matrix of the pages
     * @throws IOException
     */
    public static Accessibility load(final String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(fileName).getAbsoluteFile(), Accessibility.class);
    }
}
