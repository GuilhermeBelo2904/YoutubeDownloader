package youtubedownloader.model.domain;

import java.util.List;

import java.io.IOException;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;

public class PlaylistItemHandler {
    private YouTube.PlaylistItems.List itemsList;
    private String nextPageToken;
    private int currentPage;
    private int currentItem;

    public PlaylistItemHandler(YouTube.PlaylistItems.List itemsList) {
        this.itemsList = itemsList;
        nextPageToken = null;
        // Both start at 0
        currentPage = 0; 
        currentItem = 0;
    }

    public PlaylistItem getNext() throws IOException {
        PlaylistItem item;
        currentItem++;

        if ((currentItem + 1) / (currentPage + 1) > 50){
            currentPage++;
            item = getNextPageItems().get(currentItem - 50*currentPage);
        } else
            item = getSamePageItems().get(currentItem - 50*currentPage);
        
        return item;
    }

    public PlaylistItem getSame() throws IOException {
        return getSamePageItems().get(currentItem - 50*currentPage);
    }

    public PlaylistItem getPrev() throws IOException {
        PlaylistItem item;
        currentItem--;

        if ((currentItem + 1) % 50 == 0) {
            currentPage++;
            item = getPrevPageItems().get(currentItem - 50*currentPage);
        } else
            item = getSamePageItems().get(currentItem - 50*currentPage);
        
        return item;
    }

    private List<PlaylistItem> getNextPageItems() throws IOException {
        PlaylistItemListResponse response = newPageResponse();
        nextPageToken = response.getNextPageToken();
        
        return newPageResponse().getItems();
    }

    private List<PlaylistItem> getSamePageItems() throws IOException {
        return itemsList.setPageToken(nextPageToken).execute().getItems();
    }

    private List<PlaylistItem> getPrevPageItems() throws IOException {
        PlaylistItemListResponse response = newPageResponse();
        nextPageToken = response.getPrevPageToken();

        return newPageResponse().getItems();
    }

    private PlaylistItemListResponse newPageResponse() throws IOException {
        return itemsList.setPageToken(nextPageToken).execute();
    }
}