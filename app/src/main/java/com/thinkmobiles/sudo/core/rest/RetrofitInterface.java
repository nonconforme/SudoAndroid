package com.thinkmobiles.sudo.core.rest;



import com.thinkmobiles.sudo.core.APIConstants;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.LoginResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Sasha on 19.11.2014.
 */
public interface RetrofitInterface {

//    @FormUrlEncoded
//    @POST(APIConstants.API_BAY_NUMBER)
//    void bayNumber(@Field(Constants.PHONE_NUMBER) String number,
//                   Callback<BayNumberResponse> callback);
    @FormUrlEncoded
    @POST(APIConstants.URL_SIGN_IN)
    void signIn(@Field(Constants.EMAIL) String email, @Field(Constants.PASSWORD) String password,
                Callback<LoginResponse> callback);
    @FormUrlEncoded
    @POST(APIConstants.URL_SIGN_UP)
    void signUp(@Field(Constants.EMAIL) String email, @Field(Constants.PASSWORD) String password,
                Callback<LoginResponse> callback);

//    @FormUrlEncoded
//    @POST(APIConstants.URL_MESSAGE+APIConstants.URL_SEND)
//    void sendMessage(@Field(Constants.FROM_NUMBER) String from, @Field(Constants.TO_NUMBER) String to,
//                     @Field(Constants.TEXT_MESSAGE) String text, @Field(Constants.TYPE_MESSAGE) String type,
//                     Callback<DefaultResponseModel> callback);
//
    @GET(APIConstants.ACTION_IS_AUTHENTICATED)
    void isAuthenticated(Callback<DefaultResponseModel> callback);
//
//    @GET(APIConstants.URL_MESSAGE + APIConstants.API_LAST_CONVERSATION)
//    void lastConversations(Callback<ConversationResponse> callback);
//
//    @GET(APIConstants.URL_MESSAGE + APIConstants.API_CONVERSATION + "/{owner}"+"/{companion}"  )
//    void lastHistory(@Path("owner") String owner, @Path("companion") String companion, Callback<ConversationResponse> callback);
//
//    @GET(APIConstants.URL_USER + "/{" + Constants.PATH_PARAM_ID + "}")
//    void getProfile(@Path(Constants.PATH_PARAM_ID) String id, Callback<ProfileResponse> callback);
//
//    @FormUrlEncoded
//    @PUT(APIConstants.URL_USER + "/{" + Constants.PATH_PARAM_USER_ID + "}")
//    void updateProfile(@Path(Constants.PATH_PARAM_USER_ID) String uId, @Field(Constants.USER) UserModel password,
//                       Callback<LoginResponse> callback);
//
//    @PUT(APIConstants.URL_USER + "/{" + Constants.PATH_PARAM_ID + "}")
//    void addToAddressBook(@Body AddressBookRequest addressBook, @Path(Constants.PATH_PARAM_ID) String id,
//                          Callback<DefaultResponseModel> callback);
}
