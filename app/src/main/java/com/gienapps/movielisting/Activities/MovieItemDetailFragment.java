package com.gienapps.movielisting.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gienapps.movielisting.Activities.dummy.MovieContent;
import com.gienapps.movielisting.Objects.MovieItem;
import com.gienapps.movielisting.R;
import com.gienapps.movielisting.Tasks.ImageLoaderTask;

/**
 * A fragment representing a single MovieItem detail screen.
 * This fragment is either contained in a {@link MovieItemListActivity}
 * in two-pane mode (on tablets) or a {@link MovieItemDetailActivity}
 * on handsets.
 */
public class MovieItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private MovieItem mItem;

    private ViewHolder holder;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = MovieContent.ITEM_MAP.get(getArguments().getInt(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.title);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movieitem_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.movieitem_detail)).setText(mItem.overview);
            holder = new ViewHolder(rootView);
            holder.setMovieItem(mItem);
        }



        return rootView;
    }

    public class ViewHolder {

        public final ImageView ivBackdropPhoto, ivCoverPhoto;
        public final TextView tvTitle, tvReleaseYear, tvRating, tvLanguage, tvMpa, tvState, tvUrl;
        MovieItem movieItem;

        public ViewHolder(View view) {
            ivCoverPhoto = (ImageView) view.findViewById(R.id.ivCoverPhoto);
            ivBackdropPhoto = (ImageView) view.findViewById(R.id.ivBackdropPhoto);


            tvRating = (TextView) view.findViewById(R.id.tvRating);
            tvReleaseYear = (TextView) view.findViewById(R.id.tvReleaseYear);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvLanguage = (TextView) view.findViewById(R.id.tvLanguage);
            tvMpa = (TextView) view.findViewById(R.id.tvMpa);
            tvState = (TextView) view.findViewById(R.id.tvState);
            tvUrl = (TextView) view.findViewById(R.id.tvUrl);
        }

        public void setMovieItem(MovieItem movieItem) {
            this.movieItem = movieItem;

            tvTitle.setText(movieItem.title);
            tvRating.setText(String.valueOf(movieItem.rating));
            tvReleaseYear.setText(String.valueOf(movieItem.year));
            tvLanguage.setText(movieItem.language);
            tvMpa.setText(movieItem.mpa_rating);
            tvState.setText(movieItem.state);
            tvUrl.setText(movieItem.url);

            new ImageLoaderTask(ivCoverPhoto).execute(MovieItem.getMovieUrlImage(movieItem));
            new ImageLoaderTask(ivBackdropPhoto).execute(MovieItem.getMovieBackgroundImage(movieItem));
        }
    }
}
