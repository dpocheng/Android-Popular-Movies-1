package dpocheng.myappportfolio.proj1;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import dpocheng.myappportfolio.R;


public class MovieMainFragment extends Fragment {
    private AppCompatActivity mActivity;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public MovieMainFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_sort_order, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        String sorting = mSharedPreferences.getString("sort_order", "popularity.desc");
        if (sorting.equals("popularity.desc")) {
            menu.findItem(R.id.movie_most_popular).setChecked(true);
        } else if (sorting.equals("vote_average.desc")){
            menu.findItem(R.id.movie_top_rated).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mActivity.finish();
        }
        else if (item.getItemId() == R.id.movie_most_popular) {
            mEditor.putString("sort_order", "popularity.desc");
            mEditor.apply();
            item.setChecked(true);
            updateMovie();
            return true;
        }
        else if (item.getItemId() == R.id.movie_top_rated) {
            mEditor.putString("sort_order", "vote_average.desc");
            mEditor.apply();
            item.setChecked(true);
            updateMovie();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.proj1_movie_main_fragment, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.movie_toolbar);
        mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar(toolbar);

        if (mActivity.getSupportActionBar() != null){
            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.movie_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager= new GridLayoutManager(getActivity(), numberOfColumns());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();
        mEditor.apply();

        mAdapter = new MovieAdapter(getActivity(), new ArrayList<MovieInfo>());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }

    private void updateMovie() {
        MovieFetchTask movieFetchTask = new MovieFetchTask();
        String sorting = mSharedPreferences.getString("sort_order", "popularity.desc");
        try {
            ArrayList<MovieInfo> movieInfoList = movieFetchTask.execute(sorting).get();
            if (movieInfoList.size() != 0) {
                mAdapter = new MovieAdapter(getActivity(), movieInfoList);
                mRecyclerView.setAdapter(mAdapter);
            }
            Log.d("updateMovie()", "In MovieFetchTask");
        } catch (ExecutionException | InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 500;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }
}
