package rs.urosdragojevic.emotionsters_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView speechBubbleTop;
    private TextView speechBubbleBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speechBubbleTop = findViewById(R.id.speech_bubble_top);
        speechBubbleBottom = findViewById(R.id.speech_bubble_bottom);
        Button buttonJoy = findViewById(R.id.joy_button);

        buttonJoy.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), JoyActivity.class);
            startActivity(i);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.89:8080/emotionsters/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<HomeScreen> call = jsonPlaceHolderApi.getHomeScreen();
        call.enqueue(new Callback<HomeScreen>() {
            @Override
            public void onResponse(@NonNull Call<HomeScreen> call, @NonNull Response<HomeScreen> response) {
                if (!response.isSuccessful()) {
                    speechBubbleTop.setText(getResources().getString(R.string.code, response.code()));
                    speechBubbleBottom.setText(getResources().getString(R.string.code, response.code()));
                    return;
                }
                HomeScreen homeScreen = response.body();
                if (homeScreen != null) {
                    speechBubbleTop.setText(homeScreen.getJacksTopSpeechBubble());
                    speechBubbleBottom.setText(homeScreen.getJacksBottomSpeechBubble());
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeScreen> call, @NonNull Throwable t) {
                speechBubbleTop.setText(t.getMessage());
                speechBubbleBottom.setText(t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
