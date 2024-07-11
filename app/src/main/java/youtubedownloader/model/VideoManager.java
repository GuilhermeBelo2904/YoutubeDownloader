package youtubedownloader.model;

import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelSnippet;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoContentDetails;
import com.google.api.services.youtube.model.VideoSnippet;

import youtubedownloader.model.domain.YoutubeChannel;
import youtubedownloader.model.domain.YoutubeVideo;
import youtubedownloader.model.exceptions.YoutubeAPIException;

public class VideoManager {
    private static final YoutubeAPI YOUTUBE_API = new YoutubeAPI();
    private final String key;

    public VideoManager(String key) {
        this.key = key;
    }

    public YoutubeVideo getVideo(String url) throws YoutubeAPIException {
        Video video = YOUTUBE_API.getVideo(url, key);
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

    private YoutubeChannel getChannel(String channelId) {
        Channel channel = YOUTUBE_API.getChannel(channelId, key);
        ChannelSnippet channelSnippet = channel.getSnippet();
        String channelName = channelSnippet.getTitle();
        String channelPhotoUrl = channelSnippet.getThumbnails().getHigh().getUrl();
        return new YoutubeChannel(channelName, channelPhotoUrl);
    }    
}
