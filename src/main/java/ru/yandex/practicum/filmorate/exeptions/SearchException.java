package ru.yandex.practicum.filmorate.exeptions;

public class SearchException extends RuntimeException{
    public SearchException(String message) {
        super(message);
    }
}
