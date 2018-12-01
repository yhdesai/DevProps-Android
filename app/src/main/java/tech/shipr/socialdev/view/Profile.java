package tech.shipr.socialdev.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import tech.shipr.socialdev.R;

public class Profile extends Fragment {
    private TextView pUsername;

    private String displayName;
    private String profilePic;


    private static final int RC_PHOTO_PICKER = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        FirebaseApp.initializeApp(getActivity());

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();

        StorageReference mProfileStorageReference = mFirebaseStorage.getReference().child("profile_pic");


        pUsername = rootView.findViewById(R.id.usernameTextView);
        ImageView mProfilePic = rootView.findViewById(R.id.profileImage);

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();
                Log.d("provider id", providerId);

                // UID specific to the provider
                String uid = profile.getUid();
                Log.d("uid", uid);

                // Name, email address, and profile photo Url

                displayName = profile.getDisplayName();
                Uri uri = profile.getPhotoUrl();
                /*  profilePic = uri.toString();*/


            }

        }
        pUsername.setText(displayName);


        //TODO profile pic
        mProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                Log.d("ping", "Ping");
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
                Log.d("pong", "Pong");
            }
        });


    /*    mSubmitButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(pUsername.getText().toString())
                        *//* .setPhotoUri(Uri.parse(profilePic))*//*
                        .build();

                Objects.requireNonNull(user).updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("profile", "User profile updated.");
                                }
                            }
                        });
            }
        });*/
        return rootView;
    }


}
