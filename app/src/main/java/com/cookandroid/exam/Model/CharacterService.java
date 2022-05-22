package com.cookandroid.exam.Model;

import com.cookandroid.exam.Util.Character;
import com.cookandroid.exam.Util.GetCharacter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CharacterService {

    @GET("/character/{id}")
    Call<GetCharacter> getCharacter(@Path("id") int id);

    @POST("/character")
    Call<Character> addCharacter(@Body Character character);

    @PUT("/character/{id}")
    Call<Character> updateCharacter(@Path("id") int id, @Body Character character);

}
