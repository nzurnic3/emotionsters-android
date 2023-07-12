package rs.urosdragojevic.emotionsters_android;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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

public class flowStartActivity extends AppCompatActivity {

    private TextView speechBubble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_start);
        speechBubble = findViewById(R.id.speech_bubble);

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
                    speechBubble.setText(getResources().getString(R.string.code, response.code()));
                    return;
                }
                HomeScreen homeScreen = response.body();
                if (homeScreen != null) {
                    speechBubble.setText(homeScreen.getJacksTopBubble());
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeScreen> call, @NonNull Throwable t) {
                speechBubble.setText(t.getMessage());
            }
        });
    }

//        String i = getIntent().getStringExtra("someKey");
//
//        TextView txt = (TextView) findViewById(R.id.speech_bubble);
//
//        txt.setText(i);

//        Button cancelButton = findViewById(R.id.cancel_button);
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(i);
//            }
//        });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search));
        return true;
    }
}