package myfilesystem;

public class NotEnoughSpaceException extends Exception {

    private static final String message = "File system is full";

    public NotEnoughSpaceException() {
    }

    @Override
    public String getMessage() {
        return message;
    }
}
