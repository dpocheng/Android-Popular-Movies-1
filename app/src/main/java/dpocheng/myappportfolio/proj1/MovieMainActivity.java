package dpocheng.myappportfolio.proj1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dpocheng.myappportfolio.R;

public class MovieMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proj1_movie_main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_main_container, new MovieMainFragment())
                    .commit();
        }
    }
}
