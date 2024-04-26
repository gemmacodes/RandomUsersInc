# Random Users Inc

1. [Context](#context)
2. [Get started](#setup)
3. [Support](#support)

## Context

'Random Users Inc' is an Android app project developed by [@gemmacodes](https://github.com/gemmacode/) as a coding challenge.

It's a directory-like app, displaying random users thanks to the [Random User Generator API]([app/release/app-release.apk](https://randomuser.me/)).

Its main features are:
- Display a list of random users:
  - including name, surname, e-mail, phone number and picture.
  - with no duplicates and no previously removed users.
  - persistent across application sessions,
- Retrieve new random users and add them to the list by pressing a button.
- Search through the list by name, surname or e-mail.
- Delete users from the list by pressing a button.
- Display more details of a particular user by pressing a button. This detail view includes the information displayed on the list view plus gender, location (street, city and state) and registration date.

## Setup

### Dependencies

This project uses several additional libraries/dependencies:

- Room db (Local database)
- Retrofit (HTTP client) & Gson
- Koin (dependency injection)
- Jetpack Compose (UI)
- Compose navigation (navigation)
- Coroutines & Flows
- Glide (image loading)
- Mockito (testing)
- etc.

Do not forget to build your project so that everything runs smoothly!

## Support

Feel free to ask if there's any doubt :) 
