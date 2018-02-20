package com.example.macpac.tangibledata;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by cabeywickra on 05/02/2018.
 */

public class Speech
{
    private static TextToSpeech tts;
    private static CharSequence SC_str;
    private static String S_str;

    public static void Talk(Context context, String str)
    {
        S_str = str;
        tts = new TextToSpeech(TangibleData.getContext(), new TextToSpeech.OnInitListener()
        {

            @Override
            public void onInit(int status)
            {
                if (status != TextToSpeech.ERROR)
                {
                    tts.setLanguage(Locale.UK);
                    tts.setPitch(1.3f);
                    tts.setSpeechRate(1f);
                    //   tts.speak(SC_str, TextToSpeech.QUEUE_FLUSH, null,null);
                    tts.speak(S_str, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }
}
