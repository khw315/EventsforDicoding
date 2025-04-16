# EventsforDicoding

An Android application built with Kotlin for managing and viewing event details. Developed as part of Dicoding's [Belajar Fundamental Aplikasi Android](https://www.dicoding.com/academies/14-belajar-fundamental-aplikasi-android) course in the [Android Developer](https://www.dicoding.com/learningpaths/7) learning path, this project showcases the implementation of modern Android development practices using the MVVM architecture. The app allows users to mark events as favorites, switch between light and dark themes, and customize notification preferences. It is carefully crafted with a focus on performance, adherence to best practices, an intuitive user experience, and support for multiple languages.

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
- **UI**: RecyclerView, Material Components, View Binding
- **Other**: AndroidX, Navigation Component, Dark Mode, SharedPreferences

### Prerequisites

Before you begin, make sure you have the following software installed:

- **Android Studio** (latest stable version recommended)
- **Git** (for cloning the repository)

## Getting Started

To run this project locally:

1. Clone the repository:
   Open a terminal and run the following command to clone the repository:
    ```git clone https://github.com/khw15/EventsforDicoding.git```

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

## 📝 License

This project is licensed under the **MIT License**. See the [LICENSE](./LICENSE) file for more details.
