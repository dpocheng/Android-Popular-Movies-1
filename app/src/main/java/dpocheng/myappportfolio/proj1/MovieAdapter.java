package dpocheng.myappportfolio.proj1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dpocheng.myappportfolio.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context mContext;
    private List<MovieInfo> mMovieInfoList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_recyclerview_imageview) ImageView mImageView;
        public ViewHolder(ImageView imageView) {
            super(imageView);
            ButterKnife.bind(this, imageView);
        }
    }

    public MovieAdapter(Context context, ArrayList<MovieInfo> movieInfoList) {
        mContext = context;
        mMovieInfoList = movieInfoList;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.proj1_movie_recyclerview_imageview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mImageView.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                MovieInfo movieInfo = mMovieInfoList.get(position);
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movie_info", movieInfo);
                context.startActivity(intent);
            }
        });
        Picasso.with(mContext)
                .load(mMovieInfoList.get(position).getMoviePosterPath())
                .placeholder(R.drawable.error_image_generic)
                .error(R.drawable.error_image_generic)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mMovieInfoList.size();
    }
}
