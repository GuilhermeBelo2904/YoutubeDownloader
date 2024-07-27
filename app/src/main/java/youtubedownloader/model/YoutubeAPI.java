package youtubedownloader.model;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.Video;

import youtubedownloader.model.domain.PlaylistPageHandler;
import youtubedownloader.model.exceptions.ChannelNotFoundException;
import youtubedownloader.model.exceptions.PlaylistItemsNotFoundException;
import youtubedownloader.model.exceptions.PlaylistNotFoundException;
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
        String videoId = getVideoId(url);
        try {
            YouTube.Videos.List videoList = youtubeService.videos().list("snippet,contentDetails,status")
                    .setId(videoId)
                    .setKey(key);
            return videoList.execute().getItems().get(0);
        } catch (IOException e) {
            throw new VideoNotFoundException("Failed to get video.", e);
        }
    }

    public String getVideoDuration(String videoId, String key) throws VideoNotFoundException {
        try {
            YouTube.Videos.List videoList = youtubeService.videos().list("contentDetails")
                    .setId(videoId)
                    .setKey(key);
            return videoList.execute().getItems().get(0).getContentDetails().getDuration();
        } catch (IOException e) {
            throw new VideoNotFoundException("Failed to get video duration.", e);
        }
    }

    public Playlist getPlaylist(String url, String key) throws PlaylistNotFoundException {
        String playlistId = getPlaylistId(url);
        try {
            YouTube.Playlists.List playlistList = youtubeService.playlists().list("snippet,contentDetails")
                .setId(playlistId)
                .setKey(key);

            return playlistList.execute().getItems().get(0);
        } catch (IOException e) {
            throw new PlaylistNotFoundException("Failed to get playlist", e);
        }
    }

    public PlaylistPageHandler getPlaylistItems(String url, String key, String pageToken) throws PlaylistItemsNotFoundException {
        String playlistId = getPlaylistId(url);
        try {
            YouTube.PlaylistItems.List request = youtubeService.playlistItems()
                    .list("snippet")
                    .setPlaylistId(playlistId)
                    .setMaxResults(50L)
                    .setKey(key);
            if (pageToken != null && !pageToken.isEmpty()) {
                request.setPageToken(pageToken);
            }

            PlaylistItemListResponse response = request.execute();
            List<PlaylistItem> items = response.getItems();
            String nextPageToken = response.getNextPageToken();

            return new PlaylistPageHandler(items, nextPageToken);
        } catch (IOException e) {
            throw new PlaylistItemsNotFoundException("Failed to get playlist items.", e);
        }
    }

    public Channel getChannel(String channelId, String key) throws ChannelNotFoundException {
        try {
            YouTube.Channels.List channelList = youtubeService.channels().list("snippet")
                    .setId(channelId)
                    .setKey(key);
            return channelList.execute().getItems().get(0);
        } catch (IOException e) {
            throw new ChannelNotFoundException("Failed to get channel.", e);
        }
    }

    private String getVideoId(String youtubeLink) {
        if (youtubeLink.contains("youtu.be")) {
            return youtubeLink.substring(youtubeLink.lastIndexOf("/") + 1);
        } else {
            return youtubeLink.substring(youtubeLink.indexOf("?v=") + 3);
        }
    }

    private String getPlaylistId(String youtubeLink) {
        String videoId = youtubeLink.substring(youtubeLink.indexOf("list=") + 5);
        
        if (videoId.contains("&index="))
            videoId = videoId.substring(0, videoId.indexOf("&index="));

        return videoId;
    }
}
