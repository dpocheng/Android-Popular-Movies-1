package dpocheng.myappportfolio.proj1;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import dpocheng.myappportfolio.BuildConfig;

public class MovieFetchTask extends AsyncTask<String, Void, ArrayList<MovieInfo>> {

    private final String LOG_TAG = MovieFetchTask.class.getSimpleName();

    private ArrayList<MovieInfo> getMovieDataFromJson(String movieDataJsonStr) throws JSONException {

        final String TMDB_RESULTS = "results";
        final String TMDB_ORIGINAL_TITLE = "original_title";
        final String TMDB_POSTER_PATH = "poster_path";
        final String TMDB_OVERVIEW = "overview";
        final String TMDB_VOTE_AVERAGE = "vote_average";
        final String TMDB_RELEASE_DATE = "release_date";
        final String TMDB_BACKDROP_PATH = "backdrop_path";
        final String TMDB_POSTER_PATH_BASE_URL = "https://image.tmdb.org/t/p/w185/";
        final String TMDB_BACKDROP_PATH_BASE_URL = "https://image.tmdb.org/t/p/w300/";

        ArrayList<MovieInfo> movieInfoArrayList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(movieDataJsonStr);
        JSONArray jsonArray = jsonObject.getJSONArray(TMDB_RESULTS);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject movieObject = jsonArray.getJSONObject(i);
            String movieOriginalTitle = movieObject.getString(TMDB_ORIGINAL_TITLE);
            String moviePosterPath = movieObject.getString(TMDB_POSTER_PATH);
            String movieOverview = movieObject.getString(TMDB_OVERVIEW);
            String movieVoteAverage = movieObject.getString(TMDB_VOTE_AVERAGE);
            String movieReleaseDate = movieObject.getString(TMDB_RELEASE_DATE);
            String movieBackdropPath = movieObject.getString(TMDB_BACKDROP_PATH);
            String moviePosterURL = TMDB_POSTER_PATH_BASE_URL + moviePosterPath;
            String movieBackdropURL = TMDB_BACKDROP_PATH_BASE_URL + movieBackdropPath;
            MovieInfo movieInfo = new MovieInfo(movieOriginalTitle, moviePosterURL, movieOverview, movieVoteAverage, movieReleaseDate, movieBackdropURL);
            movieInfoArrayList.add(movieInfo);
        }
        return movieInfoArrayList;
    }

    @Override
    protected ArrayList<MovieInfo> doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String movieDataJsonStr = null;

        try {
            final String TMDB_BASE_URL_POPULAR = "http://api.themoviedb.org/3/movie/popular?";
            final String TMDB_BASE_URL_TOP_RATED = "http://api.themoviedb.org/3/movie/top_rated?";
            final String API_KEY_PARAM = "api_key";

            Uri builtUri = null;

            if (params[0].equals("popularity.desc")) {
                 builtUri = Uri.parse(TMDB_BASE_URL_POPULAR).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DATABASE_API_KEY)
                        .build();
            }
            else if (params[0].equals("vote_average.desc")) {
                builtUri = Uri.parse(TMDB_BASE_URL_TOP_RATED).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DATABASE_API_KEY)
                        .build();
            }


            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            movieDataJsonStr = buffer.toString();
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getMovieDataFromJson(movieDataJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieInfo> result) {
    }
}
