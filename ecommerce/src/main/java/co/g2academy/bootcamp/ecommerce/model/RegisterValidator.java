package co.g2academy.bootcamp.ecommerce.model;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class RegisterValidator {

    private static final String EMAIL_REGEX = "^(.+)@(.+)$";

    public Boolean validate(Register newUser) {
        return validateUserNameAsEmailAddress(newUser) && validatePasswordAndConfirmPasswordIsTheSame(newUser);
    }

    public Boolean validateUserNameAsEmailAddress(Register newUser) {
        // regex input Email Address
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return newUser.getUserName() != null && pattern.matcher(newUser.getUserName()).matches();
    }

    public Boolean validatePasswordAndConfirmPasswordIsTheSame(Register newUser){
        return newUser.getPassword().trim().length() > 0 && newUser.getPassword().equals(newUser.getConfirmPassword());
    }

}
