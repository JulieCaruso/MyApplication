package com.example.jcaruso.myapplication.movie;

import java.util.ArrayList;
import java.util.List;

public class MovieManager {

    private static List<Movie> movies = new ArrayList<Movie>() {{
        add(new Movie("The Godfather", "Francis Ford Coppola",
                new ArrayList<String>() {{
                    add("Marlon Brando");
                    add("Al Pacino");
                    add("James Caan");
                }},
                "MV5BZTRmNjQ1ZDYtNDgzMy00OGE0LWE4N2YtNTkzNWQ5ZDhlNGJmL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SY1000_CR0,0,704,1000_AL_.jpg"));
        add(new Movie("The Dark Knight", "Christopher Nolan",
                new ArrayList<String>() {{
                    add("Christian Bale");
                    add("Heath Ledger");
                    add("Aaron Eckhart");
                    add("Michel Caine");
                    add("Maggie Gyllenhaal");
                    add("Gary Oldman");
                    add("Morgan Freeman");
                    add("Monique Gabriela Curnen");
                    add("Ron Dean");
                }},
                "MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_SY1000_CR0,0,675,1000_AL_.jpg"));
        add(new Movie("12 Angry Men", "Sidney Lumet",
                new ArrayList<String>() {{
                    add("Henry Fonda");
                    add("Lee J. Cobb");
                    add("Martin Balsam");
                    add("John Fiedler");
                    add("E.G. Marshall");
                    add("Jack Klugman");
                }},
                "MV5BODQwOTc5MDM2N15BMl5BanBnXkFtZTcwODQxNTEzNA@@._V1_SY1000_CR0,0,666,1000_AL_.jpg"));
    }};

    public static List<Movie> getMovies() {
        return movies;
    }
}
