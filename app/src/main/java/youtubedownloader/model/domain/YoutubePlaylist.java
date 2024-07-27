package youtubedownloader.model.domain;

import java.io.IOException;
import java.util.List;

import youtubedownloader.model.exceptions.PageOutOfBoundsException;

public interface YoutubePlaylist {
    public String getTitle();
    public YoutubeChannel getChannel();
    public int getNOfVideos();
    public String getVideoId(int videoNumber) throws IOException;
    public List<YoutubePlaylistItem> getNextPage() throws PageOutOfBoundsException, IOException;
    public List<YoutubePlaylistItem> getCurrentPage() throws IOException;
    public List<YoutubePlaylistItem> getPreviousPage() throws PageOutOfBoundsException, IOException;
}