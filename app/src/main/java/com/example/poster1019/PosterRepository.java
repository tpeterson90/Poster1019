package com.example.poster1019;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The one place that owns the app's movie data: all posters, the selection, and the favorites.
 */
public class PosterRepository {

    // Notified whenever favorites are added or removed (Favorite List, badge).
    public interface FavoritesListener{
        void onFavoritesChanged();
    }

    private static PosterRepository instance;

    private final List<Posters> allPosters;
    private final FavoritesStore favoritesStore;
    private final List<FavoritesListener> favoritesListeners = new ArrayList<>();

    // Creates the single instance on first use. It keeps the application context,
    // never an Activity: holding an Activity in a singleton would leak it.
    public static synchronized PosterRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PosterRepository(context.getApplicationContext());
        }
        return instance;
    }

    private PosterRepository(Context appContext) {
        allPosters = Collections.unmodifiableList(createPosters());
        favoritesStore = new FavoritesStore(appContext);

        // Forget saved favorites whose poster no longer exists in the app.
        List<String> validIds = new ArrayList<>();
        for (Posters poster : allPosters) {
            validIds.add(poster.id);
        }
        favoritesStore.retainOnly(validIds);
    }

    public List<Posters> getAllPosters(){
        return allPosters;
    }

    public Posters findById(String id){
        for(Posters posters:allPosters){
            if(posters.id.equals(id)){
                return posters;
            }
        }
        return null;
    }

    // deselects every poster
    public void clearSelection(){
        for(Posters poster : allPosters){
            poster.isSelected = false;
        }
    }

    //get favorite posters, in the order they were added
    public List<Posters> getFavorites(){
        List<Posters> favorites = new ArrayList<>();
        for (String id : favoritesStore.getIds()) {
            Posters poster = findById(id);
            if (poster != null) {
                favorites.add(poster);
            }
        }
        return favorites;
    }

    public int getFavoriteCount(){
        return favoritesStore.getIds().size();
    }

    // Adds posters to favorites, skipping ones already there.
    // Returns how many were new, so the page can show the right message.
    public int addFavorites(List<Posters> posters) {
        List<String> ids = new ArrayList<>();
        for (Posters poster : posters) {
            ids.add(poster.id);
        }
        int added = favoritesStore.addAll(ids);
        if (added > 0) {
            notifyFavoritesChanged();
        }
        return added;
    }

    // Removes one favorite. Returns true if it was a favorite.
    public boolean removeFavorite(String id) {
        boolean removed = favoritesStore.remove(id);
        if (removed) {
            notifyFavoritesChanged();
        }
        return removed;
    }

    // The listener (observer) pattern: pages register to hear about changes.
    // Whoever adds a listener must remove it again (onDestroyView / onDestroy).
    public void addFavoritesListener(FavoritesListener listener) {
        if (!favoritesListeners.contains(listener)) {
            favoritesListeners.add(listener);
        }
    }

    public void removeFavoritesListener(FavoritesListener listener) {
        favoritesListeners.remove(listener);
    }

    private void notifyFavoritesChanged() {
        for(FavoritesListener listener : new ArrayList<>(favoritesListeners)){
            listener.onFavoritesChanged();
        }
    }

    private static List<Posters> createPosters() {
        List<Posters> posters = new ArrayList<>();

        Posters happiestSeason = new Posters();
        happiestSeason.id = "happiest";
        happiestSeason.image = R.drawable.poster1;
        happiestSeason.name = "The Happiest Season";
        happiestSeason.rating = 5f;
        happiestSeason.createdBy = "Clea DuVall";
        happiestSeason.story = "A Christmas Comedy about a lesbian couple";
        posters.add(happiestSeason);

        Posters homeAlone = new Posters();
        homeAlone.id = "homeAlone";
        homeAlone.image = R.drawable.poster2;
        homeAlone.name = "HomeAlone";
        homeAlone.rating = 4f;
        homeAlone.createdBy = "John Hughes";
        homeAlone.story = "An eight-year-old is forgotten by his family on Christmas.";
        posters.add(homeAlone);

        Posters elf = new Posters();
        elf.id = "elf";
        elf.image = R.drawable.poster3;
        elf.name = "Elf";
        elf.rating = 3f;
        elf.createdBy = "Jon Favreau";
        elf.story = "A human raised by elves travels to New York to meet his biological father.";
        posters.add(elf);

        Posters grinch = new Posters();
        grinch.id = "grinch";
        grinch.image = R.drawable.poster4;
        grinch.name = "How the Grinch Stole Christmas";
        grinch.rating = 5f;
        grinch.createdBy = "Ron Howard";
        grinch.story = "A reclusive Grinch tries to steal Christmas.";
        posters.add(grinch);

        Posters santa = new Posters();
        santa.id = "santa";
        santa.image = R.drawable.poster5;
        santa.name = "The Santa Clause";
        santa.rating = 5f;
        santa.createdBy = "John Pasquin";
        santa.story = "Scott accidentally causes Santa Clause to fall off a roof and now most take his place.";
        posters.add(santa);


        Posters nightmareBefore = new Posters();
        nightmareBefore.id = "nightmareBefore";
        nightmareBefore.image = R.drawable.poster6;
        nightmareBefore.name = "The Nightmare Before Christmas";
        nightmareBefore.rating = 5f;
        nightmareBefore.createdBy = "Tim Burton";
        nightmareBefore.story = "The pumpkin king wants to see what its like to be Santa Clause.";


        return posters;

    }


}
