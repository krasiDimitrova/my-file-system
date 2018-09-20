package myfilesystem;

public class InvalidArgumentException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    String message;

    public InvalidArgumentException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
