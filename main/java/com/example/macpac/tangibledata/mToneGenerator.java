package com.example.macpac.tangibledata;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.Looper;

import static java.lang.Thread.sleep;


public class mToneGenerator
{

    private final int sampleRate = 8000;
    private int numSamples;
    private double sample[];
    private byte generatedSnd[];
    private AudioTrack audioTrack;
    Handler handler = new Handler(Looper.getMainLooper());

    public Thread generateAndPlayTone(final int freqOfTone, float durationInMilliSec)
    {
        numSamples = (int) (durationInMilliSec * sampleRate) / 1000;
        sample = new double[numSamples];
        generatedSnd = new byte[2 * numSamples];

        final Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                genTone(freqOfTone);
                handler.post(new Runnable()
                {
                    public void run()
                    {
                            playSound();
                        try
                        {
                            sleep(1000);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        releaseSound();
                    }
                });
            }
        });

        return thread;
    }


    private void genTone(double freqOfTone)
    {
        // fill out the array
        for (int i = 0; i < numSamples; ++i)
        {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / freqOfTone));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample)
        {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
    }

    private void playSound() {
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
                AudioTrack.MODE_STATIC);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();
    }
    public void releaseSound(){
        audioTrack.release();
    }
}
