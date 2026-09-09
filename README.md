# Ally
Ally — For the one's who took care of you.

A parent/elderly-care concierge companion app. This MVP covers the two core
concierge workflows:

- **Hospital appointments** — schedule, view, mark complete/cancel, delete.
- **Transportation arrangement** — book a ride (pickup/drop-off/time),
  confirm/complete/cancel, delete.

Plus a **Home** dashboard summarizing what's upcoming for both.

## Tech stack

- Kotlin + Jetpack Compose (Material 3)
- Navigation Compose (bottom nav: Home / Appointments / Rides)
- Room (local SQLite persistence, no backend needed for the MVP)
- MVVM: `AllyRepository` → `AllyViewModel` → Compose screens

## Project layout

```
app/src/main/java/com/ally/care/
  data/                 Appointment, RideRequest entities + Repository
  data/db/              Room database, DAOs, and TypeConverters
  ui/                   AllyApp (root composable) and AllyViewModel
  ui/home/              Dashboard screen
  ui/appointments/      Appointment list + add/edit screens
  ui/transportation/    Ride list + add/edit screens
  ui/common/            Shared date/time picker, status chips, formatting
  ui/theme/             Material 3 theme configuration (Color, Type)
  nav/                  Navigation routes and destinations
  MainActivity.kt
  AllyApplication.kt
```

## Running it

1. Open the project root in **Android Studio** (Koala or newer).
2. Let it sync — the IDE will download Gradle 8.7 automatically on first sync.
3. Run the `app` configuration on an emulator or device (min SDK 26).

No backend, API keys, or extra setup required — everything is stored locally
via Room.

## Next steps beyond the MVP

- Sync appointments/rides with a real backend or calendar.
- Push notification reminders before appointments/pickups.
- Caregiver/family member accounts and shared visibility.
- Medication reminders, emergency SOS button.
