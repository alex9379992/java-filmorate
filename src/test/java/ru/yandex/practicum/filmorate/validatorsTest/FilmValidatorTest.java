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
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.parse("1967-03-25"))
                .duration(100)
                .build();
        boolean isValidate = validator.validate(film);
        assertTrue(isValidate, "Должен быть true.");
    }
    @Test
    @DisplayName("Проверка на пустое имя.")
    void validateNameTest() {
        Film film = Film.builder()
                .name("")
                .description("Description")
                .releaseDate(LocalDate.parse("1900-03-25"))
                .duration(200)
                .build();
        boolean isValidate = validator.validate(film);
        assertFalse(isValidate, "Должен быть false.");
    }

    @Test
    @DisplayName("Проверка максимальной длинны описания")
    void validateMaxLengthTest() {
        Film film = Film.builder()
                .name("Film name")
                .description("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. " +
                        "Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, " +
                        "а именно 20 миллионов. о Куглов, который за время «своего отсутствия»," +
                        " стал кандидатом Коломбани.")
                .releaseDate(LocalDate.parse("1900-03-25"))
                .duration(200)
                .build();
        boolean isValidate = validator.validate(film);
        assertFalse(isValidate, "Должен быть false.");
    }

    @Test
    @DisplayName("Проверка соответствия даты релиза")
    void validateRealiseTest() {
        Film film = Film.builder()
                .name("Name")
                .description("Description")
                .releaseDate(LocalDate.parse("1890-03-25"))
                .duration(200)
                .build();
        boolean isValidate = validator.validate(film);
        assertFalse(isValidate, "Должен быть false.");
    }

    @Test
    @DisplayName("Проверка продолжительности фильма")
    void validateDurationTest() {
        Film film = Film.builder()
                .name("Name")
                .description("Description")
                .releaseDate(LocalDate.parse("1980-03-25"))
                .duration(-200)
                .build();
        boolean isValidate = validator.validate(film);
        assertFalse(isValidate, "Должен быть false.");
    }
}