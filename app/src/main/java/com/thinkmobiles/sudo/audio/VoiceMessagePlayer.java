package com.thinkmobiles.sudo.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.SeekBar;

import java.io.IOException;

/**
 * Created by omar on 09.06.15.
 * Singleton
 */
public class VoiceMessagePlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, SeekBar.OnSeekBarChangeListener {


    private boolean playing;
    private String voiceURL;
    private int mediaPos;
    private int mediaMax;

    private TelephonyManager telephoneManager;
    private PhoneStateListener phoneStateListener;
    private static VoiceMessagePlayer sVoiceMessagePlayer;
    private MediaPlayer mediaPlayer;
    private boolean isPausedCall;
    private boolean paused;
    private VoiceMessagePlayerCallback mVoiceMessagePlayerCallback;
    private Context context;
    private boolean tracking = false;
    private final Handler handler = new Handler();


    private VoiceMessagePlayer(String voiceURL, Context context, VoiceMessagePlayerCallback mVoiceMessagePlayerCallback) {
        this.voiceURL = voiceURL;
        playing = false;
        isPausedCall = false;
        this.context = context;
        this.mVoiceMessagePlayerCallback = mVoiceMessagePlayerCallback;
        setupMediaPlayer(voiceURL);
        setupSeekBarListener(mVoiceMessagePlayerCallback);
    }

    private void setupSeekBarListener(VoiceMessagePlayerCallback mVoiceMessagePlayerCallback) {
        mVoiceMessagePlayerCallback.getSeekBar().setOnSeekBarChangeListener(this);
    }

    private void setupMediaPlayer(String voiceURL) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(voiceURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        setupHandler();
    }


    private void setupTelephonyListener() {
        telephoneManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new PhoneStateListener() {

            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {

                    case TelephonyManager.CALL_STATE_OFFHOOK:

                    case TelephonyManager.CALL_STATE_RINGING:
                        if (mediaPlayer != null) {
                            pauseMedia();
                            isPausedCall = true;
                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        if (mediaPlayer != null) {

                            if (isPausedCall) {
                                isPausedCall = false;
                                playMedia();
                            }
                        }
                        break;
                }

                super.onCallStateChanged(state, incomingNumber);
            }

        };
        telephoneManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    public static VoiceMessagePlayer getInstance(Context context, String voiceURL, VoiceMessagePlayerCallback mVoiceMessagePlayerCallback) {


        if (sVoiceMessagePlayer == null)
            sVoiceMessagePlayer = new VoiceMessagePlayer(voiceURL, context, mVoiceMessagePlayerCallback);
        else {
            sVoiceMessagePlayer.stopMedia();
            sVoiceMessagePlayer = new VoiceMessagePlayer(voiceURL, context, mVoiceMessagePlayerCallback);
        }
        return sVoiceMessagePlayer;
    }

    private void stopTelephonyListener() {
        if (phoneStateListener != null) {
            telephoneManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
    }

    public void stopMedia() {
        mVoiceMessagePlayerCallback.getSeekBar().setProgress(0);
        mVoiceMessagePlayerCallback.getTvAudioRemaining().setText("");
        mVoiceMessagePlayerCallback.getTvAudioCurrnet().setText("");

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            stopTelephonyListener();
            mediaPlayer.release();
            mediaPlayer = null;

        }
    }

    public void playMedia() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            paused = false;
            setupTelephonyListener();
            mediaPlayer.start();
        }
    }

    protected void pauseMedia() {
        if (mediaPlayer.isPlaying()) {
            paused = true;
            mediaPlayer.pause();
        }

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

        stopMedia();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        playMedia();
    }


    private void setupHandler() {
        handler.removeCallbacks(sendUpdatesToUi);
        handler.postDelayed(sendUpdatesToUi, 100);
    }

    private Runnable sendUpdatesToUi = new Runnable() {
        public void run() {
            LogMediaPosition();
            handler.postDelayed(this, 100);
        }

        private void LogMediaPosition() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPos = mediaPlayer.getCurrentPosition();
                mediaMax = mediaPlayer.getDuration();
                mVoiceMessagePlayerCallback.getSeekBar().setMax(mediaMax);
                mVoiceMessagePlayerCallback.getSeekBar().setProgress(mediaPos);
                mVoiceMessagePlayerCallback.getTvAudioRemaining().setText(String.valueOf(mediaMax));
                mVoiceMessagePlayerCallback.getTvAudioCurrnet().setText(String.valueOf(mediaPos));
            }
        }
    };

    protected void updateSeekPos(int _seekPos) {

        int seekPos = _seekPos;
        if (mediaPlayer.isPlaying()) {
            handler.removeCallbacks(sendUpdatesToUi);
            mediaPlayer.seekTo(seekPos);
            setupHandler();
        }

    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {
        if (!mediaPlayer.isPlaying()) {
            playMedia();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (tracking) updateSeekPos(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        tracking = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        tracking = false;
    }
}
