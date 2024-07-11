package youtubedownloader.model.exceptions;

public class VideoNotFoundException extends YoutubeAPIException {
    public VideoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
