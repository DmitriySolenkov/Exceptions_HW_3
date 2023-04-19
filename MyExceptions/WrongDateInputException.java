package MyExceptions;

public class WrongDateInputException extends Exception {
    String message;

    public WrongDateInputException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
