package com.example.dandane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dandane.R;
import com.example.dandane.network.HTTPRequest;
import com.example.dandane.network.NetworkHTTP;
import com.example.dandane.network.NetworkListner;
import com.example.dandane.network.NetworkSingleton;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Response;

public class SignInActivity extends AppCompatActivity implements NetworkListner {
    EditText idEdit, pwEdit;
    Button signInButton;
    TextView signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        NetworkHTTP.init(getApplicationContext());
        NetworkSingleton.getInstance().networkListner.add(this);
        idEdit = findViewById(R.id.idEdit);
        pwEdit = findViewById(R.id.pwEdit);
        signInButton = findViewById(R.id.signIn);
        signUpTextView = findViewById(R.id.signUp);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idEdit.getText().toString();
                String pw = pwEdit.getText().toString();
                if (id.matches("")) {
                    Toast.makeText(SignInActivity.this, "아이디를 작성해주세요", Toast.LENGTH_SHORT).show();
                }
                else if (pw.matches("")) {
                    Toast.makeText(SignInActivity.this, "패스워드를 작성해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    NetworkHTTP.requestPOSTSignIn(id, pw);
                }
            }
        });
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void responseListner(Response response, HTTPRequest httpRequest) throws IOException {
        if (httpRequest.equals(HTTPRequest.GETStatus)) {
            Log.e("Response Body", response.body().string());
        }
        else if (httpRequest.equals(HTTPRequest.POSTSignIn)) {
            JsonObject jsonObject = (JsonObject)new JsonParser().parse(response.body().string());
            String id = jsonObject.get("id").toString().replace("\"", "");
            if (!id.equals("null")) {
                Intent intent = new Intent(getApplicationContext(), VocaListActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
            else {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "입력하신 아이디와 비밀번호가 등록된 정보와 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                }, 0);
            }
        }
    }
}
