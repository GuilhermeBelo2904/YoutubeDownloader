package youtubedownloader.model;

import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelSnippet;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistContentDetails;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoContentDetails;
import com.google.api.services.youtube.model.VideoSnippet;

import youtubedownloader.model.domain.PlaylistItemHandler;
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

    public YoutubePlaylist getPlaylist(String url) throws YoutubeAPIException {
        Playlist playlist = youtubeAPI.getPlaylist(url, key);
        PlaylistItemHandler itemHandler = youtubeAPI.getPlaylistItems(url,key);
        PlaylistSnippet playlistSnippet = playlist.getSnippet();
        PlaylistContentDetails playlistContentDetails = playlist.getContentDetails();

        String title = playlistSnippet.getTitle();
        YoutubeChannel youtubeChannel = getChannel(playlistSnippet.getChannelId());
        long nOfVideos = playlistContentDetails.getItemCount();

        return new YoutubePlaylistClass(
            itemHandler,
            title,
            youtubeChannel,
            nOfVideos
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
