package com.iset.P_kids.Retro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitInterface
{
    //get users
    @GET("api/Users1/{id}")
    public Call <User> getUserById(@Path("id") int id);


    //delete user by id
    @DELETE("api/Users1/{id}")
    public Call <User> deleteUserById(@Path("id") int id);

@GET("api/Users1")
public Call<List<User>>getAllUsers();

    @FormUrlEncoded
    @POST("api/Users1")
    public Call <User> addUser(@Field("Nom") String nom,
                               @Field("Prénom") String prénom,
                               @Field("Email") String email);


    @POST("api/Users1")
    public Call <User> addUser(@Body User user);
    @FormUrlEncoded
    @PUT("api/Users1/{id}")
    public Call <User> updateUser(@Path("id") int id,
                                  @Field("Id") int idu,
                                  @Field("Email") String email,
                                  @Field("FName") String nom,
                                  @Field("LName") String prénom);


}
