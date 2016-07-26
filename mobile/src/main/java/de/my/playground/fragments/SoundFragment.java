package de.my.playground.fragments;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import de.my.playground.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SoundFragment extends Fragment {

    private TextView mTextView;

    public SoundFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sound, container, false);

        mTextView = (TextView) v.findViewById(R.id.sf_txt);

        FloatingActionButton play = (FloatingActionButton) v.findViewById(R.id.sf_fab);
        play.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playNotificationSound();
                    }
                });
        return v;
    }

    private boolean sSoundPlaying = false;
    private void playNotificationSound() {
        if(sSoundPlaying) return;

        sSoundPlaying = true;

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        MediaPlayer mp = new MediaPlayer();
        mp.setVolume(1, 1);
        mp.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
        mp.setLooping(false);
        try {
            mp.setDataSource(getContext(), sound);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        mp.prepareAsync();

        final AudioManager am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        final int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        final int currentVolume = am.getStreamVolume(AudioManager.STREAM_NOTIFICATION);

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                am.setStreamVolume(AudioManager.STREAM_NOTIFICATION, maxVolume, 0);
                mp.start();
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                am.setStreamVolume(AudioManager.STREAM_NOTIFICATION, currentVolume, 0);
                sSoundPlaying = false;
            }
        });
        mTextView.append("BEEP " + currentVolume + "/" + maxVolume + System.getProperty("line.separator"));
    }
}
