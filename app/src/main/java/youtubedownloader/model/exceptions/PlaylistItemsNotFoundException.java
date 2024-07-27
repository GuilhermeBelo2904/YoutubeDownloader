package youtubedownloader.model.exceptions;

public class PlaylistItemsNotFoundException extends YoutubeAPIException {
    public PlaylistItemsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
