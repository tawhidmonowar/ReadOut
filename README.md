<p align="center">
  <img src="https://github.com/user-attachments/assets/bf233e9a-0f3a-4218-9791-18574a45565e" alt="ReadOut Logo" width="120">
</p>
<h3 align="center">ReadOut</h3>
<p align="center">
"ReadOut" is a cross-platform app for Android and Desktop, built with Kotlin Multiplatform. It seamlessly integrates book discovery, audiobook playback, and AI-powered summaries in both text and audio formats. Discover new books, enjoy a diverse library of audiobooks, and access concise, insightful summaries — all designed to save your time and keep you informed.
    <br/>
    <br/>
    <a href="https://github.com/tawhidmonowar/ReadOut/releases">Download</a>
    •
    <a href="https://github.com/tawhidmonowar/ReadOut/issues">Report Bug</a>
    •
    <a href="https://github.com/tawhidmonowar/ReadOut/issues">Request Feature</a>
</p>

## App Features
The ReadOut app offers 28 million books, 20,000+ audiobooks, and features like AI-powered insights, personalized book searches, favorite book saving, dual themes, and seamless audio playback on both desktop and mobile.
- Audiobook Playback (Desktop and Mobile)
- Extensive Book Browsing
- AI-Powered Book Insights
- Book Insight by User Input
- Save Favorite Books
- Search Books by Title, Author, or Genre
- Dual Themes (Dark and Light)
- Audio Background Playback Support
- User-Friendly Interface with Easy Navigation

## Screenshots (Android)

|![Home Screen](https://github.com/tawhidmonowar/ReadOut/blob/main/readme/screenshots/0.jpg) | ![Search Screen](https://github.com/tawhidmonowar/ReadOut/blob/main/readme/screenshots/1.jpg) | ![Details Screen](https://github.com/tawhidmonowar/ReadOut/blob/main/readme/screenshots/3.jpg) |![Save Screen](https://github.com/tawhidmonowar/ReadOut/blob/main/readme/screenshots/2.jpg) |
|:-------------------:|:------------------------:|:-----------------:|:-----------------:|

### Screenshots (Desktop)

The UI design is fully responsive, ensuring a user-friendly experience across all devices, regardless of screen size, also the UX is carefully designed to be intuitive and enjoyable, providing a smooth experience for every user.

|![Home Screen](https://github.com/tawhidmonowar/ReadOut/blob/main/readme/screenshots/d2.gif) | ![Search Screen](https://github.com/tawhidmonowar/ReadOut/blob/main/readme/screenshots/d5.png) |
|:-------------------:|:------------------------:|

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

This project is powered by a combination of awesome technologies and libraries, Below is a list of what’s used in this project.

| Name                 | Description                                                                                 |
|-------------------------|---------------------------------------------------------------------------------------------|
| [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)    |  The Kotlin Multiplatform technology is designed to simplify the development of cross-platform projects. |
| [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform)   | A framework to build responsive, modern user interfaces that work on multiple platforms.    |
| [Kotlin Serialization](https://kotlinlang.org/docs/serialization.html)    | Simplifies working with JSON and other formats by offering built-in serialization support.  |
| [Material3](https://developer.android.com/jetpack/androidx/releases/compose-material3) | Provides the Material Design 3 components and guidelines for the UI design. |
| [Datastore Preferences](https://developer.android.com/topic/libraries/architecture/datastore) | A fast and modern replacement for SharedPreferences to store key-value data locally.        |
| [Koin](https://insert-koin.io/)                    | A lightweight framework for managing dependencies in Kotlin apps with minimal effort.  |
| [Ktor Client](https://ktor.io/docs/getting-started-ktor-client.html)      | A powerful and easy-to-use library for handling HTTP requests and networking in Kotlin.     |
| [Coil](https://coil-kt.github.io/coil)             | A fast and efficient image loading library designed specifically for Kotlin and Jetpack Compose. |
| [Media3 ExoPlayer](https://developer.android.com/media/media3/exoplayer) | A library for seamless audio and video playback in Android apps.                    |
| [VLCJ](https://github.com/caprica/vlcj)           | Provides VLC media playback capabilities for desktop Java applications.                     |
| [SQLite](https://developer.android.com/jetpack/androidx/releases/sqlite)  | An embedded SQL database for handling structured data storage in app.                  |


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
