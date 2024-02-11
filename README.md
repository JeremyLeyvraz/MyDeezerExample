# My Deezer example

## Summary
Application similar to Deezer.

It plays locally stored audio tracks.
It is possible to play a track, pause it, skip to the next or previous one.

The application handles portrait/landscape format.
The application has been tested on both phones and tablets.

The track is played by a foreground service, with an associated notification.

Warning: Currently, to stop the service, it is necessary to force stop the application.

## Hardware used for testing

Phone: Samsung Galaxy Z Flip 5 - Android 14
Tablet: Samsung Galaxy Tab S6 Lite - Android 12

# TODO

## Features
- Custom foreground notification => design mini player
- Adding a screen to choose different playlists
- Player: adding the "repeat one" function
- Player: adding the "repeat all" function
- Allow total application shutdown without using "force stop"

## Refactoring
- Use type and color from the theme package
- Use higher quality images

## Bugs
- Foreground notification: (close app + click notification) multiple times => multiple possible clicks on back button => check the activity stack
