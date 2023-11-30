package mina.app.movieapp;

import java.util.ArrayList;

public class User {
    public User() {
    }

    public User(String email, String firstName, String lastName, ArrayList<String> favoriteFilms, ArrayList<String> genres, ArrayList<String> streamingPlatforms, boolean initialized) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.favoriteFilms = favoriteFilms;
        this.genres = genres;
        this.streamingPlatforms = streamingPlatforms;
        this.initialized = initialized;
    }

    String email;
    String firstName;
    String lastName;
    ArrayList<String> favoriteFilms;
    ArrayList<String> genres;
    ArrayList<String> streamingPlatforms;
    boolean initialized;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<String> getFavoriteFilms() {
        return favoriteFilms;
    }

    public void setFavoriteFilms(ArrayList<String> favoriteFilms) {
        this.favoriteFilms = favoriteFilms;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getStreamingPlatforms() {
        return streamingPlatforms;
    }

    public void setStreamingPlatforms(ArrayList<String> streamingPlatforms) {
        this.streamingPlatforms = streamingPlatforms;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
