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
        User user = User.builder()
                .email("mail@mail.ru")
                .login("dolore")
                .birthday(LocalDate.parse("1946-08-20"))
                .build();
        boolean isValidate = validator.validate(user);
        assertTrue(isValidate, "Должен быть true.");
    }
    @Test
    @DisplayName("Проверка Email.")
    void validateEmailTest() {
        User user = User.builder()
                .email("mail.ru")
                .login("dolore")
                .birthday(LocalDate.parse("1946-08-20"))
                .build();
        boolean isValidate = validator.validate(user);
        assertFalse(isValidate, "Должен быть false.");
    }

    @Test
    @DisplayName("Проверка логина")
    void validateLoginTest() {
        User user = User.builder()
                .email("mail@mail.ru")
                .login("dolorev ullamco")
                .birthday(LocalDate.parse("1946-08-20"))
                .build();
        boolean isValidate = validator.validate(user);
        assertFalse(isValidate, "Должен быть false.");
    }

    @Test
    @DisplayName("Проверка имени пользователя")
    void validateNameTest() {
        User user = User.builder()
                .email("mail@mail.ru")
                .login("dolore")
                .birthday(LocalDate.parse("1946-08-20"))
                .build();
        boolean isValidate = validator.validate(user);
        assertTrue(isValidate, "Должен быть true.");
        assertEquals(user.getName(), user.getLogin(), "Имя должно быть dolore");
    }

    @Test
    @DisplayName("Проверка даты рождения")
    void validateDataTest() {
        User user = User.builder()
                .email("mail@mail.ru")
                .login("dolore")
                .birthday(LocalDate.parse("2446-08-20"))
                .build();
        boolean isValidate = validator.validate(user);
        assertFalse(isValidate, "Должен быть false.");
    }
}