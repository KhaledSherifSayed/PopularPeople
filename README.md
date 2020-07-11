<p align="center">
  <img src="Preview/stars.jpg" width="100%" height="350"/>
</p>

# Popular People 👓
[![GitHub license](https://img.shields.io/badge/License-Khaled-blue.svg)](LICENSE.txt)
[![Android Weekly](https://img.shields.io/badge/Android%20Weekly-%23406-2CA3E6.svg?style=flat)](http://androidweekly.net/issues/issue-406)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
![Github Followers](https://img.shields.io/github/followers/KhaledSherifSayed?label=Follow&style=social)
![GitHub forks](https://img.shields.io/github/forks/KhaledSherifSayed/PopularPeople?style=social)
![GitHub watchers](https://img.shields.io/github/watchers/KhaledSherifSayed/PopularPeople?style=social)
![Twitter Follow](https://img.shields.io/twitter/follow/Meslmawy?label=Follow&style=social)


**Popular People** is a sample  Android application 📱 showing stars of the world 👓 built to demonstrate use of *Modern Android development* tools. Dedicated to all Android Developers with ❤️. 

***You can Install and test latest Foodium app from below 👇***

[![Popular People App](https://img.shields.io/badge/Popular👓-APK-red.svg?style=for-the-badge&logo=android)](https://github.com/KhaledSherifSayed/PopularPeople/releases/latest/download/popular_people.apk)


## About
It simply loads **Popular People** data from API.Peoples will be always loaded from Remote data (from API). 
- Clean and Simple Material UI.
- Clean and Simple Architecture(MVVVM).
- It will supports soon offline capable 😃.

*Dummy API is used in this app. JSON response is statically hosted [here](https://developers.themoviedb.org/3)*.

## ScreenShots
<p align="center">
<img src="/Preview/main_screen.gif" width="32%"/>
<img src="/Preview/details_screen.gif" width="32%"/>
<img src="/Preview/open_photo.gif" width="32%"/>
</p>

## Built With 🛠
- [SharedElements](https://developer.android.com/training/transitions/start-activity) - Shared elemnts between fragments with Navigation Component.
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - 
  - [`Koin`](https://insert-koin.io/) DI Version 🗡️
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.
- [Moshi Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi) - A Converter which uses Moshi for serialization to and from JSON.
- [Glide](https://github.com/bumptech/glide) - An image loading library for Android backed by Kotlin Coroutines.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - For writing Gradle build scripts using Kotlin.


## Unit Testing Frameworks
Unit Tests verify the interactions of viewmodels between repositories and dao & REST api requests.
- [Robolectric](https://github.com/robolectric/robolectric) - Robolectric is the industry-standard unit testing framework for Android.
- [Mockito-Kotlin](https://github.com/nhaarman/mockito-kotlin) - a small library that provides helper functions to work with Mockito in Kotlin.

<p align="center">
  <img src="Preview/tests.PNG" width="100%" height="350"/>
</p>


**Contributed By:** [Khaled Sherif](https://github.com/KhaledSherifSayed)

# Package Structure
    
    com.meslmawy.ibtkarchallenge  # Root Package
    .
    ├── data                # For data handling.
    │   ├── remote          # Remote Data Handlers     
    |   │   ├── api         # Retrofit API for remote end point.
    │   └── repository      # Single source of data.
    |
    ├── model               # Model classes
        ├── dto             # Data Models         
    ├── di                  # Dependency Injection             
    │   ├── builder         # Activity Builder
    │   ├── component       # DI Components       
    │   └── module          # DI Modules
    |
    ├── ui                  # Activity/View layer
    │   ├── base            # Base View
    │   ├── main            # Main Screen Activity & ViewModel
    |   │   ├──Fragment     # Fragment
    |   │   └── viewmodel   # ViewModel for Main Fragmnet
    │   └── details         # Detail Screen Fragment and ViewModel
    |   │   ├─ Fragment     # Fragment
    |   │   └─ viewmodel    # ViewModel for Details Fragmnet 
    │   └── photo           # Photo Screen Fragment and ViewModel
    |   │   ├─ Fragment     # Fragment
    |   │   └─ viewmodel    # ViewModel for Details Fragmnet 
    │   ├── adapter         # Base Adapters Package
    |   │   ├  MainPopular  # Main adapter with ViewHolder for Popular People Items
    |   │   ├  Movies       # adapter with ViewHolder for Actor Movies
    |   │   └─ Images       # adapter with ViewHodler for Actor Images
    └── utils               # Utility Classes / Kotlin extensions

## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

## Soon 🎈💪
 - Posters will be always loaded from local database. Remote data (from API) and Local data is always synchronized.
 -  Try with [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
 
## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/KhaledSherifSayed/PopularPeople/stargazers)__ for this repository. :star: <br>
And __[follow](https://github.com/KhaledSherifSayed)__ me for my next creations! 🤩


## License
```

Copyright (c) 2020 Khaled  Sherif
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE

```
