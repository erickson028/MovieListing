package com.gienapps.movielisting.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gienapps.movielisting.Activities.dummy.MovieContent;
import com.gienapps.movielisting.Objects.MovieItem;
import com.gienapps.movielisting.R;
import com.gienapps.movielisting.Tasks.ImageLoaderTask;
import com.gienapps.movielisting.Tasks.MovieQuery;

import java.util.List;

/**
 * An activity representing a list of MovieItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieitem_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        View recyclerView = findViewById(R.id.movieitem_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.movieitem_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);

        new MovieQuery(new MovieQuery.DownloadMovieDataListener() {
            @Override
            public void onComplete(List<MovieItem> movieItems) {
                adapter.addAll(movieItems);
            }

            @Override
            public void onFailed() {
                Toast.makeText(MovieItemListActivity.this, "Failed to load movie list from server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MovieAdapter
            extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

        private SortedList<MovieItem> mValues;

        public MovieAdapter() {

            mValues = new SortedList<MovieItem>(MovieItem.class, new SortedList.Callback<MovieItem>() {
                @Override
                public int compare(MovieItem o1, MovieItem o2) {
                    return 0;
                }

                @Override
                public void onChanged(int position, int count) {
                    notifyItemChanged(position);
                }

                @Override
                public boolean areContentsTheSame(MovieItem oldItem, MovieItem newItem) {
                    return false;
                }

                @Override
                public boolean areItemsTheSame(MovieItem item1, MovieItem item2) {
                    return false;
                }

                @Override
                public void onInserted(int position, int count) {
                    notifyItemInserted(position);
                }

                @Override
                public void onRemoved(int position, int count) {
                    notifyItemRemoved(position);
                }

                @Override
                public void onMoved(int fromPosition, int toPosition) {
                    notifyItemMoved(fromPosition, toPosition);
                }
            });
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            super.onViewRecycled(holder);
            holder.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_movieitem, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.setmItem(mValues.get(position));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putInt(MovieItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        MovieItemDetailFragment fragment = new MovieItemDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.movieitem_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, MovieItemDetailActivity.class);
                        intent.putExtra(MovieItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }


        public void addAll(List<MovieItem> movieItems) {
            if (movieItems == null) return;
            mValues.addAll(movieItems);
            for (MovieItem movie :
                    movieItems) {
                MovieContent.ITEM_MAP.put(movie.id, movie);
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView tvTitle, tvReleaseDate;
            public final ImageView ivBackdrop;
            public MovieItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                tvReleaseDate = (TextView) view.findViewById(R.id.tvReleaseDate);
                tvTitle = (TextView) view.findViewById(R.id.tvTitle);
                ivBackdrop = (ImageView) view.findViewById(R.id.ivBackdrop);
            }

            public void setmItem(MovieItem mItem) {
                this.mItem = mItem;
                tvTitle.setText(mItem.title);
                tvReleaseDate.setText(String.valueOf(mItem.year));
                ivBackdrop.setImageBitmap(null);
                new ImageLoaderTask(ivBackdrop).execute(MovieItem.getMovieBackgroundImage(mItem));
            }

            @Override
            public String toString() {
                return super.toString() + " '" + tvTitle.getText() + "'";
            }

            public void recycle() {
                ivBackdrop.setImageBitmap(null);
            }
        }
    }
}
