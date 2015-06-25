# Chipkey

Instrument Application for Android with NES sample set and piano-style UI.

## General 

This application allows the user to use the touch screen of their android phone/tablet to control a virtual representation of a piano. On hitting a key, the corresponding sound file is played. This application is intended to either be used by musicians to quickly try out new ideas on the go, or by anyone with a hankering for mid-1990s era videogame sounds.

Although many similar applications exist, they are often limited by the fact that touch screens on a lot of devices aren't
pressure-sensitive. Because of this, most similar applications sound somewhat lifeless because each note can only be played at a single velocity. This application intends to solve this problem by letting the exact location where each key is tapped play a note at different velocities (hitting it near the top produces softer notes, getting closer to the bottom produces louder notes.)

Aside from controlling the initial note volume by using the touch location to determine it, the piano has a programmable aftertouch functionality, which can be set to control volume, pitch or vibrato intensity. This means that, for example, a user can cause a note to oscillate in volume by moving their finger up and down while holding the key down.

As a secondary audio parameter controller, the application boasts a programmable mod wheel as well. This can be set to control, for example, the time it takes for a note to fade in or fade out.

In addition to the volume envelope and manual pitch and volume control, the application use a simple LFO (low frequency oscillator) that can be set to control a notes pitch in a semi-automated fashion, causing it to rapidly oscillate and produce a vibrato effect.

Additionally, in order to differentiate itself from similar applications, rather than trying to approximate existing instruments with maximum fidelity, the soundset used conforms to the standards of the .NSF format (used on the Nintendo Entertainment System videogame console), giving the application more of a retro aesthetic.

## Features

- Scrollable 37-key (3-octave) piano-style user interface
- Programmable aftertouch and mod wheel functionality.
- Simple ADSR Envelope and LFO functionality to allow for more advanced sound manipulation.
- Authentic NES-style instrument sounds.



## Images
<img src="https://github.com/Jorendb9/piano-app/blob/master/docs/main.png?raw=true width="220" height="400" />
<img src="https://github.com/Jorendb9/piano-app/blob/master/docs/fx_activity.png?raw=true width="220" height="400" />
