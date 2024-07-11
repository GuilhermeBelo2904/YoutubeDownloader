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
    private static final YouTube YOUTUBE_SERVICE;

    static {
        try {
            YOUTUBE_SERVICE = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, null)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (IOException | GeneralSecurityException e) {
            throw new YouTubeServiceCreationException("Failed to create YouTube service.", e);
        }
    }

    public Video getVideo(String url, String key) {
        String videoId = getVideoId(url);
        try {
            YouTube.Videos.List videoList = YOUTUBE_SERVICE.videos().list("snippet,contentDetails").setId(videoId).setKey(key);
            return videoList.execute().getItems().getFirst();
        } catch (IOException e) {
            throw new VideoNotFoundException("Failed to get video.", e);
        }
    }

    public Channel getChannel(String channelId, String key) {
        try {
            YouTube.Channels.List channelList = YOUTUBE_SERVICE.channels().list("snippet").setId(channelId).setKey(key);
            return channelList.execute().getItems().getFirst();
        } catch (IOException e) {
            throw new ChannelNotFoundException("Failed to get channel.", e);
        }
    }

    private String getVideoId(String youtubeLink) {
        String videoId;
        if (youtubeLink.contains("youtu.be")) {
            videoId = youtubeLink.substring(youtubeLink.lastIndexOf("/") + 1);
        } else {
            videoId = youtubeLink.substring(youtubeLink.indexOf("?v=") + 3);
        }
        return videoId;
    }
}
