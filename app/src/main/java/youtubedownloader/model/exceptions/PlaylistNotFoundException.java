package youtubedownloader.model.exceptions;

public class PlaylistNotFoundException extends YoutubeAPIException {
    public PlaylistNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}