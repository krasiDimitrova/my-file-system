package myfilesystem;

public class InvalidArgumentException extends Exception {

    String message;

    public InvalidArgumentException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
