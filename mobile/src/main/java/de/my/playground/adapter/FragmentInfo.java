package de.my.playground.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

/**
 * Created by dep01181 on 7/8/2016.
 */
public class FragmentInfo {
    public final String name;
    public final Drawable image;
    public final Fragment frag;

    public FragmentInfo(String name, Drawable image, Fragment frag) {
        this.name = name;
        this.image = image;
        this.frag = frag;
    }
}
