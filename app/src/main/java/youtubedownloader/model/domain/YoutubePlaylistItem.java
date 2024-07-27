package youtubedownloader.model.domain;

public class YoutubePlaylistItem {
    private String title;
    private String videoId;

    public YoutubePlaylistItem(String title, String videoId) {
        this.title = title;
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getVideoId() {
        return videoId;
    }
}