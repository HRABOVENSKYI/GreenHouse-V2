package ua.lviv.iot.greenhouse.exception;

public class WrongDateFormatException extends RuntimeException {

    public WrongDateFormatException(String message) {
        super(message);
    }
}
