package com.ada.moneymorpher.profile;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfileDto {

    private String email;
    private String username;
    private String password;

    public ProfileDto(String username) {
        this.username = username;
    }

}
