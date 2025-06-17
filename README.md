# BooksLibrary

Aplikacja do wyszukiwania i zarządzania książkami z wykorzystaniem Google Books API oraz lokalnej bazy danych (Room).

## Funkcje

- Wyszukiwanie książek po tytule lub autorze (Google Books API)
- Dodawanie i usuwanie książek z ulubionych (lokalna baza Room)
- Przeglądanie szczegółów książki
- UI zbudowane w Jetpack Compose
- Architektura MVVM

## Technologie

- Kotlin
- Jetpack Compose
- Room (baza danych)
- Retrofit (komunikacja z API)
- StateFlow (zarządzanie stanem)
- MVVM

## Uruchomienie

1. Sklonuj repozytorium:
  git clone https://github.com/tymonzielinski/BooksLibrary.git
2. Otwórz projekt w Android Studio.
3. Uruchom aplikację na emulatorze lub urządzeniu.

## Struktura projektu

- `data/` — warstwa danych (Room, Retrofit, repozytorium)
- `model/` — modele danych
- `ui/` — ekrany i komponenty UI (Jetpack Compose)
- `viewmodel/` — logika biznesowa (ViewModel)
