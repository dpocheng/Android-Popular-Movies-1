package dpocheng.myappportfolio.proj1;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieInfo implements Parcelable {
    private String movieOriginalTitle;
    private String movieReleaseDate;
    private String moviePosterPath;
    private String movieVoteAverage;
    private String movieOverview;
    private String movieBackdropPath;

    public MovieInfo(String movieOriginalTitle, String moviePosterPath, String movieOverview, String movieVoteAverage, String movieReleaseDate, String movieBackdropPath) {
        this.movieOriginalTitle = movieOriginalTitle;
        this.moviePosterPath = moviePosterPath;
        this.movieOverview = movieOverview;
        this.movieVoteAverage = movieVoteAverage;
        this.movieReleaseDate = movieReleaseDate;
        this.movieBackdropPath = movieBackdropPath;
    }

    private MovieInfo(Parcel in) {
        movieOriginalTitle = in.readString();
        moviePosterPath = in.readString();
        movieOverview = in.readString();
        movieVoteAverage = in.readString();
        movieReleaseDate = in.readString();
        movieBackdropPath = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "MovieInfo{" +
                "movieOriginalTitle='" + movieOriginalTitle + '\'' +
                ", moviePosterPath='" + moviePosterPath + '\'' +
                ", movieOverview='" + movieOverview + '\'' +
                ", movieVoteAverage='" + movieVoteAverage + '\'' +
                ", movieReleaseDate='" + movieReleaseDate + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieOriginalTitle);
        parcel.writeString(moviePosterPath);
        parcel.writeString(movieOverview);
        parcel.writeString(movieVoteAverage);
        parcel.writeString(movieReleaseDate);
        parcel.writeString(movieBackdropPath);
    }

    public static final Parcelable.Creator<MovieInfo> CREATOR = new Parcelable.Creator<MovieInfo>() {
        public MovieInfo createFromParcel(Parcel parcel) {
            return new MovieInfo(parcel);
        }

        public MovieInfo[] newArray(int i) {
            return new MovieInfo[i];
        }
    };

    public String getMovieOriginalTitle() {
        return movieOriginalTitle;
    }

    public void setTitle(String movieOriginalTitle) {
        this.movieOriginalTitle = movieOriginalTitle;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setPosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setOverView(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMovieVoteAverage() {
        return movieVoteAverage;
    }

    public void setVoteAverage(String movieVoteAverage) {
        this.movieVoteAverage = movieVoteAverage;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieBackdropPath() {
        return movieBackdropPath;
    }

    public void setBackdrops(String movieBackdropPath) {
        this.movieBackdropPath = movieBackdropPath;
    }
}

