# 🎨 Kotlin UI Foundations: Art Gallery

A modern, offline-first Android application built to demonstrate state-of-the-art Android development practices. Originally conceived as a UI components catalog, this project evolved into a fully functional **Personal Art Gallery** featuring asynchronous image loading, local persistence for favorites, and a strict Unidirectional Data Flow (UDF) architecture.

## ✨ Key Features

* **Interactive Gallery:** Browse a curated collection of artwork with edge-to-edge image rendering.
* **Favorites System:** "Like" your favorite pieces. State is persisted locally and survives app restarts.
* **Type-Safe Navigation:** Completely eliminated string-based routes in favor of Kotlin Serialization for robust, crash-free navigation.
* **Seed Data Queue:** Built-in mechanism to dynamically load local or remote artwork into the database on demand.
* **Reactive UI:** The UI is completely driven by a centralized `StateFlow`, seamlessly handling `Loading`, `Success`, and `Error` states.

## 🛠 Tech Stack & Architecture

This project strictly adheres to **Modern Android Development (MAD)** guidelines and Clean Architecture principles.

* **Language:** 100% Kotlin
* **UI Toolkit:** Jetpack Compose (Material 3 Design System)
* **Architecture:** MVVM (Model-View-ViewModel) + UDF (Unidirectional Data Flow)
* **Local Database:** Room (SQLite abstraction)
* **Dependency Injection:** Dagger-Hilt (via KSP)
* **Navigation:** Jetpack Navigation Compose (v2.8+ with Type Safety)
* **Image Loading:** Coil
* **Asynchrony:** Kotlin Coroutines & Flow / StateFlow
* **Testing:** JUnit, MockK (Mocking), and Turbine (Flow testing)

## ©️ License & Copyright
The source code in this repository is licensed under the MIT License.
Note: The artwork and illustrations displayed within the application are the exclusive property of Bastihan and are not covered by the open-source license. All rights reserved.

## 📂 Project Structure

The codebase is modularized by feature/layer to ensure high cohesion and low coupling:

```text
com.example.kotlin_ui_foundations
│
├── data/           # Local Data Source (Room Database, DAOs, Entities)
├── di/             # Dependency Injection modules (Hilt)
├── navigation/     # Type-safe routing objects (@Serializable)
└── ui/             
    ├── common/     # Generic UI logic (e.g., UiState sealed interface)
    ├── components/ # Reusable, stateless Compose components (Atomic Design)
    ├── screens/    # Stateful screens and ViewModels
    └── theme/      # Material 3 typography, colors, and shapes
