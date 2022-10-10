package ru.yandex.practicum.filmorate.validatorsTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    private final UserValidator validator = new UserValidator();
    @Test
    @DisplayName("Проверка в нормальных условиях.")
    void validateTest() {
        boolean isValidate = validator.validate(new User("mail@mail.ru",
                "dolore",
                LocalDate.parse("1946-08-20")));
        assertTrue(isValidate, "Должен быть true.");
    }
    @Test
    @DisplayName("Проверка Email.")
    void validateEmailTest() {
        boolean isValidate = validator.validate(new User("mail.ru",
                "dolore",
                LocalDate.parse("1946-08-20")));
        assertFalse(isValidate, "Должен быть false.");
    }

    @Test
    @DisplayName("Проверка логина")
    void validateLoginTest() {
        User user = new User("mail@mail.ru",
                "dolore ullamco",
                LocalDate.parse("2446-08-20"));
        boolean isValidate = validator.validate(user);
        assertFalse(isValidate, "Должен быть false.");
    }

    @Test
    @DisplayName("Проверка имени пользователя")
    void validateNameTest() {
        User user = new User("mail@mail.ru",
                "dolore",
                LocalDate.parse("1946-08-20"));
        boolean isValidate = validator.validate(user);
        assertTrue(isValidate, "Должен быть true.");
        assertEquals(user.getName(), user.getLogin(), "Имя должно быть dolore");
    }

    @Test
    @DisplayName("Проверка даты рождения")
    void validateDataTest() {
        boolean isValidate = validator.validate(new User("mail@mail.ru",
                "dolore",
                LocalDate.parse("2446-08-20")));
        assertFalse(isValidate, "Должен быть false.");
    }
}