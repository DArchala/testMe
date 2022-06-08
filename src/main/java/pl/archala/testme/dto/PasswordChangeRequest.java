package pl.archala.testme.dto;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeRequest {

    @NotEmpty
    @Size(min = 3, max = 60)
    private String username;

    @NotEmpty
    @Pattern(regexp = "^$|^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    private String email;

    @NotEmpty
    @Size(min = 6, max = 30)
    private String currentPassword;

    @NotEmpty
    @Size(min = 6, max = 30)
    private String newPassword;

}
