package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidator {
    private final static LocalDate REALISE_DATE_IS_NOT_BEFORE = LocalDate.of(1895,12,28);

    public boolean validate(Film film) {
        if(validateName(film) && validateMaxLength(film) && validateRealise(film) && validateDuration(film)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateName(Film film) {
        return !film.getName().isEmpty();
    }

    private boolean validateMaxLength(Film film) {
        return film.getDescription().length() <= 201;
    }

    private boolean validateRealise(Film film) {
        return !film.getReleaseDate().isBefore(REALISE_DATE_IS_NOT_BEFORE);
    }

    private boolean validateDuration(Film film) {
        return film.getDuration() >= 0;
    }
}
