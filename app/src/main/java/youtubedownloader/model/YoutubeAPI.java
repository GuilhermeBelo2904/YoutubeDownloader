package youtubedownloader.model;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;

public class YoutubeAPI {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "YoutubeDownloader";

    public YouTube getYouTubeService() {
        try {
            return new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, null)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (IOException | GeneralSecurityException e) {
            return null;
        }
    }

    public String getVideoId(String youtubeLink) {
        String videoId;
        if (youtubeLink.contains("youtu.be")) {
            videoId = youtubeLink.substring(youtubeLink.lastIndexOf("/") + 1);
        } else {
            videoId = youtubeLink.substring(youtubeLink.indexOf("?v=") + 3);
        }
        return videoId;
    }

    public String getPlaylistId(String youtubeLink) {
        String videoId = youtubeLink.substring(youtubeLink.indexOf("list=") + 5);
        
        if (videoId.contains("&index="))
            videoId = videoId.substring(0, videoId.indexOf("&index="));

        return videoId;
    }
}
