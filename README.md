## Architecture Overview
This project follows the MVI (Model-View-Intent) design pattern combined with Clean Architecture principles to ensure a scalable, maintainable, and testable structure.

![image](https://github.com/tawhidmonowar/ReadOut/blob/main/readme/images/read_out_architecture.png)

* The Presentation Layer is responsible for rendering the UI and managing user interactions via a unidirectional flow of Intent, State, and ViewModel.
* The Domain Layer encapsulates business logic in UseCases, This layer is completely independent of other layers, ensuring that it can be reused and tested in isolation.
* The Data Layer abstracts data sources (API, Database) through a Repository pattern.

## API Usage Overview

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
