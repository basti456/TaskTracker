# TaskTracker

A modern, efficient, and beautifully designed task management application for Android. TaskTracker helps you stay organized and productive by providing a seamless experience for managing your daily tasks, setting priorities, and keeping track of deadlines.

## ✨ Features

- **📝 Comprehensive Task Management**: Easily create, edit, and delete tasks with detailed descriptions.
- **🔍 Smart Search & Filtering**: Quickly find tasks using the search bar and filter them by categories (Work, Personal, Shopping, Health, etc.).
- **🚩 Priority Levels**: Stay focused by assigning Low, Medium, or High priority to your tasks.
- **📅 Due Dates & Reminders**: Set deadlines and enable reminders so you never miss a beat.
- **📎 Attachments**: Support for adding file attachments to your tasks for easy access to related documents.
- **🌗 Dark & Light Mode**: A fully adaptive UI that supports both dark and light themes for comfortable use at any time.
- **📦 Swipe-to-Action**: Intuitive swipe-to-delete gestures for quick list management.

## 🛠 Tech Stack

- **[Kotlin](https://kotlinlang.org/)**: 100% Kotlin-based project for modern and safe development.
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)**: Modern toolkit for building native UI using a declarative approach.
- **[Navigation 3](https://developer.android.com/guide/navigation)**: Leveraging the latest Android Navigation framework.
- **[Koin](https://insert-koin.io/)**: Pragmatic and lightweight dependency injection.
- **[Room Database](https://developer.android.com/training/data-storage/room)**: Robust local data persistence.
- **[Coroutines & Flow](https://kotlinlang.org/docs/flow.html)**: Asynchronous programming and reactive state management.
- **[Material 3](https://m3.material.io/)**: Adhering to the latest Material Design guidelines for a modern look and feel.

## 📸 Screenshots

| Task List | Add Task | Dark Mode |
| :---: | :---: | :---: |
| ![Task List Screen](https://via.placeholder.com/200x400?text=Task+List) | ![Add Task Screen](https://via.placeholder.com/200x400?text=Add+Task) | ![Dark Mode Screen](https://via.placeholder.com/200x400?text=Dark+Mode) |

> [!TIP]
> Replace the placeholder images above with actual screenshots of your app by adding them to the `/screenshots` folder!

## 🚀 Getting Started

### Prerequisites

- Android Studio Koala (or newer)
- Android SDK 34+
- Java 17

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/TaskTracker.git
   ```
2. Open the project in Android Studio.
3. Sync Project with Gradle Files.
4. Run the app on your emulator or physical device.

## 📁 Project Structure

```text
app/
├── src/main/java/com/example/tasktraker/
│   ├── di/             # Dependency Injection (Koin)
│   ├── models/         # Data Models
│   ├── navigation/     # Navigation 3 Configuration
│   ├── repository/     # Data Layer (Room Repository)
│   ├── screens/        # UI Screens (Jetpack Compose)
│   ├── ui/theme/       # Material 3 Theme & Colors
│   └── viewModels/     # State Management
└── ...
```

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
