package youtubedownloader.model.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;

import youtubedownloader.model.domain.YoutubePlaylistItem;
import youtubedownloader.model.exceptions.PageOutOfBoundsException;

public class PlaylistItemHandler {
    private static final int ITEMS_PER_PAGE = 50;
    private static final int STARTING_PAGE = 1;

    private final YouTube.PlaylistItems.List itemsList;
    private String nextPageToken;
    private final int lastPage;
    private int currentPage;

    public PlaylistItemHandler(YouTube.PlaylistItems.List itemsList, int nOfItems) {
        this.itemsList = itemsList;
        nextPageToken = null;
        lastPage = (int) Math.ceil((double) nOfItems / ITEMS_PER_PAGE);
        
        currentPage = STARTING_PAGE;
    }

    public List<YoutubePlaylistItem> getNextPageItems() throws PageOutOfBoundsException, IOException {
        if (currentPage == lastPage)
            throw new PageOutOfBoundsException("Page out of bounds");

        PlaylistItemListResponse response = newPageResponse();
        nextPageToken = response.getNextPageToken();
        currentPage++;

        List<PlaylistItem> items = newPageResponse().getItems();
        
        return toYoutubePlaylistItems(items);
    }

    public List<YoutubePlaylistItem> getCurrentPageItems() throws IOException {
        List<PlaylistItem> items = newPageResponse().getItems();

        return toYoutubePlaylistItems(items);
    }
    
    public List<YoutubePlaylistItem> getPrevPageItems() throws PageOutOfBoundsException, IOException {
        if (currentPage == STARTING_PAGE)
            throw new PageOutOfBoundsException("Page out of bounds");

        PlaylistItemListResponse response = newPageResponse();
        nextPageToken = response.getPrevPageToken();
        currentPage--;

        List<PlaylistItem> items = newPageResponse().getItems();

        return toYoutubePlaylistItems(items);
    }

    private PlaylistItemListResponse newPageResponse() throws IOException {
        return itemsList.setPageToken(nextPageToken).execute();
    }

    private List<YoutubePlaylistItem> toYoutubePlaylistItems(List<PlaylistItem> items) {
        List<YoutubePlaylistItem> publicItems = new ArrayList<>();

        for (PlaylistItem item: items) {
            String itemStatus = item.getStatus().getPrivacyStatus();

            if (itemStatus.equals("public")) {
                String videoTitle = item.getSnippet().getTitle();
                String videoId = item.getContentDetails().getVideoId();

                publicItems.add(new YoutubePlaylistItem(videoTitle, videoId));
            }
        }

        return publicItems;
    }
}