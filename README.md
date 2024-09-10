# Open Movie Database 
> [!CAUTION] 
> The project is deprecated and going to be replaced with [Clean-Architecture](https://github.com/AntonShapovalov/Clean-Architecture).

Android application concept to search movie's info using [OMDB API](https://www.omdbapi.com) and save the result to the local DB.

### Screen structure

Applicaion uses Single Activity approach and JetPack Navigation libraty. Each screen is a fragment with dedicated view model:
1. Search screen - searches movies by title and displays the list of results.
2. Info screen - displays the full movie's info.

### How to install

Please find **app-release.apk** file inside [release](./app/release) folder.

### Used language and libraries
 * [Kotlin](https://kotlinlang.org/docs/tutorials/kotlin-android.html) - primary project language.
 * [Navigation component](https://developer.android.com/guide/navigation) - handles all navigation aspects, allows to avoid boilerplate code with fragments transaction, backstack etc.
 * [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) - the core of [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) pattern.
 * [AndroidX testing](https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-testing-basics/#0) - library to test all layers of app code.
 * [Dagger](https://codelabs.developers.google.com/codelabs/android-dagger/#0) - dependency injection framework.
 * [RxJava](https://github.com/ReactiveX/RxJava) - simple way to manage data chains.
 * [Retrofit](https://square.github.io/retrofit/) - to perform API call.
 * [GreenDao](https://greenrobot.org/greendao/) - ORM, to cache data in local SQLite DB.
 * [Glide](https://bumptech.github.io/glide/) - images loading library with cache possibility.
 * [Timber](https://github.com/JakeWharton/timber) - awesome logging on top of Android's normal `Log` class.
 * [Floating Search View](https://github.com/arimorty/floatingsearchview) - the compromise between native search and autocomplete view with suggestions.
 
