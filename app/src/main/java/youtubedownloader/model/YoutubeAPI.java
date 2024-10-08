package youtubedownloader.model;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.Video;

import youtubedownloader.model.exceptions.ChannelNotFoundException;
import youtubedownloader.model.exceptions.PlaylistItemsNotFoundException;
import youtubedownloader.model.exceptions.PlaylistNotFoundException;
import youtubedownloader.model.exceptions.VideoNotFoundException;
import youtubedownloader.model.exceptions.YouTubeServiceCreationException;
import youtubedownloader.model.utils.PlaylistItemHandler;

public class YoutubeAPI {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "YoutubeDownloader";
    private static final String SNIPPET = "snippet";
    private static final String CONTENT_DETAILS = "contentDetails";
    private static final String STATUS = "status";
    private static final long PLAYLIST_MAX_RESULTS = 50L;
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

    public Video getVideo(String videoId, String key) throws VideoNotFoundException {
        try {
            YouTube.Videos.List videoList = youtubeService.videos().list(createPartList(SNIPPET, CONTENT_DETAILS, STATUS))
                    .setId(videoId)
                    .setKey(key);
            return videoList.execute().getItems().get(0);
        } catch (IOException e) {
            throw new VideoNotFoundException("Failed to get video.", e);
        }
    }

    public String getVideoDuration(String videoId, String key) throws VideoNotFoundException {
        try {
            YouTube.Videos.List videoList = youtubeService.videos().list(CONTENT_DETAILS)
                    .setId(videoId)
                    .setKey(key);
            return videoList.execute().getItems().get(0).getContentDetails().getDuration();
        } catch (IOException e) {
            throw new VideoNotFoundException("Failed to get video duration.", e);
        }
    }
    
    public Playlist getPlaylist(String playlistId, String key) throws PlaylistNotFoundException {
        try {
            YouTube.Playlists.List playlistList = youtubeService.playlists().list(createPartList(SNIPPET, CONTENT_DETAILS))
                .setId(playlistId)
                .setKey(key);
            return playlistList.execute().getItems().get(0);
        } catch (IOException e) {
            throw new PlaylistNotFoundException("Failed to get playlist", e);
        }
    }

    public PlaylistItemHandler getPlaylistItems(String playlistId, String key, int nOfVideos) throws PlaylistItemsNotFoundException {
        try {
            YouTube.PlaylistItems.List playlistItemsList = youtubeService.playlistItems().list(createPartList(SNIPPET, CONTENT_DETAILS, STATUS))
                .setPlaylistId(playlistId)
                .setMaxResults(PLAYLIST_MAX_RESULTS)  // Maximum allowed value
                .setKey(key);
            return new PlaylistItemHandler(playlistItemsList,nOfVideos);
        } catch (IOException e) {
            throw new PlaylistItemsNotFoundException("Failed to get playlist items.", e);
        }
    }

    public Channel getChannel(String channelId, String key) throws ChannelNotFoundException {
        try {
            YouTube.Channels.List channelList = youtubeService.channels().list(SNIPPET)
                    .setId(channelId)
                    .setKey(key);
            return channelList.execute().getItems().get(0);
        } catch (IOException e) {
            throw new ChannelNotFoundException("Failed to get channel.", e);
        }
    }

    private static String createPartList(String... parts) {
        StringBuilder partList = new StringBuilder();
        for (String part : parts) {
            partList.append(part).append(",");
        }
        return partList.substring(0, partList.length() - 1);
    }
}
