package rs.urosdragojevic.emotionsters_android;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Mock
    private JsonPlaceHolderApi mockApi;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(
            MainActivity.class);

    @Test
    public void shouldDisplayTextMessage() throws IOException {

        MockWebServer mockWebServer = new MockWebServer();

        Gson gson = new GsonBuilder().setLenient().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(mockWebServer.url("").toString())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mockWebServer.enqueue(new MockResponse().setBody("{" + '\u0022' + "text" + '\u0022' +
                ":" + '\u0022' + "Hi, I'm Jack. Welcome to Monster Kingdom!" + '\u0022' +
                "," + '\u0022' + "text1" + '\u0022' + ":" + '\u0022' + "Scroll down to meet my friends!" + '\u0022' + "}"));

        mockApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Homescreen> call = mockApi.getHomescreen();

        assertNotNull(call.execute());
    }

    @Test
    public void testVisibilityOfDifferentIDs() {
        onView(withId(R.id.bubble_text)).check(matches(isDisplayed()));
        onView(withId(R.id.bubble_text1)).check(matches(isDisplayed()));
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView1)).check(matches(isDisplayed()));
    }

    @Test
    public void testIsTextDisplayed() {
        onView(withId(R.id.bubble_text)).check(matches(withText("Hi, I'm Jack. Welcome to Monster Kingdom!")));
        onView(withId(R.id.bubble_text1)).check(matches(withText("Scroll down to meet my friends!")));
    }
}
