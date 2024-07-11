package youtubedownloader.model.exceptions;

public class ChannelNotFoundException extends YoutubeAPIException {
    public ChannelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
