package com.example.my2ndproject.Network.api_interface;




import com.example.my2ndproject.models.usermodel;

import javax.xml.namespace.QName;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface Apiinterface{


    @FormUrlEncoded
    @POST("login.php")
    Call<usermodel> getUser(@Field("post_email") String email, @Field("post_pass") String pass);

    @FormUrlEncoded
    @POST("add_user.php")
    Call<String> addUser(@Field("name") String name, @Field("email") String email, @Field("pass") String pass);
}
