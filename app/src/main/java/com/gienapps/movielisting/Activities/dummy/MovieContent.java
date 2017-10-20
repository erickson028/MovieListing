package com.gienapps.movielisting.Activities.dummy;

import android.support.v7.util.SortedList;

import com.gienapps.movielisting.Objects.MovieItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MovieContent {

    /**
     * An array of sample (dummy) items.
     */

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, MovieItem> ITEM_MAP = new HashMap<Integer, MovieItem>();

    private static final int COUNT = 25;

    private static void addItem(MovieItem item) {
        ITEM_MAP.put(item.id, item);
    }


}
