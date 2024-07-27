package youtubedownloader.model.domain;

import java.io.IOException;
import java.util.List;

import youtubedownloader.model.exceptions.PageOutOfBoundsException;
import youtubedownloader.model.utils.PlaylistItemHandler;

public class YoutubePlaylistClass implements YoutubePlaylist {
    private final PlaylistItemHandler itemHandler;
    private final String title;
    private final YoutubeChannel channel;
    private final long nOfVideos;

    public YoutubePlaylistClass(PlaylistItemHandler itemHandler, String title, YoutubeChannel channel, long nOfVideos) {
        this.itemHandler = itemHandler;
        this.title = title;
        this.channel = channel;
        this.nOfVideos = nOfVideos;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public YoutubeChannel getChannel() {
        return channel;
    }

    @Override
    public int getNOfVideos() {
        return (int) nOfVideos;
    }

    @Override
    public String getVideoId(int videoNumber) throws IOException {
        return getCurrentPage().get(videoNumber - 1).getVideoId();
    }

    @Override
    public List<YoutubePlaylistItem> getNextPage() throws PageOutOfBoundsException, IOException {
        return itemHandler.getNextPageItems();
    }

    @Override
    public List<YoutubePlaylistItem> getCurrentPage() throws IOException {
        return itemHandler.getCurrentPageItems();
    }

    @Override
    public List<YoutubePlaylistItem> getPreviousPage() throws PageOutOfBoundsException, IOException {
        return itemHandler.getPrevPageItems();
    }
}