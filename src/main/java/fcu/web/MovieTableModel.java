package fcu.web;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class MovieTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "標題", "導演", "年份", "類型"};
    private final List<Movie> movies;

    public MovieTableModel() {
        movies = new ArrayList<>();
    }

    public void addMovie(int id, String title, String director, int year, String genre) {
        movies.add(new Movie(id, title, director, year, genre));
        fireTableRowsInserted(movies.size() - 1, movies.size() - 1);
    }

    public void clear() {
        int size = movies.size();
        if (size > 0) {
            movies.clear();
            fireTableRowsDeleted(0, size - 1);
        }
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Movie movie = movies.get(rowIndex);
        switch (columnIndex) {
            case 0: return movie.getId();
            case 1: return movie.getTitle();
            case 2: return movie.getDirector();
            case 3: return movie.getYear();
            case 4: return movie.getGenre();
            default: return null;
        }
    }
}