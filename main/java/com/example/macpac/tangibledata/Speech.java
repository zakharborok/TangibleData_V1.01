package com.example.macpac.tangibledata;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by cabeywickra on 05/02/2018.
 */

public class Speech
{
    public static Speech instance = new Speech();
    private TextToSpeech tts;
    private CharSequence SC_str;
    private String S_str;
    private long timeKeeper;

    public Speech()
    {
        timeKeeper = System.currentTimeMillis();
    }

    public void Talk(Context context, String str)
    {
        if (System.currentTimeMillis() - timeKeeper > 1500)
        {
            timeKeeper = System.currentTimeMillis();

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
}
