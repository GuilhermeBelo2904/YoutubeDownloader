package youtubedownloader.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VideoDownloader {

    public void downloadVideo(String link){

        String command = link;

        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String selectQuality(String quality, String link){
        return "yt-dlp " + " -S \"height:" + quality + "\" -f \"bv*\" " + link;
    }


}
