# Piano App
Piano Application for Android

## Idea 

This application will allow the user to use the touch screen of their android phone/tablet to control a virtual representation
of a piano. On hitting a key, the corresponding sound file is played. This application is intended to either be used by musicians
to quickly try out new ideas on the go, or by anyone 

Although many similar applications exist, they are often limited by the fact that touch screens on a lot of devices aren't
pressure-sensitive. Because of this, most piano applications sound somewhat lifeless because each note can only be played at a
single velocity. This application intends to solve this problem by adding a velocity slider that allows users to instantly alter
the velocity levels with which the keys are struck. This will allow users to play with more dynamic expressions 
than would normally be possible on touchscreens that lack pressure sensitivity.

Functionally this means that rather than a single set of audio samples, the application will use at least 3 sets, corresponding with 3 levels of velocity.



## Possible complications
- Audio streaming. The application has to be able to access sound files with as little latency as possible (by pre-loading them into memory, for example), and perform operations such as playing multiple sounds at once (polyphony). 

- Memory management. The audio files will have to be compressed or used in such a way so as to minimize the amount of memory required, especially given that each key corresponds to at least 3 audio samples.

- GUI. Due to the limited screenspace on mobile devices, fitting an entire 88-key piano on screen would be impractical, so the application would either have to be limited to a smaller range of sounds or allow the user to shift in octaves.


## Sketches
