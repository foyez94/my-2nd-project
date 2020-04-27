package com.example.my2ndproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my2ndproject.Network.Networkclient;
import com.example.my2ndproject.Network.api_interface.Apiinterface;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.my2ndproject.R.id.add;
import static com.example.my2ndproject.R.id.et_pass_signin;
import static com.example.my2ndproject.R.id.et_pass_signup;
import static com.example.my2ndproject.R.id.start;

public class signupactivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AlertDialog alertDialog;
    ProgressDialog progressDialog;

    String name;
    String email;
    String pass;


    @BindView(value = R.id.et_email_signup)
    EditText et_email_signup;
    @BindView(value=R.id.et_name_signup)
    EditText et_name_signup;
    @BindView(value= R.id.et_pass_signup)
    EditText et_pass_signup;
    @BindView(R.id.btn_signup)
    Button btn_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinactivity);
        ButterKnife.bind(this);
        progressDialog= new ProgressDialog(this);
      progressDialog.setTitle("Loading");
      progressDialog.setCancelable(false);
      btn_signup.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
          email=et_email_signup.getText().toString();
          name=et_name_signup.getText().toString();
           pass=et_pass_signup.getText().toString();
           if (!email.isEmpty() && !name.isEmpty() && !pass.isEmpty()){
               addUser();
           }else{

           Toast.makeText(signupactivity.this,"fill all the information",Toast.LENGTH_SHORT).show();
           }

          }});






    }

    private void addUser() {
        progressDialog.show();
        Apiinterface service = Networkclient.getRetrfitClient().create(Apiinterface.class);
        Call<String>call;
        call= service.addUser(name,email,pass);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
             progressDialog.hide();
             Log.e("res",response.body().toString());
              String model=response.body();
             if (!model.equals("same")){
                 startActivity(new Intent(signupactivity.this,MainActivity.class));
                 finish();
             }
             else{
                 Toast.makeText(signupactivity.this,"Email already used",Toast.LENGTH_SHORT).show();

             }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
           progressDialog.hide();
            }
        });

    }




}


