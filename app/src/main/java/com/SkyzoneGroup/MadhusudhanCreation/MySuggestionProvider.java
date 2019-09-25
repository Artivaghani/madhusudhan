package com.SkyzoneGroup.MadhusudhanCreation;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by kevalt on 5/11/2018.
 */

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.sky.FirststChoice";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
