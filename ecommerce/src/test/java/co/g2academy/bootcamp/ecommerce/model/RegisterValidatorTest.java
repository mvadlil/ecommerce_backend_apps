package co.g2academy.bootcamp.ecommerce.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class RegisterValidatorTest {



    RegisterValidator validator = new RegisterValidator();



    @Test
    public void validateWithTrueObject() {
        Register register = new Register();
        register.setUserName("fadlil@gmail.com");
        register.setName("fadlil");
        register.setPassword("passwordku");
        register.setConfirmPassword("passwordku");
        boolean actual = validator.validate(register);
        assertTrue(actual);

    }

    @Test
    public void validateUserNameAsEmailAddressWithTrueObject() {
        Register register = new Register();
        register.setUserName("fadlil@gmail.com");
        register.setName("fadlil");
        register.setPassword("passwordku");
        register.setConfirmPassword("passwordku");
        boolean actual = validator.validateUserNameAsEmailAddress(register);
        assertTrue(actual);
    }

    @Test
    public void validatePasswordAndConfirmPasswordIsTheSameWithTrueObject() {
        Register register = new Register();
        register.setUserName("fadlil@gmail.com");
        register.setName("fadlil");
        register.setPassword("passwordku");
        register.setConfirmPassword("passwordku");
        boolean actual = validator.validatePasswordAndConfirmPasswordIsTheSame(register);
        assertTrue(actual);
    }

    @Test
    public void validateWithFalseObject() {
        Register register = new Register();
        register.setUserName("fadlil@gmail.com");
        register.setName("fadlil");
        register.setPassword("passwordk");
        register.setConfirmPassword("passwordku");
        boolean actual = validator.validate(register);
        assertFalse(actual);

    }

    @Test
    public void validateUserNameAsEmailAddressWithFalseObject() {
        Register register = new Register();
        register.setUserName("fadlil.gmail.com");
        register.setName("fadlil");
        register.setPassword("passwordku");
        register.setConfirmPassword("passwordku");
        boolean actual = validator.validateUserNameAsEmailAddress(register);
        assertFalse(actual);
    }

    @Test
    public void validatePasswordAndConfirmPasswordIsTheSameWithFalseObject() {
        Register register = new Register();
        register.setUserName("fadlil@gmail.com");
        register.setName("fadlil");
        register.setPassword("password");
        register.setConfirmPassword("passwordku");
        boolean actual = validator.validatePasswordAndConfirmPasswordIsTheSame(register);
        assertFalse(actual);
    }
}