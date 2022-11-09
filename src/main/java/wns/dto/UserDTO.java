package wns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import wns.constants.Roles;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDTO {
    private long id;
    private String username;
    private String password;
    private String phoneNumber;
    private String fullName;
    private Roles roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO dto = (UserDTO) o;
        return Objects.equals(username, dto.username) && Objects.equals(password, dto.password) && Objects.equals(fullName, dto.fullName)&& Objects.equals(phoneNumber, dto.phoneNumber) && Objects.equals(roles, dto.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, phoneNumber,fullName, roles);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
