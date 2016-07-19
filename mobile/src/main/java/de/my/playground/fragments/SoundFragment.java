package de.my.playground.fragments;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sound, container, false);

        mTextView = (TextView)v.findViewById(R.id.sf_txt);

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

    private void playNotificationSound() {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer player = MediaPlayer.create(getContext(), notification);
        player.setVolume(1.0f, 1.0f);
        player.start();
    }
}
