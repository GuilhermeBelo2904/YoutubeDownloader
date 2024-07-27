package youtubedownloader.model;

import java.util.ArrayList;
import java.util.List;

import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelSnippet;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistContentDetails;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoContentDetails;
import com.google.api.services.youtube.model.VideoSnippet;

import youtubedownloader.model.domain.PlaylistPageHandler;
import youtubedownloader.model.domain.YoutubeChannel;
import youtubedownloader.model.domain.YoutubePlaylist;
import youtubedownloader.model.domain.YoutubePlaylistClass;
import youtubedownloader.model.domain.YoutubeVideo;
import youtubedownloader.model.exceptions.YoutubeAPIException;

public class VideoManager {
    private final YoutubeAPI youtubeAPI;
    private final String key;

    public VideoManager(String key) throws YoutubeAPIException {
        this.key = key;
        this.youtubeAPI = new YoutubeAPI();   
    }

    public YoutubeVideo getVideo(String url) throws YoutubeAPIException {
        Video video = youtubeAPI.getVideo(url, key);
        return toYoutubeVideo(video);
    }

    public YoutubePlaylist getPlaylist(String url) throws YoutubeAPIException {
        Playlist playlist = youtubeAPI.getPlaylist(url, key);
        PlaylistSnippet playlistSnippet = playlist.getSnippet();
        PlaylistContentDetails playlistContentDetails = playlist.getContentDetails();

        String title = playlistSnippet.getTitle();
        YoutubeChannel youtubeChannel = getChannel(playlistSnippet.getChannelId());
        long nOfVideos = playlistContentDetails.getItemCount();
        List<YoutubeVideo> playlistVideos = getPlaylistVideos(url, (int) nOfVideos);

        return new YoutubePlaylistClass(
            playlistVideos,
            title,
            youtubeChannel,
            nOfVideos
        );
    }

    private List<YoutubeVideo> getPlaylistVideos(String url, int nOfVideos) throws YoutubeAPIException {
        List<YoutubeVideo> videos = new ArrayList<>();
        String nextPageToken = null;
        int remainingVideos = nOfVideos;

        while (remainingVideos > 0) {
            PlaylistPageHandler pageHandler = youtubeAPI.getPlaylistItems(url, key, nextPageToken);
            List<PlaylistItem> items = pageHandler.getItems();
            for (PlaylistItem item : items) {
                videos.add(toYoutubeVideo(item));
                if (--remainingVideos <= 0) {
                    break;
                }
            }
            nextPageToken = pageHandler.getNextPageToken();
            if (nextPageToken == null) {
                break;
            }
        }
        return videos;
    }

    private YoutubeVideo toYoutubeVideo(Video video) throws YoutubeAPIException {
        VideoSnippet videoSnippet = video.getSnippet();
        VideoContentDetails videoContentDetails = video.getContentDetails();

        String title = videoSnippet.getTitle();
        YoutubeChannel youtubeChannel = getChannel(videoSnippet.getChannelId());
        String thumbnailUrl = videoSnippet.getThumbnails().getHigh().getUrl();
        String duration = videoContentDetails.getDuration();

        return new YoutubeVideo(
            title,
            youtubeChannel, 
            thumbnailUrl, 
            duration
        );
    }

    private YoutubeVideo toYoutubeVideo(PlaylistItem item) throws YoutubeAPIException {
        PlaylistItemSnippet playlistItemSnippet = item.getSnippet();
        String title = playlistItemSnippet.getTitle();
        YoutubeChannel youtubeChannel = getChannel(playlistItemSnippet.getChannelId());
        String thumbnailUrl = playlistItemSnippet.getThumbnails().getHigh().getUrl();
        String videoId = playlistItemSnippet.getResourceId().getVideoId();
        String duration = youtubeAPI.getVideoDuration(videoId, key);

        return new YoutubeVideo(
            title,
            youtubeChannel, 
            thumbnailUrl, 
            duration
        );
    }

    private YoutubeChannel getChannel(String channelId) throws YoutubeAPIException {
        Channel channel = youtubeAPI.getChannel(channelId, key);
        ChannelSnippet channelSnippet = channel.getSnippet();
        String channelName = channelSnippet.getTitle();
        String channelPhotoUrl = channelSnippet.getThumbnails().getHigh().getUrl();
        return new YoutubeChannel(channelName, channelPhotoUrl);
    }    
}
