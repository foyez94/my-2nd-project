package com.example.my2ndproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my2ndproject.Network.Networkclient;
import com.example.my2ndproject.Network.api_interface.Apiinterface;
import com.example.my2ndproject.models.usermodel;
import com.example.my2ndproject.models.usermodelItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginactivity extends AppCompatActivity {

    SharedPreferences SharedPreferences;
    SharedPreferences.Editor editor;

    AlertDialog alertDialog;
    ProgressDialog ProgressDialog;

    boolean isSignedIn =false;

    String name;
    String email;
    String pass;
    @BindView(R.id.et_email_signin)
    EditText et_email_signin;
    @BindView(R.id.et_pass_signin)
    EditText et_pass_signin;
    @BindView(R.id.btn_signin)
    Button btn_signin;
    @BindView(R.id.tv_gotoSignup )
    TextView btn_signup;
    //ToastMgs toastMgs;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        ButterKnife.bind(this);
        prefManager = new PrefManager(this);
        if(prefManager.isLogin()){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
        ProgressDialog =  new ProgressDialog(this) ;
        ProgressDialog.setTitle("Loading");
        ProgressDialog.setCancelable(false);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email_signin.getText().toString();
                pass = et_pass_signin.getText().toString();
                if(email.isEmpty() || pass.isEmpty()){
                    Toast.makeText(loginactivity.this,"You have to give email and password",Toast.LENGTH_SHORT).show();

                }else {
                    login(email,pass);
                }

            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginactivity.this, signupactivity.class));
            }
        });


    }
    private void login(String email,String pass){
        ProgressDialog.show();
        Apiinterface service = Networkclient.getRetrfitClient().create(Apiinterface.class);
        Call<usermodel> call;
        call = service.getUser(email,pass);
        call.enqueue(new Callback<usermodel>() {
            @Override
            public void onResponse(Call<usermodel> call, Response<usermodel> response) {
                if(response.body().get(0).getStatus().equals("success")){
                    Toast.makeText(loginactivity.this,"Login success",Toast.LENGTH_SHORT).show();
                    usermodelItem item = response.body().get(0);
                    prefManager.saveUserData(item.getId(),item.getEmail(),item.getUser_name());
                    startActivity(new Intent(loginactivity.this,MainActivity.class));
                    finish();

                }else {
                    Toast.makeText(loginactivity.this,"Login failed",Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.cancel();

            }

            @Override
            public void onFailure(Call<usermodel> call, Throwable t) {
                ProgressDialog.cancel();

            }
        });
    }



}
