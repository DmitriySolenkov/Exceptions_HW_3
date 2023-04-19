package MyExceptions;

public class WrongFullNameException extends Exception {
    String message;

    public WrongFullNameException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}