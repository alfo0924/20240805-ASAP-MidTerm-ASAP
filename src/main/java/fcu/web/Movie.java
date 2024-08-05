package fcu.web;

public class Movie {
    private int id;
    private String title;
    private String director;
    private int year;
    private String genre;

    public Movie(int id, String title, String director, int year, String genre) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.year = year;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }
}