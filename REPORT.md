# Report

## Intro

Chipkey is a playable instrument application with a retro aesthetic that is part sampler, part synthesizer. Rather than generating a tone using an oscillator, the application uses an NES-style square wave sample set for its sounds. 

The inclusion of a programmable mod wheel and aftertouch, backed by a low frequency oscillator and an ADSR volume envelope allows for a surprising amount of versatility in tailoring the sound to the users' tastes. The program allows for fairly complex combinations of different parameters to shape the sound, and is designed specifically to be comfortably playable even on smaller phone screens.

## Technical Design


### Basic functionality 
At its core, the application uses the soundPool class to load 35 distinct audio samples. The samples are specifically trimmed in such a way as to be infinitely loopable, which means that their actual length can be kept fairly short (typically less than a second), which grants a lot of headroom in terms of memory usage. The soundPool object itself is created inside a soundBank class, which handles all the loading and unloading of sounds.

In order to trigger the appropriate samples, each of them is associated with a particular button on screen. Rather than using an onClickListener (which would only trigger the samples *after* clicking and releasing a button), each button has an onTouchListener that performs different actions depending on the type of touch the user performs. 

At a basic level, this onTouchListener causes the application to start playing a sample on ACTION_DOWN (i.e. when a button is pressed), and stop playing it on ACTION_UP (i.e. when the button is released). This is essentially all there is to say about the basic functionality of the program.

### Aftertouch

The first expansion to this is the ACTION_MOVE branch of the onTouchListener. In this branch any motions the user makes while a key is depressed are registered and used to trigger the aftertouch functionality. The aftertouch functions detect the y coordinate at which the user is touching a particular key, and, if active, use it to control parameters of the sound while that sound is playing. 

Thus, if the aftertouch is set to "Volume", the user can control the volume level of the sound by moving their finger up or down along the key. Aftertouch can currently be set to control a notes' volume level, its pitch, or the vibrato intensity (more on that below).

### Mod Wheel

A mod wheel is a controller on many keyboards and synthesizers that, similarly to aftertouch, can be assigned to control a multitude of sound parameters. In the case of this application it's represented by a **seekBar** object that uses the OnProgressChanged method to update the selected parameter value every time its position is manually altered. This effectively means that, similarly to aftertouch, it's possible to change sound parameters *while* the sound is playing. The mod wheel can be set to control a sounds' Attack, Decay, Release or vibrato rate.

The Mod Wheels values are converted using a logarithmic scale, which allows the user to perform fine manipulation at lower values, and much broader changes at higher values.

### ADSR Amp Envelope

<img src="https://github.com/Jorendb9/piano-app/blob/master/docs/ADSR.jpg?raw=true width="220" height="400" />

#### Overview & Explanation
Rather than going straight to discussing implementation of this functionality, it's probably necessary to first give a global overview of what it actually *does*. Amp Envelopes are, like the mod wheel and aftertouch, somewhat standardized functions on many a synthesizer. They allow the user to intelligently control the volume level of each individual note in a semi-automated fashion and impart a lot of versatility to the sound with a fairly low level of controller complexity (using only four values). 

More specifically, when a sound is triggered, it goes through four distinct 'phases': Attack, Decay, Sustain and Release. **Attack** signifies the length of time it takes for the sound to go from silent to its desired volume level after a key is pressed and held down. A longer attack time will cause the sound to 'swell' or fade in, while a low or non-existent attack time will cause it to (almost) instantly go to its desired volume level.

After reaching full volume, the note enters the **Decay** phase. Decay dictates the amount of time it takes for a note to reach the volume level set by the **Sustain** parameter. No Sustain and a low Decay time means short, snappy sounds that almost immediately reach volume level 0, while a maxed out Sustain parameter effectively negates the Decay, and causes the sound to carry on looping infinitely (or for as long as the key is pressed).

The final phase, **Release**, occurs only after the user releases their touch. The Release parameter dictates for how long the sound will play on after the key has been released.

#### Implementation

The complex interplay of different volume levels and other variables required the creation a **Note** class. Each Note object contains, among other things, its unique audio sample, a string name to identify it, the Button object that triggers it, and the aforementioned ADSR values. 

Additionally, to properly implement Attack, Decay and Release functionality, it has to be possible to gradually increase or reduce the volume level of a note over a given amount of time. In order to achieve this, the application makes use of a modified **CountDownTimer** class. This CountDownTimer takes as its arguments the total amount of time that it has to run for, as well as the time it takes in between each 'tick'. 

In the case of the Note class, it takes the A, D or R value as the total amount of time, and A, D or R/100 as the time between ticks. Additionally, on each tick, the timer is made to increase or decrease the Notes' volume level by a certain amount. 

This amount is determined, during the Attack phase for example, by taking the desired volume level/100. Provided that the note starts on volume level 0, it will then increase in volume on each tick, until the timer runs out, and the note will be at the desired volume level. Although strictly speaking this isn't a linear increase (the Note constantly spends a small amount of time at the same volume level before changing at the next tick), the increments are small enough to not be noticeable.

The ADSR values themselves can either be mapped to the Mod Wheel or controlled through four distinct seekBars in the FX Activity screen. 

### Low Frequency Oscillator

#### Overview & Explanation

