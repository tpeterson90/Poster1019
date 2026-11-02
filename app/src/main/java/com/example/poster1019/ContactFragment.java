package com.example.poster1019;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment {
    public ContactFragment(){
        super(R.layout.fragment_contact);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.layoutEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEmailApp(getString(R.string.contact_email));
            }
        });

        //Changes the company description to personal intro when clicked
        TextView companyIntro = view.findViewById(R.id.companyIntro);
        Button personalIntro = view.findViewById(R.id.personalIntro);

        personalIntro.setOnClickListener(v -> {
            companyIntro.setText(
                    "I'm a computer science student who enjoys technology." +
                            "I am studying at Central Washington University at the Des Moines campus." +
                            "This is my final year in school!"
            );
        });
    }


            private void openEmailApp(String emailAddress) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailAddress));
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(requireContext(), R.string.no_email_app, Toast.LENGTH_SHORT).show();
                }
            }
        }