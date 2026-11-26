# Stations

An Android application for browsing stations and viewing their connected devices, using the provided [mock API](https://test-ucs-mock-api.vercel.app).

## Implementation

- **Jetpack Compose** and **Navigation Compose** for the UI and navigation.
- **MVVM** with a repository to handle data access.
- **Retrofit + Gson** for fetching and parsing API data.
- **Room + Flow** for local caching and keeping the UI updated.
- Dependencies are set up manually in `StationsApplication`.

The app uses Room as its main data source, while the repository fetches fresh data from the API. If a request fails, the cached data is kept, so users can still view stations and devices offline.

Database updates are handled in transactions to keep the data consistent. When stations are removed, their cached devices are also cleaned up.

Since the mock API occasionally fails or responds slowly, the app handles loading, empty, and error states, with an option to retry failed requests.

## Running

Open the project in Android Studio and run it on an emulator or physical device.

## Tests

- Unit tests to check the mapping between models and database entities.
- Repository tests using an in-memory Room database and a fake API to check that caching and data updates work correctly, including when requests fail or stations are removed.