An LFO is another synthesizer staple. It generates a signal (or in this applications' case a series of values) similar to a rhythmic pulse or sweep, and is often used to modulate a synthesizers' parameters in a controlled, automated fashion. In the case of this application the LFO is effectively the backend of the Vibrato functionality. Vibrato is a musical effect where a sounds' pitch is rapidly modulated around its 'central' pitch, and is typically considered aesthetically pleasing (though in the case of this application mileage may vary).

This apps' LFO simulates a triangular wave by generating values that go up and down between two edge values, and can be set to control the sounds' pitch.

#### Implementation

Similarly to the amp envelope, the LFO functionality requires gradual modulation of a parameter over a given amount of time, so it utilizes a different version of the CountDownTimer class. When the LFO is activated, it starts generating a descending amount of values until it reaches its lower bound, at which point the timer object recursively creates a new instance of itself that generates values that go in the 'opposite' direction, until it reaches its upper bound, etc.

The upper and lower bound of the generated wave, as well as the waves' frequency can be user-controlled as well. This is done through the Mod Wheels Vibrato Rate setting and the Aftertouch Vibrato Intensity. 

Rather than making a seperate LFO object for each note that is playing (which would not only be performance-intensive, but also cause an undesirable effect with each note vibrating out of sync), the application creates a single LFO instance on startup, which only starts generating values if vibrato is turned on and at least one Note is playing. 

In order to properly handle multiple notes playing at once, when a Note is triggered and starts playing, it's 'fed' into the LFO as an argument and added to an ArrayList of Notes. The LFO then manipulates the pitch of every Note currently inside the ArrayList. If a Note stops playing it's removed from the list, and if the list is empty (therefore no sound is playing at the moment), the LFO stops its timers as well. In order to prevent multiple timers from being triggered at once, the LFO only starts its timers when the first Note is added to the ArrayList.

### Main Activity Layout

The Main Activity contains the 35 piano keys arranged in the typical black/white pattern and contained inside a scrollview with a scrollbar at the bottom. Additionally, it has the Mod Wheel seekBar, as well as a button that starts the FX Activity, and a Stop button that stops all sounds that are currently playing. The application forces the device into Landscape mode.

### FX Activity Layout

FX Activity contains a ToggleButton that switches vibrato on/off, a back button that returns the user to Main Activity (the actual back button on the device does the same), two sliders that allow the user to set the functionality of the mod wheel and aftertouch, and four seekBars that control ADSR. 

### Combining Controllers

There are multiple ways to control a single sound parameter such as volume or pitch, which inevitably leads to clashes. For example, having Aftertouch set to control Volume, while also using a significant Attack time causes the application to try and gradually fade in a note but simultaneously try to set the volume according to y coordinate that the user is touching. This leads to strange, uncontrolled jumps in volume. Although this is a logical effect of combining the two controllers  (thus not necessarily a bug), it nonetheless doesn't sound very aesthetically pleasing. 

For this reason, certain combinations of controller settings are disabled.



## Challenges

A lot of the features and design decisions in the final product differ from what I had anticipated based mostly on my inexperience with the Android Studio IDE. 

For example, I had initially planned to display a rigid amount of piano keys on screen and have two buttons that would correspond to lower or higher parts of the keyboard. On pressing a button, the current set of samples would unload and the higher or lower set would be loaded instead. In retrospect, this would have been very slow and clunky, and the current solution (a scrollview of piano keys that can be navigated with a fling) is, in my opinion, far more elegant and natural as a design solution. 

The only drawback is that a larger amount of audio samples has to be loaded into memory at once, since they all have to be accessible, but using small, loopable samples made this a non-issue. 

Actually implementing the scrollview did pose a bit of a challenge as it would often interrupt the touch commands that were intended for the keys/buttons. What would happen was that you could perform the ACTION_DOWN, i.e., depressing a key without a problem, but if you tried to impart aftertouch by moving up and down, and accidentally moved laterally instead, the scrollview would interpret this as a fling or swipe, and move itself. 

This in and of itself would not be a problem, but the scrollview would *then* intercept the ACTION_UP as well. Since ACTION_UP was the trigger to stop playing the sound, it would then hang infinitely. The user would have to shut the app down in order for the sound to stop.

I solved this by implementing a custom Scrollview class and override its OnInterceptTouchEvent to prevent it from ever interfering with the key presses. The drawback from this solution was that it made it less obvious that the keyboard was horizontally scrollable, so I tried to remedy this by adding a scrollbar at the bottom that is visible at all times.

A fairly fortunate discovery was aftertouch functionality. On real keyboards and synthesizers this function is actually somewhat rare (usually limited to higher end instruments), so it hadn't immediately occurred to me that its implementation in this application would be relatively simple. The benefits of using aftertouch over a different type of controller are especially obvious for a mobile environment: it has essentially no cost in terms of screenspace real estate, as its performed on the piano keys themselves, but still allows for intricate manipulation of a sounds parameters.

In general, I had planned on placing all the parameter controllers on the second FX activity screen, (since there was already little room on the main screen). Ultimately, however, it seemed to me that giving users the ability to manipulate sound on the fly, even while a sound was already playing, was too beneficial to pass up. For this reason, the application now has the two programmable controllers on the main screen, while the FX screen allows for more finetuning. 

Another matter I somewhat struggled with was generating a series of increasing/decreasing values over a certain amount of time to create a fade-in/fade-out effect. Since each pass through the loop had to have a small delay, and Android Studio frowns upon using something like Sleep on the main UI thread, I experimented with all kinds of Handlers and sleeping on seperate threads until I finally settled on using the CountDownTimer class. 

Although I can't say for sure (there was no noticeable difference in performance on the test device), I think the drawback is that creating CountDownTimer objects has a larger memory footprint than other solutions for this problem. I feel like using CountDownTimer was nonetheless the most 'organic' way of going about solving this, since the inherent functionality of the class (i.e. it runs for a certain amount of time, and can update a value on every tick during its runtime) seemed like a really good fit for what I needed it to do.
