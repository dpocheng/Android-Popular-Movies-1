package dpocheng.myappportfolio.proj1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;

import butterknife.BindView;
import butterknife.ButterKnife;
import dpocheng.myappportfolio.R;

public class MovieDetailsFragment extends Fragment {
    private AppCompatActivity mActivity;
    @BindView(R.id.movie_details_backdrop) ImageView mBackdrop;
    @BindView(R.id.movie_details_poster) ImageView mPoster;
    @BindView(R.id.movie_details_original_title) TextView mOriginalTitle;
    @BindView(R.id.movie_details_release_year) TextView mReleaseDateYear;
    @BindView(R.id.movie_details_release_month) TextView mReleaseDateMonth;
    @BindView(R.id.movie_details_release_day) TextView mReleaseDateDay;
    @BindView(R.id.movie_details_vote_average) TextView mVoteAverage;
    @BindView(R.id.movie_details_vote_average_baase) TextView mVoteAverageBase;
    @BindView(R.id.movie_details_overview) TextView mOverview;

    public MovieDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mActivity.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.proj1_movie_details_fragment, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.movie_toolbar);
        mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar(toolbar);

        if (mActivity.getSupportActionBar() != null){
            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        MovieInfo parcelableExtra = getActivity().getIntent().getParcelableExtra("movie_info");

        ButterKnife.bind(this, rootView);

        Picasso.with(rootView.getContext())
                .load(parcelableExtra.getMovieBackdropPath())
                .placeholder(R.drawable.error_image_generic)
                .error(R.drawable.error_image_generic)
                .into(mBackdrop);
        Picasso.with(rootView.getContext())
                .load(parcelableExtra.getMoviePosterPath())
                .placeholder(R.drawable.error_image_generic)
                .error(R.drawable.error_image_generic)
                .into(mPoster);

        mOriginalTitle.setText(parcelableExtra.getMovieOriginalTitle());
        mReleaseDateYear.setText(getReleaseYear((parcelableExtra.getMovieReleaseDate())));
        mReleaseDateMonth.setText(getReleaseMonth(parcelableExtra.getMovieReleaseDate()));
        mReleaseDateDay.setText(getReleaseDay(parcelableExtra.getMovieReleaseDate()));
        mVoteAverage.setText(parcelableExtra.getMovieVoteAverage());
        mVoteAverageBase.setText(R.string.movie_rated_base);
        mOverview.setText(parcelableExtra.getMovieOverview());

        return rootView;
    }

    private String getReleaseYear(String date) {
        return date.substring(0, 4);
    }

    private String getReleaseMonth(String date) {
        String monthStr = date.substring(5, 7);
        int monthNum = Integer.parseInt(monthStr);
        return new DateFormatSymbols().getMonths()[monthNum - 1];
    }

    private String getReleaseDay(String date) {
        return date.substring(8);
    }
}
