# Design

## Basic functionality

- SoundPool
- buttonOnClick
- OnClickListener

The application is centered around the use of the SoundPool class. Depending on the musical instrument selected by the user, the class loads the appropriate array of audio samples into memory and plays them back according to the key hit by the user.

### Velocities

As shown in the image below, each 'key' on the piano is divided into three clickable areas by overlaying the image with three invisible buttons in the appropriate positions. Each key plays the corresponding sample(s) at a different 'velocity' (a.k.a. volume level), allowing for dynamic playing (variations in volume). 

This method is currently fairly crude, but increasing the amount of subdivisions for each key could make the variations in volume far more gradual and 'natural'. Another possible improvement would be to use a single clickable area for each key but to check the y-coordinates of the touch location, and use those to make the program determine the volume level and sample use. 

<img src="https://github.com/Jorendb9/piano-app/blob/master/docs/Piano%20Sketch.png?raw=true width="220" height="400" />

### Octaves

A phone screen can only represent a fraction of the full range of a piano keyboard. In order to remedy this, users have to be able to go up or down an octave in order to access the higher and lower registers of an instrument. In this application this functionality is controlled by the two buttons shown in the image above.

### Variable note length

Another measure to make the instrument feel more realistic is to use OnClickListener in such a way that the audio samples only play for as long as the button is held down, rather than simply triggering them with a touch. This means that a short tap will only play a fraction of the sample, while holding a button down will play the entirety of the sample.

### Looping
- SoundPool

Certain instruments (Violins, organs) are capable of producing notes at a constant volume level and essentially indefinite duration. Using long duration samples (10+ seconds) to represent this quality is highly impractical due to memory constraints. Instead, a sample should seamlessly loop at some point. 

This can typically not be achieved by simply looping the entire sample because the start of a note (also known as the 'Attack') is often very distinct from the rest of the sound (such as the percussive click when hitting a note on an organ). Instead, each sample that is intended to loop has to be cut into two parts, with only the second part looping. The first part will play *once* when the user hits a key, and as long as the key is held, the second part will keep playing and looping indefinitely.


## Navigation

GestureDetector.OnGestureListener

Users can move to the advanced FX settings screen by swiping to the right.


<img src="https://github.com/Jorendb9/piano-app/blob/master/docs/FX%20Screen%20Sketch.png?raw=true width="220" height="400" />


## The FX activity

- SoundPool
- ButtonOnClick
- ToggleOnClick
- VerticalSeekBar
- OnSeekBarChangeListener
- Spinner
- OnItemSelectedListener
- PresetReverb

In the FX activity, users can use some more advanced parameters to play around with the sound.

### ADSR

The four vertical sliders are intended to function as a so-called ADSR envelope. This is a feature found in most synthesizers that allows users to 'shape' the character of a sound through advanced volume control. As an example of its functionality, the A-slider (A stands for attack) determines how quickly a sample fades in after a key has been hit. If it's set to 0, the sample simply plays instantly at full volume, but as the slider is turned up, the sound comes in more gradually. 

This will be done by taking integer values from each slider, and then using them to control SoundPools volume levels.

### FX

Reverb and Delay are two effects that affect the sound as a whole and can be toggled on or off. Reverb uses the PresetReverb class to simulate the sound being played in a larger space. When Delay is active and a note is played, the same note is automatically retriggered after a certain(small) amount of time at a lower volume level. This creates the illusion of an artificial echo.

### Instrument selection

A spinner is used to select from available instrument sample sets. On selection, the old samples are immediately unloaded from the SoundPool and replaced by the new ones.


### UI (Optional, if feasible within time constraints)

Allows users to switch to a drumkit style UI instead of piano, most likely as a new activity.

