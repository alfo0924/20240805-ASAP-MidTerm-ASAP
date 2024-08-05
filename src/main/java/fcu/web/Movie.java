package fcu.web;

import java.util.Random;

public class Movie {
    private String title;
    private String releaseDate;
    private String endDate;
    private String description;
    private String director;
    private String mainActors;
    private int id;
    private int boxOffice;

    public Movie(int id, String title, String releaseDate, String endDate, String description, String director, String mainActors) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.endDate = endDate;
        this.description = description;
        this.director = director;
        this.mainActors = mainActors;
        generateRandomBoxOffice();
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getMainActors() {
        return mainActors;
    }

    public void setMainActors(String mainActors) {
        this.mainActors = mainActors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoxOffice() {
        return boxOffice;
    }

    public void generateRandomBoxOffice() {
        Random random = new Random();
        this.boxOffice = random.nextInt(9000000) + 1000000; // Generates a random box office between 1,000,000 and 10,000,000
    }

    @Override
    public String toString() {
        return title;
    }
}