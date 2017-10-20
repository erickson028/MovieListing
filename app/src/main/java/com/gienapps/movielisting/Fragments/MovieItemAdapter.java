package com.gienapps.movielisting.Fragments;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gienapps.movielisting.Fragments.MovieItemFragment.OnListFragmentInteractionListener;
import com.gienapps.movielisting.Objects.MovieItem;
import com.gienapps.movielisting.R;
import com.gienapps.movielisting.Tasks.ImageLoaderTask;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MovieItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MovieItemAdapter extends RecyclerView.Adapter<MovieItemAdapter.ViewHolder> {

    private SortedList<MovieItem> mValues;

    public MovieItemAdapter() {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movieitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setmItem(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void clear() {
        mValues.clear();
    }

    public void addAll(List<MovieItem> movieItems) {
        if (movieItems == null) return;
        mValues.addAll(movieItems);
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
            new ImageLoaderTask(ivBackdrop).execute(MovieItem.getMovieBackgroundImage(mItem));
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitle.getText() + "'";
        }
    }
}
