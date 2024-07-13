package youtubedownloader.model.domain;

import com.google.api.services.youtube.model.Video;

public class YoutubePlaylistClass implements YoutubePlaylist {
    // To add: Videos to Download
    private PlaylistItemHandler itemHandler;
    private String title;
    private YoutubeChannel channel;
    private long nOfVideos;

    public YoutubePlaylistClass(PlaylistItemHandler itemHandler, String title, YoutubeChannel channel, long nOfVideos) {
        this.itemHandler = itemHandler;
        this.title = title;
        this.channel = channel;
        this.nOfVideos = nOfVideos;
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

    public Video getNext() {
        return null;
    }

    public Video getPrevious() {
        return null;
    }
}