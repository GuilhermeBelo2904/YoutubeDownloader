package youtubedownloader.model;

import java.util.List;

import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelSnippet;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoContentDetails;
import com.google.api.services.youtube.model.VideoSnippet;

import youtubedownloader.model.domain.YoutubeChannel;
import youtubedownloader.model.domain.YoutubePlaylist;
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

    private YoutubeChannel getChannel(String channelId) throws YoutubeAPIException {
        Channel channel = youtubeAPI.getChannel(channelId, key);
        ChannelSnippet channelSnippet = channel.getSnippet();
        String channelName = channelSnippet.getTitle();
        String channelPhotoUrl = channelSnippet.getThumbnails().getHigh().getUrl();
        return new YoutubeChannel(channelName, channelPhotoUrl);
    }    

    public YoutubePlaylist getPlaylist(String url) {
        return null;
    }

    public List<PlaylistItem> getPlaylistItems(String url, int dir) throws YoutubeAPIException {
        return switch (dir) {
            case 1 -> youtubeAPI.getPlaylistItems(youtubeAPI.getPlaylistId(url),key,YoutubeAPI.Page.NEXT);
            case -1 -> youtubeAPI.getPlaylistItems(youtubeAPI.getPlaylistId(url),key,YoutubeAPI.Page.PREVIOUS);
            default -> youtubeAPI.getPlaylistItems(youtubeAPI.getPlaylistId(url),key,YoutubeAPI.Page.SAME);
        };
    }
}
