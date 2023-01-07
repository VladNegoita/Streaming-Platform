package fileio;

import entities.Credentials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class UserInput {
    @Getter
    @Setter
    private Credentials credentials;
}
