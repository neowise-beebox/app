package com.project.spaceapps.beebox.beebox.webservice;

import com.project.spaceapps.beebox.beebox.model.Bee;
import com.project.spaceapps.beebox.beebox.model.Task;
import com.project.spaceapps.beebox.beebox.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Matheus on 29/04/2017.
 */

public interface APIInterface {

    @FormUrlEncoded
    @POST(Constants.SAVE_BEE)
    Call<Task> saveBee(@Field("beedata") Bee bee);
}
