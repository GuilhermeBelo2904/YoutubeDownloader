package youtubedownloader.model.domain;

public interface YoutubePlaylist {
    public String getTitle();
    public YoutubeChannel getChannel();
    public int getNOfVideos();
}