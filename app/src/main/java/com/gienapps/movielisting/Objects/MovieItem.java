package com.gienapps.movielisting.Objects;

import java.util.List;

/**
 * Created by erick on 10/20/17.
 */

public class MovieItem {

    public int id, runtime, year;
    public double rating;
    public List<String> genres;
    public String language, title, url, title_long, state, overview, slug, mpa_rating, imdb_code;

    public MovieItem() {
    }

    public static String getMovieUrlImage(MovieItem movieItem) {
        return "http://cayaco.info/movielist/images/" + movieItem.slug + "-cover.jpg";
    }

    public static String getMovieBackgroundImage(MovieItem movieItem) {
        return "http://cayaco.info/movielist/images/" + movieItem.slug + "-backdrop.jpg";
    }
}
