package ru.yandex.practicum.filmorate.validators.exeptions;

public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super(message);
    }
}
