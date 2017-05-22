package dpocheng.myappportfolio.proj1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dpocheng.myappportfolio.R;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proj1_movie_details_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_details_container, new MovieDetailsFragment())
                    .commit();
        }
    }
}
