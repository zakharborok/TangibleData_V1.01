package com.example.macpac.tangibledata;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;

/**
 * Created by MacPac on 23/02/2018.
 */

public class mToneGenerator
{
    private final int sampleRate = 8000;
    private Handler handler = new Handler();
    private byte[] globalSnd;

    public void generateAndPlayTone(final float duration, final int freqOfTone/*256-2048*/)
    {
        final Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                genTone(duration, freqOfTone);
                handler.post(new Runnable()
                {
                    public void run()
                    {
                        playSound();
                    }
                });
            }
        });
        thread.start();
    }

    private void genTone(float duration, double freqOfTone/*440*/)
    {
        int numSamples = (int) (duration * (float) sampleRate);
        double sample[] = new double[numSamples];
        byte generatedSnd[] = new byte[2 * numSamples];
        globalSnd = generatedSnd;

        // fill out the array
        for (int i = 0; i < numSamples; ++i)
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / freqOfTone));

        // convert to 16 bit pcm sound array
        int idx = 0;
        for (final double dVal : sample)
        {
            // scale to quater of maximum amplitude
            final short val = (short) ((dVal * (32767 / 4)));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
    }

    private void playSound()
    {
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, globalSnd.length,
                AudioTrack.MODE_STATIC);
        audioTrack.write(globalSnd, 0, globalSnd.length);
        audioTrack.play();
    }
}
