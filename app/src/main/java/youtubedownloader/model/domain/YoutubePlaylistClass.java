package youtubedownloader.model.domain;

import java.util.List;

public class YoutubePlaylistClass implements YoutubePlaylist {
    private List<YoutubeVideo> videos;
    private String title;
    private YoutubeChannel channel;
    private long nOfVideos;
    private int currentVideo;

    public YoutubePlaylistClass(List<YoutubeVideo> videos, String title, YoutubeChannel channel, long nOfVideos) {
        this.videos = videos;
        this.title = title;
        this.channel = channel;
        this.nOfVideos = nOfVideos;
        this.currentVideo = 0;
    }

    public String getTitle() {
        return title;
    }

    public YoutubeChannel getChannel() {
        return channel;
    }

    public int getNOfVideos() {
        return (int) nOfVideos;
    }

    public YoutubeVideo getNext() {
        return videos.get(++currentVideo);
    }

    public YoutubeVideo getCurrent() {
        return videos.get(currentVideo);
    }

    public YoutubeVideo getPrevious() {
        return videos.get(--currentVideo);
    }
}