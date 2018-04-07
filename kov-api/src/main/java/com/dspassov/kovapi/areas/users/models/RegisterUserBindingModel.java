package com.dspassov.kovapi.areas.users.models;

import com.dspassov.kovapi.areas.game.common.GameDomainConstants;
import com.dspassov.kovapi.areas.users.common.UserDomainConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class RegisterUserBindingModel {

    @NotEmpty(message = "Username cannot be empty.")
    @Email(message = "Valid email is required.")
    private String username;

    @NotEmpty(message = "Password cannot be empty.")
    @Size(min = UserDomainConstants.PASSWORD_MIN_LENGTH,
            message = "Password must contain at least "
                    + UserDomainConstants.PASSWORD_MIN_LENGTH + " symbols.")
    private String password;

    @NotEmpty(message = "Hero name cannot be empty.")
    @Size(min = GameDomainConstants.HERO_NAME_MIN_LENGTH, max = GameDomainConstants.HERO_NAME_MAX_LENGTH,
            message = "Hero name must contain between "
                    + GameDomainConstants.HERO_NAME_MIN_LENGTH
                    + " and " + GameDomainConstants.HERO_NAME_MAX_LENGTH
                    + " symbols.")
    private String heroName;

    public RegisterUserBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }
}
