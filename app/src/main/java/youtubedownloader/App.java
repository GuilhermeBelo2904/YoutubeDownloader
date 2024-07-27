/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package youtubedownloader;

import java.io.IOException;
import java.util.Scanner;

import java.util.List;

import youtubedownloader.model.VideoManager;
import youtubedownloader.model.domain.YoutubePlaylist;
import youtubedownloader.model.domain.YoutubePlaylistItem;
import youtubedownloader.model.domain.YoutubeVideo;
import youtubedownloader.model.exceptions.PageOutOfBoundsException;
import youtubedownloader.model.exceptions.YoutubeAPIException;

public class App {
    static final String API_KEY = "AIzaSyCLtNPNHaj9Wot6U1rmGYW0zuUbMRb9C7s";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {
            VideoManager videoManager = new VideoManager(API_KEY);
            System.out.println("Insert a link:");
            String url = in.nextLine().trim();

            if (videoManager.isPlaylist(url))
                handlePlaylist(in,videoManager,url);
            else
                handleVideo(in,videoManager,url);
        
        } catch (YoutubeAPIException e) {
            System.err.println("Failed to connect with API: " + e.getMessage());
        }
        in.close();
    }

    private static void handlePlaylist(Scanner in, VideoManager videoManager, String url) {
        try {
            String playlistId = videoManager.getPlaylistId(url);
            YoutubePlaylist playlist = videoManager.getPlaylist(playlistId);
            
            String command;
            String quality = null;
            String type = null;
            do {
                System.out.println("Command:");
                command = in.nextLine().trim().toUpperCase();
                try {
                    switch (command) {
                        case "SEE VIDEO X" -> seeVideo(in,videoManager,playlist);
                        case "DESELECT X" -> {/*TODO*/}
                        case "SELECT X" -> {/*TODO*/}
                        case "SELECT X-Y" -> {/*TODO*/}
                        case "DESELECT X-Y" -> {/*TODO*/}
                        case "SELECT ALL" -> {/*TODO*/}
                        case "DOWNLOAD" -> {
                            if (quality != null && type != null) {
                                // TODO
                            } else {
                                System.out.println("Choose Quality and Type to Download!");
                            }
                        }
                        case "QUALITY" -> {/*TODO*/}
                        case "TYPE" -> {/*TODO*/}
                        case "NEXT" -> nextPage(playlist);
                        case "CURRENT" -> currentPage(playlist);
                        case "PREV" -> previousPage(playlist);
                        case "EXIT" -> System.out.println("Bye");
                        default -> System.out.println("Insert Valid Command!");
                    }
                } catch (IOException | YoutubeAPIException e) {
                    System.err.println("Error ocurred: " + e.getMessage());
                }
            } while (!command.equals("EXIT"));
        } catch (YoutubeAPIException e) {
            System.err.println("Failed to get playlist: " + e.getMessage());
        }
    }

    private static void handleVideo(Scanner in, VideoManager videoManager, String url) {
        try {
            String videoId = videoManager.getVideoId(url);
            YoutubeVideo video = videoManager.getVideo(videoId);
            
            String command;
            String quality = null;
            String type = null;
            do {
                System.out.println("Title: " + video.getTitle());
                System.out.println("Channel Name: " + video.getChannel().getName());
                System.out.println("Channel Photo Url: " + video.getChannel().getPhotoUrl());
                System.out.println("Thumbnail Url: " + video.getThumbnailUrl());
                System.out.println("Duration: " + video.getDuration());

                System.out.print("Command:");
                command = in.nextLine().trim().toUpperCase();
                System.out.println();

                switch (command) {
                    case "DOWNLOAD" -> {
                        if (quality != null && type != null) {
                            // TODO
                        } else {
                            System.out.println("Choose Quality and Type to Download!");
                        }
                    }
                    case "QUALITY" -> {/*TODO*/}
                    case "TYPE" -> {/*TODO*/}
                    case "EXIT" -> System.out.println("Bye");
                    default -> System.out.println("Insert Valid Command!");
                }
            } while (!command.equals("EXIT"));
        } catch (YoutubeAPIException e) {
            System.err.println("Failed to get video: " + e.getMessage());
        }
    }

    private static void seeVideo(Scanner in, VideoManager videoManager, YoutubePlaylist playlist) throws IOException, YoutubeAPIException {
        System.out.println("Choose a video from the page");
        int videoNumber = in.nextInt();
        in.nextLine();
        
        String videoId = playlist.getVideoId(videoNumber);
        YoutubeVideo video = videoManager.getVideo(videoId);

        System.out.println("Title: " + video.getTitle());
        System.out.println("Channel Name: " + video.getChannel().getName());
        System.out.println("Channel Photo Url: " + video.getChannel().getPhotoUrl());
        System.out.println("Thumbnail Url: " + video.getThumbnailUrl());
        System.out.println("Duration: " + video.getDuration());
    }


    private static void nextPage(YoutubePlaylist playlist) {
        try {
            printItems(playlist.getNextPage());
        } catch (IOException | PageOutOfBoundsException e) {
            System.err.println("Failed to get playlist page: " + e.getMessage());
        }
    }

    private static void currentPage(YoutubePlaylist playlist) {
        try {
            printItems(playlist.getCurrentPage());
        } catch (IOException e) {
            System.err.println("Failed to get playlist page: " + e.getMessage());
        }
    }

    private static void previousPage(YoutubePlaylist playlist) {
        try {
            printItems(playlist.getPreviousPage());
        } catch (IOException | PageOutOfBoundsException e) {
            System.err.println("Failed to get playlist page: " + e.getMessage());
        }
    }

    private static void printItems(List<YoutubePlaylistItem> items) {
        int i = 1;
        for (YoutubePlaylistItem item: items)
            System.out.println(i++ + ". " + item.getTitle());
    }
}
