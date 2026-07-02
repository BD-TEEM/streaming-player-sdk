package com.mox_flim.me;

import java.util.List;

public class MovieResponse {
    private int page;
    private List<Movie> results;
    private int total_pages;
    private int total_results;

    // Getters and Setters
    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public List<Movie> getResults() { return results; }
    public void setResults(List<Movie> results) { this.results = results; }

    public int getTotalPages() { return total_pages; }
    public void setTotalPages(int total_pages) { this.total_pages = total_pages; }

    public int getTotalResults() { return total_results; }
    public void setTotalResults(int total_results) { this.total_results = total_results; }

    // Inner class for Movie items
    public static class Movie {
        private int id;
        private String title;
        private String poster_path;
        private String overview;
        private String video_url;

        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getPosterPath() { return poster_path; }
        public void setPosterPath(String poster_path) { this.poster_path = poster_path; }

        public String getOverview() { return overview; }
        public void setOverview(String overview) { this.overview = overview; }

        public String getVideoUrl() { return video_url; }
        public void setVideoUrl(String video_url) { this.video_url = video_url; }
    }
}

