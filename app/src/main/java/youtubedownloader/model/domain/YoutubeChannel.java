package youtubedownloader.model.domain;

public class YoutubeChannel {
    private final String name;
    private final String photoUrl;

    public YoutubeChannel(String name, String photoUrl) {
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public String toString() {
        return "YoutubeChannel{" +
                "name='" + name + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
