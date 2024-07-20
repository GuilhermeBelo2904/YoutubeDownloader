package youtubedownloader.model.exceptions;

import java.io.IOException;

public class YoutubeAPIException extends IOException {
    public YoutubeAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
