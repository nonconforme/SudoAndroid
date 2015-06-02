package com.thinkmobiles.sudo.audio;

import com.thinkmobiles.sudo.global.Constants;

/**
 * Created by omar on 02.06.15.
 */
public class VoiceRecorder {
    static {
        System.loadLibrary(Constants.MP3_LAME);
    }

}
