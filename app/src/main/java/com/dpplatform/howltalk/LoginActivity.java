package com.dpplatform.howltalk;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class LoginActivity extends AppCompatActivity {

    private EditText id;
    private EditText password;

    private Button login;
    private Button signup;
    private FirebaseRemoteConfig FirebaseRemoteConfig;
    private FirebaseAuth firebaseAuth; //로그인 관리
    private FirebaseAuth.AuthStateListener authStateListener; //로그인이 되었는지 확인해줌 (로그인이 되었으면 다음 화면으로 넘어가게 됨)

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        String splash_background = FirebaseRemoteConfig.getString("splash_background");
        getWindow().setStatusBarColor(Color.parseColor(splash_background));

        id = findViewById(R.id.loginActivity_edit_id);
        password = findViewById(R.id.loginActivity_edit_password);

        login = findViewById(R.id.loginActivity_button_login);
        signup = findViewById(R.id.loginActivity_button_signup);
        login.setBackgroundColor(Color.parseColor(splash_background));
        signup.setBackgroundColor(Color.parseColor(splash_background));

        login.setOnClickListener(new View.OnClickListener() { //로그인 버튼을 눌렀을때,
            @Override
            public void onClick(View v) {
                loginEvent();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this , SignupActivity.class));
            }
        });

    }
    //로그인이 정상적으로 되었는지 확인해주는 메소드,
    void loginEvent() {
        firebaseAuth.signInWithEmailAndPassword(id.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //로그인 실패했을때만 작동
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this , task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}