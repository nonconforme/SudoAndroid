package com.thinkmobiles.sudo.audio;

import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.LocalSocketAddress;
import android.util.Log;
import com.thinkmobiles.sudo.global.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by omar on 02.06.15.
 */
public class VoiceRecorder {
	private final int mSampleRate = 16000;
	private final int minBufferSize = AudioRecord.getMinBufferSize(mSampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
	private AudioRecord recorder;
	private String audioPath;
	boolean recording;
	private FileOutputStream output;

	static {
		System.loadLibrary(Constants.MP3_LAME);
	}

	public VoiceRecorder(Context context) {
		recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, mSampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, minBufferSize * 2);
		audioPath = context.getFilesDir() + "q";
		recordStart();
	}

	public void recThread() {

		new Thread() {
			@Override
			public void run() {

				android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);

				File outFile = new File(audioPath);
				if (outFile.exists()) {
					outFile.delete();
				}
				try {
					outFile.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, mSampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, minBufferSize * 4);
				short[] buffer = new short[mSampleRate * (16 / 8) * 1 * 5]; // SampleRate[Hz]
				byte[] mp3buffer = new byte[(int) (7200 + buffer.length * 2 * 1.25)];
				output = null;
				try {
					output = new FileOutputStream(new File(audioPath));
				} catch (FileNotFoundException e) {
					Log.e("audio recorder ===>>", "file not found");

				}
				SimpleLame.init(mSampleRate, 1, mSampleRate, 192);
				recorder.startRecording();
				int readSize = 0;
				while (recording) {
					Log.d("recorder", "recording");
					readSize = recorder.read(buffer, 0, minBufferSize);
					int encResult = SimpleLame.encode(buffer, buffer, readSize, mp3buffer);
					try {
						output.write(mp3buffer, 0, encResult);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				int flushResult = SimpleLame.flush(mp3buffer);
				if (flushResult != 0) {
					try {
						output.write(mp3buffer, 0, flushResult);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				recorder.stop();
				recorder.release();
				SimpleLame.close();


			}
		}.start();
	}

	public void recordStart() {
		recording = true;
		recThread();

	}
}