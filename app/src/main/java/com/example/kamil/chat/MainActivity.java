package com.example.kamil.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class MainActivity extends RoboActivity {

    @InjectView(R.id.signIn)
    private Button signInButton;
    @InjectView(R.id.enterName)
    private EditText enternameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startServiceIntent = new Intent(getBaseContext(), ConnectionService.class);
                Intent intent = new Intent(getApplicationContext(), MessagesListActivity.class);
                String username = enternameEditText.getText().toString();
                intent.putExtra("userName", username);
                startService(startServiceIntent);
                startActivity(intent);
            }
        });

    }

}
