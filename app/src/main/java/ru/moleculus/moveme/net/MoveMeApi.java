package ru.moleculus.moveme.net;

import java.util.Map;

import retrofit.http.EncodedPath;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import ru.moleculus.moveme.net.beans.BaseResponse;
import ru.moleculus.moveme.net.beans.moveme.AuthResponse;
import ru.moleculus.moveme.net.beans.moveme.BaseMoveMeResponse;
import ru.moleculus.moveme.net.beans.moveme.FileUploadResponse;
import ru.moleculus.moveme.net.beans.moveme.OrdersResponse;
import ru.moleculus.moveme.net.beans.moveme.SendNumberResponse;
import ru.moleculus.moveme.net.beans.moveme.UserResponse;
import rx.Observable;

/**
 * Created by Oleg on 11.02.2016.
 */
public interface MoveMeApi {

    @GET("/sms_code")
    public Observable<SendNumberResponse> requestSmsCode(@Query("phone") String phone);

    @GET("/token")
    public Observable<AuthResponse> getToken(@Query("phone") String phone, @Query("code") String code);

    @GET("/orders")
    public Observable<OrdersResponse> getOrders(@Query("token") String token);

    @GET("/orders/my")
    public Observable<OrdersResponse> getMyOrders(@Query("token") String token);

    @GET("/templates")
    public Observable<OrdersResponse> getTemplates(@Query("token") String token);

    @FormUrlEncoded
    @POST("/orders")
    public Observable<BaseMoveMeResponse> sendOrder(@Query("token") String token, @FieldMap Map<String, String> values);

    @FormUrlEncoded
    @POST("/orders/{id}")
    public Observable<BaseMoveMeResponse> sendOrder(@EncodedPath("id") long id, @Query("token") String token, @FieldMap Map<String, String> values);


    @POST("/orders/{id}/save")
    public Observable<BaseMoveMeResponse> saveOrderAsTemplate(@EncodedPath("id") long id, @Query("token") String token);

    @POST("/orders/{id}/save_cancel")
    public Observable<BaseMoveMeResponse> deleteTemplate(@EncodedPath("id") long id, @Query("token") String token);

    @POST("/orders/{id}/accept")
    public Observable<BaseMoveMeResponse> acceptOrder(@EncodedPath("id") long id, @Query("token") String token);

    @POST("/orders/{id}/reject")
    public Observable<BaseMoveMeResponse> rejectOrder(@EncodedPath("id") long id, @Query("token") String token);

    @Multipart
    @POST("/user_file")
    public Observable<FileUploadResponse> uploadFile(@Query("token") String token, @Part("pic") TypedFile file);


    @POST("/user")
    public Observable<BaseMoveMeResponse> registerUser(@Query("token") String token, @Query("type_of_account") int type_of_account,
                                                 @Query("firstname") String firstname, @Query("lastname") String lastname,
                                                 @Query("middlename") String middlename, @Query("mail") String mail);


    @POST("/user")
    public Observable<BaseMoveMeResponse> setPrice(@Query("token") String token, @Query("tariff_first_2hours") String first2,
                                             @Query("tariff_after_2hours") String after2, @Query("tariff_out_of_mkad_per_km")
                                             String outOfMkad);


    @POST("/user")
    public Observable<BaseMoveMeResponse> updateUserPhoto(@Query("token") String token, @Query("user_pic") String userPic);


    @POST("/user")
    public Observable<BaseMoveMeResponse> updateUser(@Query("token") String token, @Query("firstname") String firstname, @Query("lastname") String lastname,
                                               @Query("middlename") String middlename, @Query("mail") String mail);

    @FormUrlEncoded
    @POST("/passports")
    public Observable<BaseMoveMeResponse> sendPassportData(@Query("token") String token, @FieldMap Map<String, String> values);

    @FormUrlEncoded
    @POST("/passports/{id}")
    public Observable<BaseMoveMeResponse> sendPassportData(@EncodedPath("id") long id, @Query("token") String token, @FieldMap Map<String, String> values);

    @FormUrlEncoded
    @POST("/payment_methods")
    public Observable<BaseMoveMeResponse> sendCardData(@Query("token") String token, @FieldMap Map<String, String> values);

    @FormUrlEncoded
    @POST("/payment_methods/{id}")
    public Observable<BaseMoveMeResponse> sendCardData(@EncodedPath("id") long id, @Query("token") String token, @FieldMap Map<String, String> values);

    @GET("/user")
    public Observable<UserResponse> getUser(@Query("token") String token);
}
