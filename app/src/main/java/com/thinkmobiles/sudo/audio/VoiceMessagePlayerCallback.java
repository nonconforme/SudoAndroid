package com.thinkmobiles.sudo.audio;

import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by omar on 09.06.15.
 */
public class VoiceMessagePlayerCallback{
    private SeekBar mSeekBar;
    private TextView tvCounter;

    public VoiceMessagePlayerCallback (SeekBar mSeekBar, TextView tvCounter){
        this.tvCounter = tvCounter;
        this.mSeekBar = mSeekBar;
    }

}
