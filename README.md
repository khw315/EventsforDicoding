# Events for Dicoding

An Android application built with Kotlin for managing and viewing event details. Developed as part of Dicoding's [Belajar Fundamental Aplikasi Android](https://www.dicoding.com/academies/14-belajar-fundamental-aplikasi-android) course in the [Android Developer](https://www.dicoding.com/learningpaths/7) learning path, this project showcases the implementation of modern Android development practices using the MVVM architecture. The app allows users to mark events as favorites, switch between light and dark modes, and customize notification preferences. It is carefully crafted with a focus on performance, adherence to best practices, an intuitive user experience, and support for multiple languages.

## Features

- ✅ Browse a list of finished events  
- 🔍 Search events by title  
- 📄 View detailed event information  
- ❤️ Like/unlike events with persistent storage using Room  
- 🌐 Supports multiple languages (English, Indonesian, and Korean)  
- 🧼 Clean and responsive UI following Material Design guidelines  

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Data Binding**: LiveData, ViewModel
- **Networking**: Retrofit, Coroutines, Dicoding Event API
- **Local Storage**: Room Database
- **UI Components**: RecyclerView, Material Components, View Binding
- **Other**: AndroidX, Navigation Component, Dark Mode, SharedPreferences

## Prerequisites

Before you begin, make sure you have the following software installed:

- **Android Studio** (latest stable version recommended)
- **Git** (for cloning the repository)

## Getting Started

To run this project locally:

1. Open a terminal and run the following command to clone the repository:

    ```bash
    git clone https://github.com/khw315/EventsforDicoding.git
    ```

2. Open the project in Android Studio:
    - Open Android Studio.
    - Select Open an `existing Android Studio project`.
    - Navigate to the cloned repository and select it.

3. Build the project:
    - Android Studio will automatically build the project.
    - If the build does not start automatically, select `Build > Make Project` from the menu.

4. Run the app:
    - Connect your Android device or use an emulator.
    - Click the green play button to run the app on the device.

## App Structure

- **Home**: Displays two categories of events (Upcoming & Finished) using a RecyclerView.
- **Detail Page**: Shows detailed event information (name, date, description) and includes a link to the event’s official webpage.
- **Favorites**: Users can save events to their favorites list, which is stored in the Room Database for persistence.
- **Settings**:
  - **Dark Mode**: Users can toggle between light and dark themes.
  - **Notification Settings**: Manage event notifications (enable/disable).
  - **Network Connectivity Check**: The app monitors internet status and notifies the user if the device is offline.

## Usage

Once the app is up and running, you can:

- **Browse Events**: Easily navigate between upcoming and finished events on the home screen.
- **View Event Details**: Tap on any event to access detailed information, including a link to the event’s official webpage.
- **Favorite Events**: Add events to your favorites list for quick access later.
- **Customize Settings**: Enable dark mode and manage notification preferences from the settings page.
- **Offline Handling**: The app detects when the device is offline and notifies the user accordingly.

## License

This project is distributed under the **MIT License**. See the [LICENSE](./LICENSE) file for more details.

## Contact

For any inquiries or feedback, feel free to reach out to me at [email](mailto:contact@alfaisal.my.id) or connect with me on [LinkedIn](https://www.linkedin.com/in/fafr/).

## Acknowledgments

This project was developed as part of Dicoding's [Belajar Fundamental Aplikasi Android](https://www.dicoding.com/academies/14-belajar-fundamental-aplikasi-android) course and [Android Developer](https://www.dicoding.com/learningpaths/7) learning path, which gave me a deeper understanding of Android development.

This project was made possible with the help of various resources and tools. Special thanks to:

- [Android Developers](https://developer.android.com/) for documentation and best practices.
- [Android Studio](https://developer.android.com/studio) for development environment.
- [AndroidX](https://developer.android.com/jetpack/androidx) for modern Android development.
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) for asynchronous programming.
- [Git](https://git-scm.com/) for version control.
- [GitHub](https://github.com/) for hosting the project.
- [Glide](https://github.com/bumptech/glide) for image loading.
- [Kotlin](https://kotlinlang.org/) for programming language.
- [Lottie](https://airbnb.io/lottie/#/) for animations.
- [Material Design](https://material.io/) for design guidelines.
- [Navigation Component](https://developer.android.com/guide/navigation) for navigation.
- [Retrofit](https://square.github.io/retrofit/) for networking.
- [Room](https://developer.android.com/training/data-storage/room) for local database management.
- [SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences) for simple data storage.
- [Shimmer](https://github.com/facebookarchive/shimmer-android) for loading animations.
- And many more!

한국어 버전은 [README.ko.md](README.ko.md) 파일에서 확인하세요.
