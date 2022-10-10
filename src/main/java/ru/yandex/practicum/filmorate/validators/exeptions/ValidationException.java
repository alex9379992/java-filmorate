package ru.yandex.practicum.filmorate.validators.exeptions;

public class ValidationException extends Exception{
    public ValidationException(String message) {
        super(message);
    }
}
