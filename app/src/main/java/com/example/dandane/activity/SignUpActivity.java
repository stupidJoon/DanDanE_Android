package com.example.dandane.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dandane.R;
import com.example.dandane.network.HTTPRequest;
import com.example.dandane.network.NetworkHTTP;
import com.example.dandane.network.NetworkListner;
import com.example.dandane.network.NetworkSingleton;

import java.io.IOException;

import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity implements NetworkListner {
    EditText idEdit, pwEdit;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        NetworkSingleton.getInstance().networkListner.add(this);
        idEdit = findViewById(R.id.signUpIdEdit);
        pwEdit = findViewById(R.id.signUpPwEdit);
        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idEdit.getText().toString();
                String pw = pwEdit.getText().toString();
                if (id.matches("")) {
                    Toast.makeText(SignUpActivity.this, "아이디를 작성해주세요", Toast.LENGTH_SHORT).show();
                }
                else if (pw.matches("")) {
                    Toast.makeText(SignUpActivity.this, "패스워드를 작성해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    NetworkHTTP.requestPOSTSignUp(id, pw);
                }
            }
        });
    }
    @Override
    public void responseListner(Response response, HTTPRequest httpRequest) throws IOException {
        if (httpRequest.equals(HTTPRequest.POSTSignUp)) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }, 0);
        }
    }
}
