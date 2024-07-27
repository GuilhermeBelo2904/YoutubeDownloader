/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package youtubedownloader;

import java.util.Scanner;

import youtubedownloader.model.VideoManager;
import youtubedownloader.model.domain.YoutubePlaylist;
import youtubedownloader.model.domain.YoutubeVideo;
import youtubedownloader.model.exceptions.YoutubeAPIException;

public class App {
    static final String API_KEY = "AIzaSyCLtNPNHaj9Wot6U1rmGYW0zuUbMRb9C7s";
    static final String PLAYLIST_LINK_EXAMPLE = "https://www.youtube.com/watch?v=3sO-Y1Zbft4&list=PL-gjJw1sh-N8CiJEZkK6I-gwgxNYDzTAb";

    public static void main(String[] args) {
        String apiKey = "YOUR_API_KEY";  // Replace with your actual API key
        String videoUrl = "https://www.youtube.com/watch?v=VIDEO_ID";  // Replace with your actual video URL
        String playlistUrl = "https://www.youtube.com/playlist?list=PLAYLIST_ID";  // Replace with your actual playlist URL

        try {
            VideoManager videoManager = new VideoManager(apiKey);

            // Fetch and display video details
            YoutubeVideo video = videoManager.getVideo(videoUrl);
            System.out.println("Video Title: " + video.getTitle());
            System.out.println("Channel: " + video.getChannel().getName());
            System.out.println("Thumbnail URL: " + video.getThumbnailUrl());
            System.out.println("Duration: " + video.getDuration());

            // Fetch and display playlist details
            YoutubePlaylist playlist = videoManager.getPlaylist(playlistUrl);
            System.out.println("Playlist Title: " + playlist.getTitle());
            System.out.println("Channel: " + playlist.getChannel().getName());
            System.out.println("Number of Videos: " + playlist.getNOfVideos());

            // Display playlist videos
            for (YoutubeVideo playlistVideo : playlist.getVideos()) {
                System.out.println("\nVideo Title: " + playlistVideo.getTitle());
                System.out.println("Channel: " + playlistVideo.getChannel().getName());
                System.out.println("Thumbnail URL: " + playlistVideo.getThumbnailUrl());
                System.out.println("Duration: " + playlistVideo.getDuration());
            }
        } catch (YoutubeAPIException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    // public static void main(String[] args) {
    //     // Console application to test the YoutubeAPI
    //     while (true) {
    //         System.out.println("\nEnter a Youtube video URL:");
    //         String url = System.console().readLine();
    //         try {
    //             VideoManager videoManager = new VideoManager(API_KEY);
    //             YoutubeVideo video = videoManager.getVideo(url);
    //             System.out.println("Title: " + video.getTitle());
    //             System.out.println("Channel Name: " + video.getChannel().getName());
    //             System.out.println("Channel Photo URL: " + video.getChannel().getPhotoUrl());
    //             System.out.println("Thumbnail URL: " + video.getThumbnailUrl());
    //             System.out.println("Duration: " + video.getDuration());
    //         } catch (YoutubeAPIException e) {
    //             System.out.println("Failed to get video: " + e.getMessage());
    //         }
    //     Scanner in = new Scanner(System.in);
    //     try {
    //         VideoManager videoManager = new VideoManager(API_KEY);
    //         YoutubePlaylist playlist = videoManager.getPlaylist(PLAYLIST_LINK_EXAMPLE);
    //         String command = "";
    //         do {
    //             System.out.print("Command:");
    //             command = in.nextLine().trim();

    //             switch (command) {
    //                 case "next" -> System.out.println(playlist.getNext().getTitle());
    //                 case "current" -> System.out.println(playlist.getCurrent().getTitle());
    //                 case "prev" -> System.out.println(playlist.getPrevious().getTitle());
    //                 case "exit" -> System.out.println("Bye");
    //             }
    //         } while (!command.equals("exit"));
    //     } catch (YoutubeAPIException e) {
    //         System.err.println("Failed to get playlist: " + e.getMessage());
    //     }
    //     in.close();
    //     }
    // }
}
