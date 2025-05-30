<div align="center">
<p><img src="https://github.com/user-attachments/assets/bf233e9a-0f3a-4218-9791-18574a45565e" width="128" alt="ReadOut"></p>
<h3 align="center">ReadOut</h3>
  <p align="center">
"ReadOut" is a cross-platform app for Android and Desktop, built with Kotlin Multiplatform. It seamlessly integrates book discovery, audiobook playback, and AI-powered summaries in both text and audio formats. Discover new books, enjoy a diverse library of audiobooks, and access concise, insightful summaries — all designed to save your time and keep you informed.
    <br/>
    <br/>
    <a href="https://github.com/tawhidmonowar/ReadOut/releases">Download</a>
    .
    <a href="https://github.com/tawhidmonowar/ReadOut/issues">Report Bug</a>
    .
    <a href="https://github.com/tawhidmonowar/ReadOut/issues">Request Feature</a>
  </p>


[<img alt="Get it on OpenAPK" height="80" src="https://www.openapk.net/images/openapk-badge.png">](https://www.openapk.net/readout/org.tawhid.readout/)
[<img alt="Get it on GitHub" height="80" src="https://raw.githubusercontent.com/nucleus-ffm/foss_warn/main/docs/get-it-on-github.png">](https://github.com/tawhidmonowar/ReadOut/releases)
[<img alt="Direct APK Download" height="80" src="https://tachibanagenerallaboratories.github.io/images/badges/Direct%20Download/direct-apk-download.png">](https://github.com/tawhidmonowar/ReadOut/releases/download/v1.0.0/ReadOut.Android.v1.0.0.apk)

![GitHub License](https://img.shields.io/github/license/tawhidmonowar/ReadOut)
![Platforms](https://img.shields.io/badge/Platforms-Android%20%7C%20Desktop-brightgreen)
![GitHub Release](https://img.shields.io/github/v/release/tawhidmonowar/ReadOut?logo=github)
![GitHub Downloads (all assets, all releases)](https://img.shields.io/github/downloads/tawhidmonowar/ReadOut/total?logo=github)
![FreePalestine](https://raw.githubusercontent.com/tawhidmonowar/polyglot_ai/187d25e5f3acaa5af6b361d19053938cf6d3bf81/client/public/FreePalestine.svg)

</div>

## App Features
The ReadOut app offers 28 million books, 20,000+ audiobooks, and features like AI-powered insights, personalized book searches, favorite book saving, dual themes, and seamless audio playback on both desktop and mobile. It's currently in the early stages of development, so feel free to report any issues or suggestions.
- Audiobook Playback (Desktop and Mobile)
- Extensive Book Browsing
- AI-Powered Book Insights
- Book Insight by User Input
- Save Favorite Books
- Search Books by Title, Author, or Genre
- Dual Themes (Dark and Light)
- Audio Background Playback Support
- User-Friendly Interface with Easy Navigation

### Screenshots (Android)

|![Home Screen](https://github.com/tawhidmonowar/ReadOut/blob/main/readme/screenshots/0.jpg) | ![Search Screen](https://github.com/tawhidmonowar/ReadOut/blob/main/readme/screenshots/1.jpg) | ![Details Screen](https://github.com/tawhidmonowar/ReadOut/blob/main/readme/screenshots/3.jpg) |![Save Screen](https://github.com/tawhidmonowar/ReadOut/blob/main/readme/screenshots/2.jpg) |
|:-------------------:|:------------------------:|:-----------------:|:-----------------:|

### Screenshots (Desktop)

The UI design is fully responsive, ensuring a user-friendly experience across all devices, regardless of screen size, also the UX is carefully designed to be intuitive and enjoyable, providing a smooth experience for every user.

|![Home Screen](https://github.com/tawhidmonowar/ReadOut/blob/main/readme/screenshots/d2.gif) | ![Search Screen](https://github.com/tawhidmonowar/ReadOut/blob/main/readme/screenshots/d5.png) |
|:-------------------:|:------------------------:|

## User Guide

Discover Books

- Open the app and navigate to the OpenLibrary section.
- Use the search bar to find books by title, author, or genre.
- Browse curated recommendations and popular titles.

Play Audiobooks

- Go to the Audiobooks section and select a book.
- Tap the Play button to start listening.
- Use the playback controls to pause, rewind, or fast-forward.

Generate Summaries

- Open the details page of any book.
- Tap the Summarize button to generate an AI-powered text or audio summary.
- Alternatively, navigate to the Summarize section.
- Enter the required details and tap the Summarize button.

Save Books

- Tap the Save icon on any book to add it to your saved list.
- Access your saved books in the Saved section.

Switch Themes

- Open the app’s settings.
- Toggle between Light and Dark themes to customize the app’s appearance.

Clear Data

- Open the app’s settings.
- Tap Clear all data to remove any saved books or cache from the app.

## Installation

1. Clone the repository:
```
git clone https://github.com/tawhidmonowar/ReadOut.git
```
2. Open the project in Android Studio.
3. Place API keys in `commonMain -> core -> utils -> ApiKey.kt`
4. Build and run the project using Gradle.

### Requirements 
- [Vlcj](https://github.com/caprica/vlcj) (For Desktop Audio Playback)
- Goolge Gemini API Key
- Goolge Cloud Text to Speech API Key
- Android Compile SDK 35
- Android Gradle Plugin "8.7.3"
- Kotlin "2.1.0"

## Architecture Overview
This project follows the MVI (Model-View-Intent) design pattern combined with Clean Architecture principles to ensure a scalable, maintainable, and testable structure.

![image](https://github.com/tawhidmonowar/ReadOut/blob/main/readme/images/read_out_architecture.png)

* The Presentation Layer is responsible for rendering the UI and managing user interactions via a unidirectional flow of Intent, State, and ViewModel.

* The Domain Layer encapsulates business logic in UseCases, This layer is completely independent of other layers, ensuring that it can be reused and tested in isolation.

* The Data Layer abstracts data sources (API, Database) through a Repository pattern.



## Technologies and Libraries Used

This project is powered by a combination of awesome technologies and libraries, Below is a list of what’s used.

| Name                                      | Description                                                  |
|-------------------------------------------|--------------------------------------------------------------|
| [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) | Simplifies the development of cross-platform projects.       |
| [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform) | Builds responsive, modern user interfaces across multiple platforms. |
| [Kotlin Serialization](https://kotlinlang.org/docs/serialization.html) | Handles JSON and other formats with built-in serialization support. |
| [Material3](https://developer.android.com/jetpack/androidx/releases/compose-material3) | Provides Material Design 3 components and guidelines for UI design. |
| [Datastore Preferences](https://developer.android.com/topic/libraries/architecture/datastore) | A modern tool for storing key-value data locally.            |
| [Koin](https://insert-koin.io/)           | Manages dependencies in Kotlin apps with minimal effort.     |
| [Ktor Client](https://ktor.io/docs/getting-started-ktor-client.html) | Handles HTTP requests and networking in Kotlin.              |
| [Coil](https://coil-kt.github.io/coil)    | Loads images efficiently, designed specifically for Kotlin and Jetpack Compose. |
| [Media3 ExoPlayer](https://developer.android.com/media/media3/exoplayer) | Seamless audio and video playback for Android apps.          |
| [VLCJ](https://github.com/caprica/vlcj)   | Adds VLC media playback capabilities for desktop Java applications. |
| [SQLite](https://developer.android.com/jetpack/androidx/releases/sqlite) | An embedded SQL database for structured data storage.         |

## APIs Used
This project uses several APIs to enhance functionality and provide seamless user experiences. Below is a list of the APIs used in this project.

<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="22%"><a href="https://librivox.org/"><img src="https://github.com/tawhidmonowar/ReadOut/blob/main/readme/images/librivox_api_logo.png?raw=true" width="100px;" alt="LibriVox API"/><br /><sub><b>LibriVox API</b></sub></a><br /></td>
      <td align="center" valign="top" width="23%"><a href="https://openlibrary.org/"><img src="https://github.com/tawhidmonowar/ReadOut/blob/main/readme/images/open_library_api_logo.png?raw=true" width="100px;" alt="Open Library"/><br /><sub><b>Open Library API</b></sub></a><br /></td>
      <td align="center" valign="top" width="24%"><a href="https://ai.google.dev/"><img src="https://github.com/tawhidmonowar/ReadOut/blob/main/readme/images/google_gemini_api_logo.png?raw=true" width="100px;" alt="Google Gemini"/><br /><sub><b>Google Gemini API</b></sub></a><br/></td>
      <td align="center" valign="top" width="33%"><a href="https://cloud.google.com/text-to-speech"><img src="https://github.com/tawhidmonowar/ReadOut/blob/main/readme/images/cloud_text_to_speech_api_logo.png?raw=true" width="50px;" alt="Cloud Text-to-Speech"/><br /><sub><b>Cloud Text-to-Speech API</b></sub></a><br /></td>
    </tr>
  </tbody>
</table>

## Author
**Tawhid Monowar** - *Computer Science & Engineering Student* <br>
[LinkedIn](https://www.linkedin.com/in/tawhidmonowar) | [Portfolio](https://tawhidmonowar.github.io/profile)  | [Email](mailto:tawhidmonowar@gmail.com)
