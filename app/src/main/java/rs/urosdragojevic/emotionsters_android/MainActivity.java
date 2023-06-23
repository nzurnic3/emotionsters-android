package rs.urosdragojevic.emotionsters_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textView1;
    private Toolbar toolbar;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.bubble_text);
        textView1 = findViewById(R.id.bubble_text1);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

//        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(@NonNull MenuItem item) {
//                Toast.makeText(MainActivity.this, "search is expanded", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(@NonNull MenuItem item) {
//                Toast.makeText(MainActivity.this, "search is collapsed", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        };
//        menu.findItem(R.id.search).setOnActionExpandListener(onActionExpandListener);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search data here...");

        return true;
    }
//    protected void onSaveInstanceState(Bundle outState) { NESTO ZA UKLANJANJE TEKSTA NAKON SEARCHA
//        super.onSaveInstanceState(outState);
//        searchView.setQuery("", false);
//        searchView.setIconified(true);
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.account) {
            Toast.makeText(this, "account is pressed", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.search) {
            Toast.makeText(this, "search is pressed", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.menu) {
            Toast.makeText(this, "menu is pressed", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
