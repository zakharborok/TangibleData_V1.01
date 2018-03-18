package com.example.macpac.tangibledata;

import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * \class Speech
 * \brief Class to verbally represent strings.
 * We used built in library to perform this task.
 */
public class Speech
{
    public static Speech instance = new Speech(); /**< Global reference to a single instance of the class. */
    private TextToSpeech tts; /**< Object to transform text into speech. */

    /**
     * \brief Constrictor.
     * Method to create object which would transform text into speech(UK Local, pitch:1.3, Speech rate:1).
     */
    public Speech()
    {
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
                }
            }
        });
    }

    /**
     * Method to call Global to this class object tts, to verbally say out text.
     * @param str String to be verbally represented.
     */
    public void talk(String str)
    {
        if (!tts.isSpeaking())
            tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
    }

    /**
     * Method to check if app currently producing a speech.
     * @return result of the check.
     */
    public boolean isTalking()
    {
        return tts.isSpeaking();
    }

    /**
     * Method to stop current execution of the speech.
     */
    public void stopTalk()
    {
        tts.stop();
    }
}
