package fcu.web;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class MovieTableModel extends AbstractTableModel {
    private List<Movie> movies;
    private String[] columnNames = {"標題", "上映日期", "下檔日期", "簡介", "導演", "主要演員"};

    public MovieTableModel() {
        movies = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
        fireTableRowsInserted(movies.size() - 1, movies.size() - 1);
    }

    public void clear() {
        movies.clear();
        fireTableDataChanged();
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        Movie movie = movies.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return movie.getTitle();
            case 1:
                return movie.getReleaseDate();
            case 2:
                return movie.getEndDate();
            case 3:
                return movie.getDescription();
            case 4:
                return movie.getDirector();
            case 5:
                return movie.getMainActors();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void removeRow(int selectedRow) {
    }
}