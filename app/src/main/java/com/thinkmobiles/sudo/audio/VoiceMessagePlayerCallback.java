package com.thinkmobiles.sudo.audio;

import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by omar on 09.06.15.
 */
public class VoiceMessagePlayerCallback{
    private SeekBar mSeekBar;
    private TextView tvAudioCurrnet;
    private TextView  tvAudioRemaining;


    public SeekBar getSeekBar() {
        return mSeekBar;
    }



    public TextView getTvAudioCurrnet() {
        return tvAudioCurrnet;
    }

    public TextView getTvAudioRemaining() {
        return tvAudioRemaining;
    }

    public VoiceMessagePlayerCallback (SeekBar mSeekBar, TextView tvAudioCurrnet,TextView  tvAudioRemaining ){
        this.tvAudioCurrnet = tvAudioCurrnet;
        this.tvAudioRemaining = tvAudioRemaining;
        this.mSeekBar = mSeekBar;
    }

}
