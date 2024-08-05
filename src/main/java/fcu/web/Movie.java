package fcu.web;

public class Movie {
    private String title;
    private String releaseDate;
    private String endDate;
    private String description;
    private String director;
    private String mainActors;
    private int id;

    public Movie(int id, String title, String releaseDate, String endDate, String description, String director, String mainActors) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.endDate = endDate;
        this.description = description;
        this.director = director;
        this.mainActors = mainActors;
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

    @Override
    public String toString() {
        return title;
    }
}