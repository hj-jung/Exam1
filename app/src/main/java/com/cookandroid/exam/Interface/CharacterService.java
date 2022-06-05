package com.cookandroid.exam.Interface;

import com.cookandroid.exam.DTO.Character;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CharacterService {

    @GET("/character/{id}")
    Call<Character> getCharacter(@Path("id") int id);

    @POST("/character")
    Call<Character> addCharacter(@Body Character character);

    @PUT("/character/{id}")
    Call<Character> updateCharacter(@Path("id") int id, @Body Character character);

}
