package com.rich.bryan.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.AssertTrue;

public class RegisterForm {

//    @Email(message = "Must be a valid Email Address")
    @NotBlank(message = "Cannot be blank")
    private String email;

    @NotBlank(message = "Cannot be blank")
    private String password;

    @NotBlank(message = "Cannot be Blank")
    private String confirm;

    private String unique;

    private boolean valid;

    @AssertTrue(message="Passwords Must Match")
    public boolean isValid() {
        return this.password.equals(this.confirm);
    }

    public boolean getValid(){
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }
}
