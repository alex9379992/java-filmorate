package ru.yandex.practicum.filmorate.validatorsTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmValidatorTest {

    private final FilmValidator validator = new FilmValidator();

    @Test
    @DisplayName("Проверка в нормальных условиях.")
    void validateTest() {
        boolean isValidate = validator.validate(new Film("nisi eiusmod",
                 "adipisicing",
                LocalDate.parse("1967-03-25"),
                100));
        assertTrue(isValidate, "Должен быть true.");
    }
    @Test
    @DisplayName("Проверка на пустое имя.")
    void validateNameTest() {
        boolean isValidate = validator.validate(new Film(   "",
                "Description",
                LocalDate.parse("1900-03-25"),
                200));
        assertFalse(isValidate, "Должен быть false.");
    }

    @Test
    @DisplayName("Проверка максимальной длинны описания")
    void validateMaxLengthTest() {
        boolean isValidate = validator.validate(new Film(  "Film name",
                "Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.",
                LocalDate.parse("1900-03-25"),
                200));
        assertFalse(isValidate, "Должен быть false.");
    }

    @Test
    @DisplayName("Проверка соответствия даты релиза")
    void validateRealiseTest() {
        boolean isValidate = validator.validate(new Film(  "Name",
                "Description",
                LocalDate.parse("1890-03-25"),
                200));
        assertFalse(isValidate, "Должен быть false.");
    }

    @Test
    @DisplayName("Проверка продолжительности фильма")
    void validateDurationTest() {
        boolean isValidate = validator.validate(new Film( "Name",
                "Descrition",
                LocalDate.parse("1980-03-25") ,
                -200));
        assertFalse(isValidate, "Должен быть false.");
    }
}