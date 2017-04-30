package com.project.spaceapps.beebox.beebox.webservice;

import com.project.spaceapps.beebox.beebox.model.Bee;
import com.project.spaceapps.beebox.beebox.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Matheus on 29/04/2017.
 */

public interface APIInterface {

    @POST("/tasks")
    Call<Bee>  saveBee(@Body Bee bee);


}
