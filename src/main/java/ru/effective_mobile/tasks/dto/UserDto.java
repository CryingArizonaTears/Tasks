package ru.effective_mobile.tasks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import ru.effective_mobile.tasks.model.Role;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Role role;
    String name;
}
