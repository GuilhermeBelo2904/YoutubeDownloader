package youtubedownloader.model.domain;

public class YoutubeVideo {
    private final String title;
    private final YoutubeChannel channel;
    private final String thumbnailUrl;
    private final String duration;

    public YoutubeVideo(String title, YoutubeChannel channel, String thumbnailUrl, String duration) {
        this.title = title;
        this.channel = channel;
        this.thumbnailUrl = thumbnailUrl;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public YoutubeChannel getChannel() {
        return channel;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "YoutubeVideo{" +
                "title='" + title + '\'' +
                ", channel=" + channel +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}