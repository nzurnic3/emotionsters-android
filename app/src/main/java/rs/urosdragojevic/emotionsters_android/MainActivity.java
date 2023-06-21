package rs.urosdragojevic.emotionsters_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.bubble_text);
        textView1 = findViewById(R.id.bubble_text1);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.89:8080/emotionsters/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Homescreen> call = jsonPlaceHolderApi.getHomescreen();

        call.enqueue(new Callback<Homescreen>() {
            @Override
            public void onResponse(Call<Homescreen> call, Response<Homescreen> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code: " + response.code());
                    textView1.setText("Code: " + response.code());
                    return;
                }
                Homescreen homescreen = response.body();

                    textView.setText(homescreen.getText());
                    textView1.setText(homescreen.getText1());

            }
            @Override
            public void onFailure(Call<Homescreen> call, Throwable t) {
                textView.setText(t.getMessage());
                textView1.setText(t.getMessage());

            }
        });
    }
}
