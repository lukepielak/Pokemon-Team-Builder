package model.exception;

// represents the exception that occurs when trying to create a team with no name.
public class TeamNotFoundException extends Throwable {
    public TeamNotFoundException(String msg) {
        super(msg);
    }
}