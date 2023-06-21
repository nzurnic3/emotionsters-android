package rs.urosdragojevic.emotionsters_android;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("homescreenText")
    Call<Homescreen> getHomescreen();
}
