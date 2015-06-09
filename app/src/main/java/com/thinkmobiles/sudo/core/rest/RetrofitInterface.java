package com.thinkmobiles.sudo.core.rest;


import com.thinkmobiles.sudo.core.APIConstants;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.models.*;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.models.chat.LastChatsModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;
import com.thinkmobiles.sudo.models.counties.CountryModel;
import com.thinkmobiles.sudo.models.numbers.BuyNumberResponce;
import com.thinkmobiles.sudo.models.numbers.NumberListResponse;
import com.thinkmobiles.sudo.utils.TypedJsonString;
import retrofit.Callback;
import retrofit.http.*;
import retrofit.mime.TypedFile;

import java.util.List;

/**
 * Created by Sasha on 19.11.2014.
 */
public interface RetrofitInterface {

    @FormUrlEncoded
    @POST(APIConstants.URL_SIGN_IN)
    void signIn(@Field(Constants.EMAIL) String email, @Field(Constants.PASSWORD) String password, Callback<LoginResponse> callback);

    @FormUrlEncoded
    @POST(APIConstants.URL_SIGN_UP)
    void signUp(@Field(Constants.EMAIL) String email, @Field(Constants.PASSWORD) String password, Callback<LoginResponse> callback);

    @FormUrlEncoded
    @POST(APIConstants.API_BAY_NUMBER)
    void buyCredits(@Field(Constants.RECEIPT) String receipt, @Field(Constants.PROVIDER) String provider, Callback<BuyNumberResponce> callback);

    @POST(APIConstants.URL_ADDRESSBOOK)
    void addContact(@Body TypedJsonString userModel, Callback<DefaultResponseModel> callback);

    @FormUrlEncoded
    @POST(APIConstants.URL_USER + APIConstants.URL_CHANGE_PASS)
    void changePassword(@Field(Constants.NEW_PASSWORD) String newPassword, @Field(Constants.CONFIRM_PASSWORD) String confirmPass, @Field(Constants.PASSWORD) String password, Callback<DefaultResponseModel> callback);


    @FormUrlEncoded
    @POST(APIConstants.URL_NUMBER + APIConstants.API_BAY_NUMBER)
    void buyNumber(@Field(Constants.PARAM_NUMBER) String number, @Field(Constants.COUNTRY_CODE) String countryIso, @Field(Constants.PARAM_PACKAGE_NAME) String packageName, Callback<BuyNumberResponce> callback);
    //    @FormUrlEncoded

    @PUT(APIConstants.URL_ADDRESSBOOK + "/{" + Constants.PATH_PARAM_ID + "}")
    void updateContact(@Body TypedJsonString userModel, @Path(Constants.PATH_PARAM_ID) String id, Callback<UpdateProfileModel> callback);

    @GET(APIConstants.URL_ADDRESSBOOK)
    void getContacts(Callback<List<UserModel>> callback);

    @FormUrlEncoded
    @POST(APIConstants.URL_MESSAGE + APIConstants.API_SEND)
    void senMessage(@Field(Constants.FROM_NUMBER) String src, @Field(Constants.TO_NUMBER) String dst, @Field(Constants.TEXT_MESSAGE) String text, @Field(Constants.TYPE_MESSAGE) String type, Callback<DefaultResponseModel> callback);


    @GET(APIConstants.URL_PRICE + APIConstants.API_COUNTRIES)
    void getCountries(Callback<List<CountryModel>> callback);

    @GET(APIConstants.ACTION_IS_AUTHENTICATED)
    void isAuthenticated(Callback<AuthenticatedModel> callback);


    @GET(APIConstants.URL_USER + "/{" + Constants.PATH_PARAM_ID + "}")
    void getProfile(@Path(Constants.PATH_PARAM_ID) String name, Callback<ProfileResponse> callback);

    @GET(APIConstants.URL_SIGN_OUT)
    void sigOut(Callback<DefaultResponseModel> callback);

    @GET(APIConstants.URL_NUMBER + APIConstants.API_SEARCH + "/{" + Constants.PATH_PARAM_COUNTRY_ISO + "}")
    void searchNumbers(@Path(Constants.PATH_PARAM_COUNTRY_ISO) String iso, Callback<NumberListResponse> callback);


    @PUT(APIConstants.URL_ADDRESSBOOK + APIConstants.URL_NUMBERS + APIConstants.API_BLOCK_NUMBERS)
    void blockNumebers(@Body TypedJsonString blockedNumbers, Callback<DefaultResponseModel> callback);

    @GET(APIConstants.URL_ADDRESSBOOK)
    void searchContacts(@Query(Constants.QUERRY) String querry, Callback<List<UserModel>> callback);

    @GET(APIConstants.URL_MESSAGE + APIConstants.API_CONVERSATION + "/{" + Constants.FROM_NUMBER + "}" + "/{" + Constants.TO_NUMBER + "}")
    void getConversationPages(@Path(Constants.FROM_NUMBER) String src, @Path(Constants.TO_NUMBER) String dst, @Query(Constants.PAGE) int page, @Query(Constants.LENGTH) int length, Callback<List<MessageModel>> callback);

    @GET(APIConstants.URL_MESSAGE + APIConstants.URL_LAST_CHATS)
    void getLastChatsPages(@Query(Constants.PAGE) int page, @Query(Constants.LENGTH) int length, Callback<List<LastChatsModel>> callback);


    @DELETE(APIConstants.URL_MESSAGE + "/{" + Constants.USER_NUMBER + "}" + "/{" + Constants.COMPANION_NUMBER + "}")
    void deleteChat(@Path(Constants.USER_NUMBER) String user_number, @Path(Constants.COMPANION_NUMBER) String companion_unmber, Callback<DefaultResponseModel> callback);

    @DELETE(APIConstants.URL_ADDRESSBOOK + "/{" + Constants.QUERRY + "}")
    void deleteContact(@Path(Constants.QUERRY) String querry, Callback<DefaultResponseModel> callback);


    @POST(APIConstants.PUSH)
    void sendDeviceId(@Body TypedJsonString deviceID, Callback<DefaultResponseModel> callback);

    @PUT(APIConstants.URL_MESSAGE +  APIConstants.READ )
    void messageRead(@Body TypedJsonString readModel, Callback<DefaultResponseModel> callback );

    @Multipart
    @POST(APIConstants.VOICE_MESSAGES +  APIConstants.API_SEND )
    void sendVoiceMessage(@Part(APIConstants.AUDIO_FILE)TypedFile file, Callback<DefaultResponseModel> callback );
}