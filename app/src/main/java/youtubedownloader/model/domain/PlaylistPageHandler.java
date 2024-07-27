package youtubedownloader.model.domain;

import java.util.List;

import com.google.api.services.youtube.model.PlaylistItem;

public class PlaylistPageHandler {
    private final List<PlaylistItem> items;
    private final String nextPageToken;

    public PlaylistPageHandler(List<PlaylistItem> items, String nextPageToken) {
        this.items = items;
        this.nextPageToken = nextPageToken;
    }

    public List<PlaylistItem> getItems() {
        return items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }
}
