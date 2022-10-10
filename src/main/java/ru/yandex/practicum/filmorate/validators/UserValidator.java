package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidator {

    public boolean validate(User user) {
        validateName(user);
        if(validateEmail(user) && validateLogin(user) && validateData(user)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateEmail(User user) {
        return !user.getEmail().isEmpty() && user.getEmail().contains("@");
    }

    private boolean validateLogin(User user) {
        return !user.getLogin().isEmpty() && !user.getLogin().contains(" ");
    }

    private void validateName(User user) {
        if(user.getName() == null) {
            user.setName(user.getLogin());
        }
    }

    private boolean validateData(User user) {
        LocalDate dateNow = LocalDate.now();
        return user.getBirthday().isBefore(dateNow);
    }
}
