package MyExceptions;

public class WrongSexInputException extends Exception {
    String message;

    public WrongSexInputException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
