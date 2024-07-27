package youtubedownloader.model.domain;

import java.io.IOException;
import java.util.List;

import youtubedownloader.model.exceptions.PageOutOfBoundsException;
import youtubedownloader.model.utils.PlaylistItemHandler;

public class YoutubePlaylistClass implements YoutubePlaylist {
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

    public String getVideoId(int videoNumber) throws IOException {
        return getCurrentPage().get(videoNumber - 1).getVideoId();
    }

    public List<YoutubePlaylistItem> getNextPage() throws PageOutOfBoundsException, IOException {
        return itemHandler.getNextPageItems();
    }

    public List<YoutubePlaylistItem> getCurrentPage() throws IOException {
        return itemHandler.getCurrentPageItems();
    }

    public List<YoutubePlaylistItem> getPreviousPage() throws PageOutOfBoundsException, IOException {
        return itemHandler.getPrevPageItems();
    }
}