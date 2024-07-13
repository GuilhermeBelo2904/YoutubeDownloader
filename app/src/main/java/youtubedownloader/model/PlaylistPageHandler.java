package youtubedownloader.model;

import java.util.List;

import java.io.IOException;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;

public class PlaylistPageHandler {
    private YouTube.PlaylistItems.List request;
    private static String nextPageToken = null;

    public PlaylistPageHandler(YouTube.PlaylistItems.List request) {
        this.request = request;
    }

    public List<PlaylistItem> getNextPageItems() throws IOException {
        PlaylistItemListResponse response = request.setPageToken(nextPageToken).execute();
        nextPageToken = response.getNextPageToken();
        
        return newPageResponse().getItems();
    }

    public List<PlaylistItem> getSamePageItems() throws IOException {
        return request.setPageToken(nextPageToken).execute().getItems();
    }

    public List<PlaylistItem> getPrevPageItems() throws IOException {
        PlaylistItemListResponse response = request.setPageToken(nextPageToken).execute();
        nextPageToken = response.getPrevPageToken();

        return newPageResponse().getItems();
    }

    private PlaylistItemListResponse newPageResponse() throws IOException {
        return request.setPageToken(nextPageToken).execute();
    }
}