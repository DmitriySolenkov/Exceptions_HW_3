package MyExceptions;

public class SaveFailedException extends Exception {
    String message;

    public SaveFailedException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}