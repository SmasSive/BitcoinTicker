# Bitcoin Ticker

[![Build Status](https://travis-ci.org/SmasSive/BitcoinTicker.svg?branch=master)](https://travis-ci.org/SmasSive/BitcoinTicker)

An Android application that fetches information about the current Bitcoin market price and display the data in a line graph.

This project aims to be a demonstration of a clean architecture implemented with:
* Android Architecture Components (LiveData, ViewModel, Navigation)
* Dagger
* RxJava
* Room database
* Retrofit
* [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)

## Modules:

Application is divided in the following modules of Android Studio:

### app
The main entrance for the application, is the responsible for navigate through app screens (Navigation)

### core
The common module that contains shared classes between modules.

### charts
Charts feature module responsible for showing a Bitcoin Market Price chart.
It's also divided in the following packages:

**presentation**

Implemented with MVVM pattern, is responsible for requesting the desired chart and update automatically (via LiveData) the UI for the user.

**domain**

Business logic of the app. In this case there is no much to say, just that it will only contain Java, knows nothing about Android or another SDKs.

**data**

Layer responsible for querying the data. The core of this module is the repository, responsible for getting the data from local or remote data store.

It's important to mention that this is an offline first application, it means that the app will try to return first the local data stored in a local DB (Room) and if there is no data, go to the API (Retrofit) and store it locally.

Another thing to keep in mind is that this local data is not always valid, it's invalidated (app forces the request to API) once data is older than the last chart value plus the period (in that case 1 day)

And last, but not least, if data is invalid but we are not able to request from API (for example no internet connection), we will return the stored data when possible, that way user can see it whenever she wants.

## Testing
The main classes of the app are tested. Following the test pyramid, there are more unit tests than instrumentation tests and just one ui test (just one per screen)

I've used tools like Mockito and Mockito Kotlin to mock dependencies between classes.

You'll see an annotation `@OpenClassOnDebug`, this annotation is part of `kotlin-allopen` plugin that "opens" the Kotlin classes on debug just for testing purposes. As you know this is necessary because by default Kotlin classes are final and Mockito cannot mock them.

I've made use of [Barista](https://github.com/SchibstedSpain/Barista) for UI tests, as they define it, Barista is the guy who serves a great Espresso.

And thanks to [DaggerMock](https://github.com/fabioCollini/DaggerMock) I am able to replace dagger provided dependencies with mocks.

## Future
### Features
- [ ] Add chart personalization
- [ ] Allow chart selection
- [ ] Add stats and pools data
- [ ] Add a cool icon!

### Tech
- [ ] Koin for DI
- [ ] Coroutines

# License
**[Apache License, Version 2.0 (the "License")](LICENSE.txt)**
