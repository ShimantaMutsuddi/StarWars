
# StarWars

The StarWars - App was created using Kotlin, clean architecture, networking, local caching, threading, jetpack components, and lifecycle-aware components. 

## Build and Run
- Clone the Repository.
- Build and Run the Project.

**App features:**
- List of  characters
- Detail of characters
- List of planets
- List of starships
  
**Screenshots:**
  <table>
  <tr>
     <td>Characters Screen</td>
     <td>Character Detail Screen</td>
     <td>Planets Screen</td>
     <td>Starships Screen</td>
  </tr>
  <tr>
    <td><img src="app/screenshots/characters.jpeg" width=300 ></td>
    <td><img src="app/screenshots/characterdetails.jpeg" width=300 ></td>
    <td><img src="app/screenshots/planets.jpeg" width=300 ></td>
    <td><img src="app/screenshots/starships.jpeg" width=300 ></td>
  </tr>
 </table>
 <br> If you appreciate what you see, please star⭐ the repository.


## Code Flow:
```
app
│
├── data
│   ├──adapter // Adapters for RecyclerViews
│   ├── local
│   │   ├── dao         // Room database DAOs
│   │   └── database    // Room database setup
│   │
│   ├── remote
│   │   ├── api         // Retrofit API interfaces
│   │
│   ├── datasources          // Paging3 with remote mediator for pagination
│   │
│   ├── model           // Network model and entities
│   │
│   ├── repository      // Repository pattern for data management
│   │
│
├── di                  // Dependency Injection setup
│
├── ui
│   ├── common          // Common UI components, extensions, etc.
│   │
│   ├── features
│   │   ├── feature1
│   │   │   ├── view    // Fragments
│   │   │   ├── viewmodel   // ViewModels
│   │   │
│   │   ├── feature2
│
├── utils               // Utility classes and extensions
│
└── StarWarsApplication.kt     // Application class



```
## Tech stack - Library:

- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
- [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - for dependency injection.
- JetPack
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - For reactive style programming (from VM to UI). 
  - [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) - Used get lifecyle event of an activity or fragment and performs some action in response to change
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - Used to create room db and store the data.
  - [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) - Used to navigate between fragments
  - [View Binding](https://developer.android.com/topic/libraries/view-binding) - Makes it easier to write code that interacts with views.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
- [Retrofit](https://github.com/square/retrofit) - Used for REST api communication.

  ## Testing

- Character Details ViewModel

## TODO
- [X] Character List Screen
- [X] Character Details Screen
- [X] Starship List and Planet List Screens
- [X] Offline Support
- [X] Error Handling
- [X] Unit Tests (Not finished yet)
