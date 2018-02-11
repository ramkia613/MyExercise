package com.ramki.myexercise.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Ramki on 2/11/2018.
 */
public class Response<T> {

    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;
//    public static final int LOADING = 2;
//    public static final int BAD_REQUEST = 3;
    public static final int NO_NETWORK = 2;
    public static final int UNAUTHORISED = 3;

    @NonNull
    public final int status;

    @Nullable
    public final T data;

    @Nullable
    public final Throwable error;

    public Response(@NonNull int status, @Nullable T data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    @Nullable
    public static <T> Response<T> noNetwork(@Nullable T data, Throwable error) {
        return new Response<>(NO_NETWORK, data, error);
    }

    @Nullable
    public static <T> Response<T> success(@Nullable T data) {
        return new Response<>(SUCCESS, data, null);
    }

    @Nullable
    public static <T> Response<T> error(@Nullable T data, Throwable error) {
        return new Response<>(FAILURE, data, error);
    }

   /* @Nullable
    public static <T> Response<T> badRequest(@Nullable T data, Throwable error) {
        return new Response<>(BAD_REQUEST, data, error);
    }*/

    @Nullable
    public static <T> Response<T> unAuthorised(@Nullable T data) {
        return new Response<>(UNAUTHORISED, data, null);
    }

    /*@Nullable
    public static <T> Response<T> loading(@Nullable T data) {
        return new Response<>(LOADING, data, null);
    }*/
}
