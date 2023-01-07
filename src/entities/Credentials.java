package entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public final class Credentials {
    public Credentials(final Credentials other) {
        this.name = other.name;
        this.password = other.password;
        this.accountType = other.accountType;
        this.country = other.country;
        this.balance = other.balance;
    }

    @Getter
    @Setter
    private String name, password, accountType, country;

    @Getter
    @Setter
    @JsonSerialize(using = ToStringSerializer.class)
    private int balance;
}
