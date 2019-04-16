package peter.retrofit;

import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import peter.activities.Login_Activity;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.0.26:5000/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();
                                Request.Builder requestBuilder = original.newBuilder()
                                        .addHeader("Authorization", "Bearer " + Login_Activity.token)
                                        .method(original.method(), original.body());

                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }
                        }
                ).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }
    private static String AUTH; // = "Basic " + Base64.encodeToString(("leon@hll.com:1234").getBytes(), Base64.NO_WRAP);

    private static ApiClient mInstance;

    private ApiClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();

                                Request.Builder requestBuilder = original.newBuilder()
                                        .addHeader("Authorization", AUTH)
                                        .method(original.method(), original.body());

                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }
                        }
                ).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static synchronized ApiClient getInstance(String user, String password) {

        AUTH = "Basic " + Base64.encodeToString((user + ":" + password).getBytes(), Base64.NO_WRAP);
        if (mInstance == null) {
            mInstance = new ApiClient();
        }
        return mInstance;
    }

    public ApiService getService() {
        return retrofit.create(ApiService.class);
    }


}
