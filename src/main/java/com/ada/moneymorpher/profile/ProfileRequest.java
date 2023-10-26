package com.ada.moneymorpher.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileRequest {

    @Email
    private String email;
    @Pattern(regexp = "[\\w.]{5,20}", message = "Username must be an alphanumeric between 5 and 20 characters (lowercase, uppercase, numbers, _, .)")
    private String username;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "Password must contain 8 to 20 characters (lowercase, uppercase, numbers, special, no-sequences)")
    private String password;
}
