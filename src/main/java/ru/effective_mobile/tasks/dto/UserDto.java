package ru.effective_mobile.tasks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.effective_mobile.tasks.model.Role;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "Password cannot be empty")
    String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Role role;
    @NotEmpty(message = "Name cannot be empty", groups = RegistrationValidationGroup.class)
    String name;

    public interface RegistrationValidationGroup {
    }

    public interface EditValidationGroup {
    }

}

