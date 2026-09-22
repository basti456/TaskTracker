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
<table>
  <tr>
<td><img width="200"  alt="Screenshot_20260921_213555" src="https://github.com/user-attachments/assets/99e36399-2615-47dc-9683-431915732f4b" />
<td><img width="200" alt="Screenshot_20260921_213840" src="https://github.com/user-attachments/assets/4b4114d8-1fde-4a95-b8ae-db0de46b4b8e" />
<td><img width="200"  alt="Screenshot_20260921_213855" src="https://github.com/user-attachments/assets/1cb4e21f-b127-4483-bc83-dda40d74adda" />
<td><img width="200"   alt="Screenshot_20260921_213949" src="https://github.com/user-attachments/assets/8b2e6215-cac5-4f71-93e3-02b14e011c97" />
<td><img width="200"  alt="Screenshot_20260921_214241" src="https://github.com/user-attachments/assets/7917a745-b085-40ca-8822-9f7d6f9b652f" />
  </tr>
</table>


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

## ⚙️ CI/CD & Release Builds

This project uses **GitHub Actions** for automated build and release workflows ([.github/workflows/android.yml](.github/workflows/android.yml)).

On every push to `main` or manual workflow trigger, GitHub Actions compiles signed **Release APKs** and **Android App Bundles (AAB)** and uploads them as workflow artifacts.

### Setting Up GitHub Repository Secrets

To enable signed release builds in GitHub Actions, configure the following secrets in your GitHub repository under **Settings > Secrets and variables > Actions**:

| Secret Name | Description |
| :--- | :--- |
| `KEYSTORE_BASE64` | Base64-encoded string of your release `.jks` keystore file |
| `KEYSTORE_PASSWORD` | Password for your release keystore |
| `KEY_ALIAS` | Alias name for your key |
| `KEY_PASSWORD` | Password for your key alias |

> **Note:** Do not commit `release.jks` or keystore credentials to version control. The CI pipeline dynamically decodes the keystore string onto the runner during execution.

### Building Locally

- **Debug Build:**
  ```bash
  ./gradlew assembleDebug
  ```
- **Release Build:**
  Place `release.jks` in the root folder and set local environment variables before running:
  ```bash
  export KEYSTORE_PASSWORD="your_keystore_password"
  export KEY_ALIAS="your_key_alias"
  export KEY_PASSWORD="your_key_password"
  ./gradlew assembleRelease
  ```

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
