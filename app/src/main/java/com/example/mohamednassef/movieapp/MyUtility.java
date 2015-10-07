package com.example.mohamednassef.movieapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Inspired by stackoverflow code in the link below
 * http://stackoverflow.com/questions/9054193/how-to-use-sharedpreferences-to-save-more-than-one-values.
 */
public class MyUtility {


    public static boolean addFavoriteMovie(Context context,String newFavoriteMovie, int mode) {
        //Get previous favorite items


        String key;
        if (mode==0)
        {
            key = "favorites";
        }
        else
        {
            key = "posters";
        }

        String favoriteList = getFavoriteMoviesString(context, null, key);
        // Append new Favorite item
        if(favoriteList!=null && favoriteList.length()>0){
            if (favoriteList.charAt(favoriteList.length()-1) == ',')
            {
                favoriteList = favoriteList+newFavoriteMovie;

            }
            else
            {
                favoriteList = favoriteList+","+newFavoriteMovie;
            }

        }else{
            favoriteList = newFavoriteMovie;
        }
        // Save in Shared Preferences
        return addMovieToPreferences(context, favoriteList, key);
    }


    // No Change
    public static String[] getFavoriteMoviesArray(Context context, String key){
        String favoriteMovies = getFavoriteMoviesString(context, null, key);
        return convertStringToArray(favoriteMovies);
    }

    // No Change
    private static boolean addMovieToPreferences(Context context,String newFavoriteList,String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, newFavoriteList);
        editor.commit();
        //Toast.makeText(context, newFavoriteList, Toast.LENGTH_LONG).show();
        return true;
    }

    // No Change
    public static String getFavoriteMoviesString(Context context,String defaultValue,String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String temp = sharedPreferences.getString(key, defaultValue);
        return temp;
    }


    // No Change
    public static Boolean isFavorite(Context context, int movieID){

        Boolean isFavorite = false;
        String favoriteMovies = getFavoriteMoviesString(context, null, "favorites");


        if (favoriteMovies!=null && favoriteMovies.length()>0)

        {
            String [] favoriteMoviesArray = convertStringToArray(favoriteMovies);
            for(int i=0; i<favoriteMoviesArray.length; i++)
            {
                if (movieID == Integer.parseInt(favoriteMoviesArray[i]))
                {
                    isFavorite = true;
                    break;
                }
            }

        }


        return isFavorite;
    }


    // No Change
    public static String[] convertStringToArray(String str){
        String[] arr = str.split(",");
        return arr;
    }


    // No Change
    public static String convertArrayToString(String[] strArray){
        String str="";
        if (strArray.length>0)
        {
            for (String s: strArray)
            {
                str = str+s+",";
            }
        }
        return str;
    }

    public static void removeMovieFromFavorite (Context context, String movieID, int mode)


    {

        String key;
        if (mode==0)
        {
            key = "favorites";
        }
        else
        {
            key = "posters";
        }

        String favoriteMovies = getFavoriteMoviesString(context, null, key);
        String [] favoriteMoviesArray = convertStringToArray(favoriteMovies);
        String [] updatedMovieArray = new String [(favoriteMoviesArray.length-1)];

        int i;
        int j=0;
        for(i=0; i<favoriteMoviesArray.length; i++)
        {
            if (!favoriteMoviesArray[i].equals(movieID))
            {
                updatedMovieArray[j] = favoriteMoviesArray[i];
                j++;
            }

        }

        String UpdatedMovieString = convertArrayToString(updatedMovieArray);
        //Toast.makeText(context,UpdatedMovieString , Toast.LENGTH_LONG).show();
        updateFavoriteString(context,UpdatedMovieString, key);

    }




    // No change
    public static void updateFavoriteString(Context context, String newFavorites, String key)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, newFavorites);
        editor.commit();

    }


}
