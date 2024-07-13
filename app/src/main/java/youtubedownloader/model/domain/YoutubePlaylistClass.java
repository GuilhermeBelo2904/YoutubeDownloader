package youtubedownloader.model.domain;

import java.util.List;

import com.google.api.services.youtube.model.Video;

public class YoutubePlaylistClass implements YoutubePlaylist {
    private List<Video> videos;
    private int currentVideo;

    public YoutubePlaylistClass(List<Video> videos) {
        this.videos = videos;
        currentVideo = 0;
    }

    public Video getNext() {
        currentVideo++;
        return videos.get(currentVideo);
    }

    public Video getPrevious() {
        currentVideo--;
        return videos.get(currentVideo);
    }
}