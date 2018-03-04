package com.example.macpac.tangibledata;

import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by cabeywickra on 05/02/2018.
 */

public class Speech
{
    public static Speech instance = new Speech();
    private TextToSpeech tts;

    public Speech()
    {
        tts = new TextToSpeech(TangibleData.getContext(), new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status)
            {
                if (status != TextToSpeech.ERROR)
                {
<<<<<<< HEAD
                    tts.setLanguage(Locale.UK);
=======
                    tts.setLanguage(Locale.ENGLISH);
>>>>>>> 4de5c96aaa492f0e035238e78032926191e8fdcc
                    tts.setPitch(1.3f);
                    tts.setSpeechRate(1f);
                }
            }
        });
    }

    public void talk(String str)
    {
        if (!tts.isSpeaking())
            tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
    }

    public boolean isTalking()
    {
        return tts.isSpeaking();
    }
}
