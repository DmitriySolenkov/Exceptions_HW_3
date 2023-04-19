package MyExceptions;

public class WrongPhoneNumberInputException extends Exception {
    String message;

    public WrongPhoneNumberInputException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}