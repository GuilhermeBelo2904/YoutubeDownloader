# YouTube Downloader App

Welcome to the YouTube Downloader App project! This application leverages `yt-dlp` and a JavaFX interface to provide a seamless experience for downloading YouTube videos.

## Objective

The main goal of this project is to create a user-friendly desktop application that allows users to download videos from YouTube effortlessly. By combining the powerful features of `yt-dlp` with a sleek JavaFX interface, we aim to deliver a robust and intuitive tool for all users.

## Key Components

- **yt-dlp**: A powerful command-line program to download videos from YouTube and other video sites.
  - [yt-dlp GitHub Repository](https://github.com/yt-dlp/yt-dlp)
  
- **JavaFX**: A set of graphics and media packages that enables developers to design, create, test, debug, and deploy rich client applications.
  - [OpenJFX](https://openjfx.io/)

- **Gradle**: A build automation tool that is designed to be flexible enough to build almost any type of software.
  - [Gradle](https://gradle.org/)

- **MVVM Model**: A design pattern that helps to separate the business logic from the user interface.
  - [MVVM Model](https://learn.microsoft.com/pt-pt/dotnet/architecture/maui/mvvm)

## Development Environment

To set up your development environment, we recommend using Visual Studio Code and SourceTree. Here are some useful links to get you started:

- **Visual Studio Code**: An editor that helps you write, debug, and maintain your code efficiently.
  - [Java in Visual Studio Code](https://code.visualstudio.com/docs/java/java-tutorial)
  - [Java Build Tools in Visual Studio Code](https://code.visualstudio.com/docs/java/java-build)
  
- **SourceTree**: A free Git GUI client for Windows and macOS.
  - [SourceTree](https://www.sourcetreeapp.com/)

## Getting Started

1. **Clone the Repository**: Use SourceTree or your preferred Git client to clone the project repository.

2. **Install Dependencies**: Ensure you have Java, Gradle, and `yt-dlp` installed on your machine.

3. **Set Up Your IDE**: Follow the Visual Studio Code tutorials to set up your Java development environment.

4. **Run the Application**: Use Gradle to build and run the application. You can use the following commands:
   ```
   ./gradlew build
   ./gradlew run
   ```

## Things We Have Left to Do
1. **Setting up the Youtube API**
2. **Download Videos**
   - Display information about the video:
     - Download size
     - Name
     - Author
     - Thumbnail and author photo
     - Video length
     - etc.
   - Options:
     - Type
     - Quality
     - etc.
   - Features:
     - If it's a playlist:
       - Option to download all or selected videos (checklist)
       - Display specific information for each video
       - Show playlist, author name and number of videos
     - Ability to cut a specific part of the video
     - etc.

3. **Settings**
   - Output folder:
     - Default: Downloads
   - Light/Dark Mode
   - Window size
   - Language

4. **Extra Features**
   - Open the folder where the videos are downloaded
   
