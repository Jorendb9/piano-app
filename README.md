# Chipkeys
Instrument Application for Android with NES sample set and piano UI.

## Idea 

This application will allow the user to use the touch screen of their android phone/tablet to control a virtual representation
of a piano. On hitting a key, the corresponding sound file is played. This application is intended to either be used by musicians
to quickly try out new ideas on the go, or by anyone with a hankering for mid-1990s era videogame sounds.

Although many similar applications exist, they are often limited by the fact that touch screens on a lot of devices aren't
pressure-sensitive. Because of this, most piano applications sound somewhat lifeless because each note can only be played at a
single velocity. This application intends to solve this problem by letting the exact location where each key is tapped play a note at different velocities (hitting it near the top produces softer notes, getting closer to the bottom produces louder notes.)

Functionally this means that rather than a single set of audio samples, the application will use at least 3 sets, corresponding with 3 levels of velocity.

Additionally, in order to differentiate itself from similar applications, rather than trying to approximate existing instruments with maximum fidelity, the soundset used will be downsampled to conform with the standards of the .SPC format (used on the Super Nintendo Entertainment System videogame console). The benefit of this is that the comparatively small size of audio files allows for the program incorporate many different instrument sets.

## Features

- Playable 25-key piano-style user interface ( Amount of keys not set in stone just yet, depends on available screen space)
- Buttons to shift between octaves to functionally allow users access to the entire 88-key range of a piano.
- Multiple velocity layers for each soundset corresponding to key location.
- Multiple instrument soundsets (At least Piano, Strings, Electric Bass and Drums)
- Authentic SNES-style instrument sounds.

### Possible additional features (if feasible within time and technology constraints)

- Alternate UIs (Drumkit or Guitar)
- Additional instrument soundsets.
- Toggleable effects (Reverb, Delay, Chorus, Phaser, etc.)
- More advanced note manipulation (pitch wheel, ADSR envelope, filters)
- Allow users to record short musical passages and save them to the devices' internal storage



## Possible complications
- Audio streaming. The application has to be able to access sound files with as little latency as possible (by pre-loading them into memory, for example), and perform operations such as playing multiple sounds at once (polyphony). 

- Memory management. The audio files will have to be compressed or used in such a way so as to minimize the amount of memory required, especially given that each key corresponds to at least 3 audio samples.

- GUI. Due to the limited screenspace on mobile devices, fitting an entire 88-key piano on screen would be impractical, so the application would either have to be limited to a smaller range of sounds or allow the user to shift in octaves.

- Resampling. Each soundset could either use a single sample per layer, with the application itself altering the pitch depending on the key pressed, or each key could have a discrete sample mapped to it. Either approach has its advantages and disadvantages.


## Sketches
<img src="https://github.com/Jorendb9/piano-app/blob/master/docs/Sketch.png?raw=true width="220" height="400" />
