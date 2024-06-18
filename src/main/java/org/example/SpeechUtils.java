package org.example;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class SpeechUtils {
    private static final String VOICE_NAME = "kevin16";
    private Voice voice;

    public SpeechUtils() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(VOICE_NAME);
        if (voice == null) {
            System.err.println("Voice not found: " + VOICE_NAME);
            System.exit(1);
        }
        voice.allocate();
        voice.setRate(100);
    }

    public void speak(String text) {
        voice.speak(text);
    }

    public void deallocate() {
        voice.deallocate();
    }
}
