package com.example.poster1019;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * This is the fragment for the About page that extends from fragment.
 * It creates the displays the About page layout.
 */
public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState){

        return inflater.inflate(
                R.layout.fragment_about,
                container,
                false
        );
    }

}
