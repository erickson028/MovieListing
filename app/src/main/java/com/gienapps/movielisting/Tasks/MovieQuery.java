package com.gienapps.movielisting.Tasks;

import android.os.AsyncTask;

import com.gienapps.movielisting.Objects.MovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erick on 10/20/17.
 */

public class MovieQuery {

    public static String queryString = "http://cayaco.info/movielist/list_movies_page1.json";

    DownloadMovieDataListener listener;

    public MovieQuery(DownloadMovieDataListener listener) {
        this.listener = listener;

        new DownloadMoviewData(listener).execute();
    }


    private List<MovieItem> parseJSon(String data) throws JSONException {
        if (data == null)
            return null;

        List<MovieItem> movieItems = new ArrayList<MovieItem>();
        JSONObject jsonData = new JSONObject(data);
        JSONObject jsonData1 = jsonData.getJSONObject("data");
        JSONArray jsonMovies = jsonData1.getJSONArray("movies");
        for (int i = 0; i < jsonMovies.length(); i++) {
            JSONObject jsonMovie = jsonMovies.getJSONObject(i);
            MovieItem movie = new MovieItem();

            movie.id = jsonMovie.getInt("id");
            movie.runtime = jsonMovie.getInt("runtime");
            movie.year = jsonMovie.getInt("year");

            movie.title = jsonMovie.getString("title");
            movie.rating = jsonMovie.getDouble("rating");
            movie.language = jsonMovie.getString("language");
            movie.url   = jsonMovie.getString("url");
            movie.title_long   = jsonMovie.getString("title_long");
            movie.state   = jsonMovie.getString("state");
            movie.overview   = jsonMovie.getString("overview");
            movie.slug   = jsonMovie.getString("slug");
            movie.mpa_rating   = jsonMovie.getString("mpa_rating");
            movie.imdb_code   = jsonMovie.getString("imdb_code");



            movieItems.add(movie);
        }

        return movieItems;
    }

    public interface DownloadMovieDataListener {
        void onComplete(List<MovieItem> movieItems);

        void onFailed();
    }

    public class DownloadMoviewData extends AsyncTask<Void, Void, List<MovieItem>> {

        DownloadMovieDataListener listener;

        public DownloadMoviewData(DownloadMovieDataListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<MovieItem> doInBackground(Void... params) {
            return queryMovies();
        }

        private List<MovieItem> queryMovies() {
            StringBuffer buffer = new StringBuffer();
            try {
                URL url = new URL(queryString);
                InputStream is = null;

                is = url.openConnection().getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                return parseJSon(buffer.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<MovieItem> movieItems) {
            if(movieItems == null) listener.onFailed();

            listener.onComplete(movieItems);
        }
    }

}
