package youtubedownloader.model;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.Video;

import youtubedownloader.model.exceptions.ChannelNotFoundException;
import youtubedownloader.model.exceptions.VideoNotFoundException;
import youtubedownloader.model.exceptions.YouTubeServiceCreationException;

public class YoutubeAPI {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "YoutubeDownloader";
    private final YouTube youtubeService;

    public YoutubeAPI() throws YouTubeServiceCreationException {
        try {
            this.youtubeService = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, null)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (IOException | GeneralSecurityException e) {
            throw new YouTubeServiceCreationException("Failed to create YouTube service.", e);
        }
    }

    public Video getVideo(String url, String key) throws VideoNotFoundException {
        String videoId = extractVideoId(url);
        try {
            YouTube.Videos.List videoList = youtubeService.videos().list("snippet,contentDetails,status")
                    .setId(videoId)
                    .setKey(key);
            return videoList.execute().getItems().getFirst();
        } catch (IOException e) {
            throw new VideoNotFoundException("Failed to get video.", e);
        }
    }

    public Channel getChannel(String channelId, String key) throws ChannelNotFoundException {
        try {
            YouTube.Channels.List channelList = youtubeService.channels().list("snippet")
                    .setId(channelId)
                    .setKey(key);
            return channelList.execute().getItems().getFirst();
        } catch (IOException e) {
            throw new ChannelNotFoundException("Failed to get channel.", e);
        }
    }

    private String extractVideoId(String youtubeLink) {
        if (youtubeLink.contains("youtu.be")) {
            return youtubeLink.substring(youtubeLink.lastIndexOf("/") + 1);
        } else {
            return youtubeLink.substring(youtubeLink.indexOf("?v=") + 3);
        }
    }
}
