package fcu.web;

public class Movie {
    private String title;
    private String releaseDate;
    private String endDate;
    private String description;
    private String director;
    private String mainActors;

    public Movie(String title, String releaseDate, String endDate, String description, String director, String mainActors) {
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }

    public String getMainActors() {
        return mainActors;
    }
}