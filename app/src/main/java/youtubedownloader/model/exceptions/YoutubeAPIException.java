package youtubedownloader.model.exceptions;

public class YoutubeAPIException extends IllegalStateException {
    public YoutubeAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
