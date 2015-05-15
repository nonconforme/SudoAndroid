package com.thinkmobiles.sudo.core.rest;



import android.content.Context;

import com.thinkmobiles.sudo.core.APIConstants;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.models.AuthenticatedModel;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.LoginResponse;
import com.thinkmobiles.sudo.models.ProfileResponse;
import com.thinkmobiles.sudo.models.UpdateProfileModel;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.models.chat.LastChatsModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;
import com.thinkmobiles.sudo.models.counties.CountryModel;
import com.thinkmobiles.sudo.models.numbers.BuyNumberResponce;
import com.thinkmobiles.sudo.models.numbers.NumberListResponse;
import com.thinkmobiles.sudo.utils.TypedJsonString;

import org.json.JSONObject;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedString;

/**
 * Created by Sasha on 19.11.2014.
 */
public interface RetrofitInterface {

    @FormUrlEncoded
    @POST(APIConstants.URL_SIGN_IN)
    void signIn(@Field(Constants.EMAIL) String email, @Field(Constants.PASSWORD) String password,
                Callback<LoginResponse> callback);
    @FormUrlEncoded
    @POST(APIConstants.URL_SIGN_UP)
    void signUp(@Field(Constants.EMAIL) String email, @Field(Constants.PASSWORD) String password,
                Callback<LoginResponse> callback);

    @FormUrlEncoded
    @POST(APIConstants.API_BAY_NUMBER)
    void buyCredits(@Field(Constants.RECEIPT) String receipt, @Field(Constants.PROVIDER) String provider,
                Callback<BuyNumberResponce> callback);

    @POST(APIConstants.URL_ADDRESSBOOK)
    void addContact(@Body TypedJsonString userModel,
                Callback<DefaultResponseModel> callback);

    @FormUrlEncoded
    @POST(APIConstants.URL_NUMBER + APIConstants.API_BAY_NUMBER)
    void buyNumber(@Field(Constants.PARAM_NUMBER) String number, @Field(Constants.COUNTRY_CODE) String countryIso, @Field(Constants.PARAM_PACKAGE_NAME) String packageName,
                    Callback<BuyNumberResponce> callback);
//    @FormUrlEncoded

    @PUT(APIConstants.URL_ADDRESSBOOK + "/{" + Constants.PATH_PARAM_ID + "}" )
    void updateContact( @Body TypedJsonString userModel, @Path(Constants.PATH_PARAM_ID) String id,
                       Callback<UpdateProfileModel> callback);

    @GET(APIConstants.URL_ADDRESSBOOK)
    void getContacts( Callback<List<UserModel>> callback);


    @GET(APIConstants.URL_MESSAGE + APIConstants.URL_LAST_CHATS)
    void getLastChats(Callback<List<LastChatsModel>> callback);

    @GET(APIConstants.URL_MESSAGE + APIConstants.API_CONVERSATION + "/{" + Constants.FROM_NUMBER + "}" + "/{" + Constants.TO_NUMBER + "}")
    void getConversation(@Path(Constants.FROM_NUMBER) String src, @Path(Constants.TO_NUMBER) String dst, Callback<List<MessageModel>> callback);

    @FormUrlEncoded
    @POST(APIConstants.URL_MESSAGE + APIConstants.API_SEND)
    void senMessage(@Field(Constants.FROM_NUMBER) String src, @Field(Constants.TO_NUMBER) String dst, @Field(Constants.TEXT_MESSAGE) String text,
                    @Field(Constants.TYPE_MESSAGE) String type, Callback<DefaultResponseModel> callback);


    @GET(APIConstants.URL_PRICE + APIConstants.API_COUNTRIES)
    void getCountries(Callback<List<CountryModel>> callback);

//    @FormUrlEncoded
//    @POST(APIConstants.URL_MESSAGE+APIConstants.URL_SEND)
//    void sendMessage(@Field(Constants.FROM_NUMBER) String from, @Field(Constants.TO_NUMBER) String to,
//                     @Field(Constants.TEXT_MESSAGE) String text, @Field(Constants.TYPE_MESSAGE) String type,
//                     Callback<DefaultResponseModel> callback);
//
    @GET(APIConstants.ACTION_IS_AUTHENTICATED)
    void isAuthenticated(Callback<AuthenticatedModel> callback);
//
//    @GET(APIConstants.URL_MESSAGE + APIConstants.API_LAST_CONVERSATION)
//    void lastConversations(Callback<ConversationResponse> callback);
//
//    @GET(APIConstants.URL_MESSAGE + APIConstants.API_CONVERSATION + "/{owner}"+"/{companion}"  )
//    void lastHistory(@Path("owner") String owner, @Path("companion") String companion, Callback<ConversationResponse> callback);
//
    @GET(APIConstants.URL_USER + "/{" + Constants.PATH_PARAM_ID + "}")
    void getProfile(@Path(Constants.PATH_PARAM_ID) String name, Callback<ProfileResponse> callback);

    @GET(APIConstants.URL_SIGN_OUT)
    void sigOut(Callback<DefaultResponseModel> callback);

    @GET(APIConstants.URL_NUMBER + APIConstants.API_SEARCH + "/{" + Constants.PATH_PARAM_COUNTRY_ISO + "}")
    void searchNumbers(@Path(Constants.PATH_PARAM_COUNTRY_ISO) String iso, Callback<NumberListResponse> callback);

//    @FormUrlEncoded
//    @PUT(APIConstants.URL_USER + "/{" + Constants.PATH_PARAM_USER_ID + "}")
//    void updateProfile(@Path(Constants.PATH_PARAM_USER_ID) String uId, @Field(Constants.USER) UserModel password,
//                       Callback<LoginResponse> callback);
//
//    @PUT(APIConstants.URL_USER + "/{" + Constants.PATH_PARAM_ID + "}")
//    void addToAddressBook(@Body AddressBookRequest addressBook, @Path(Constants.PATH_PARAM_ID) String id,
//                          Callback<DefaultResponseModel> callback);
}
