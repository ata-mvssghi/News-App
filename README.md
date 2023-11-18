# News App

## Overview

This Android application leverages "The Guardian" API to fetch news articles using the Retrofit library. The retrieved data is stored in a Room database for efficient offline access. The app features a user-friendly interface with a ViewPager containing six different pages (fragments), each focusing on a specific subject such as sports, science, general news, society, and more.

![Screenshot_2023-09-12-23-46-03-564_com example project](https://github.com/ata-mvssghi/News-App/assets/99190904/a819f47e-b48e-467d-b33e-08091d0481fe)

![Screenshot_2023-09-12-23-45-59-327_com example project](https://github.com/ata-mvssghi/News-App/assets/99190904/c21b776e-87e9-478e-b4c2-af15df84a5e9)

![Screenshot_2023-09-12-23-48-45-996_com example project](https://github.com/ata-mvssghi/News-App/assets/99190904/c3163805-927d-4b6e-92d5-373f4fd949a1)

## Features

1. **API Integration**: Utilizes Retrofit to interact with "The Guardian" API and fetch the latest news articles.

2. **Offline Storage**: Saves retrieved data in a Room database for offline access, ensuring users can read news even without an active internet connection.

3. **ViewPager with Fragments**: Organizes news content into a ViewPager with separate fragments for different subjects, providing a seamless browsing experience.

4. **Pagination**: Implements pagination to continuously fetch data from the API as users scroll down, ensuring a constant stream of up-to-date news articles.

5. **RecyclerView**: Uses RecyclerView to display news items, enabling efficient handling of large data sets and smooth scrolling.

6. **Sharing Functionality**: Enables users to share individual news articles using implicit intents.

7. **WebView Integration**: Allows users to tap on news items, opening a WebView fragment for a more detailed view of the article.

8. **Shared Preferences**: Utilizes Shared Preferences to store user preferences, including options for adjusting font size, selecting themes, and setting a specific date to filter news articles.

9. **Navigation Drawer**: Incorporates a navigation drawer for easy navigation between fragments, providing an alternative to swiping through the ViewPager.

## Getting Started

1. Clone the repository to your local machine.

   ```bash
  https://github.com/ata-mvssghi/News-App.git
   ```

2. Open the project in Android Studio.

3. Build and run the app on an Android emulator or physical device.

## Dependencies

- Retrofit: [link to Retrofit](https://square.github.io/retrofit/)
- Room: [link to Room](https://developer.android.com/topic/libraries/architecture/room)
- RecyclerView: [link to RecyclerView](https://developer.android.com/jetpack/androidx/releases/recyclerview)
- coroutines


## Contributing

Feel free to contribute by opening issues, suggesting features, or submitting pull requests. Your contributions are greatly appreciated.
---

Happy coding! 🚀
