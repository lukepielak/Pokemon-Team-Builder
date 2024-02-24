package model.exception;

// represents the exception that occurs when trying to create a team with no name.
public class PokemonNotFoundException extends Throwable {
    public PokemonNotFoundException(String msg) {
        super(msg);
    }
}
