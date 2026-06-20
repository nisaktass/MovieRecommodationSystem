
package models;

public class MovieRating implements Comparable<MovieRating>{
 private int movieId;
    private int rating;

    public MovieRating(int movieId, int rating){
        this.movieId = movieId;
        this.rating = rating;
    }

    public int getMovieId(){
        return movieId;
    }

    public int getRating(){
        return rating;
    }

    @Override
    public int compareTo(MovieRating other){
        return this.rating - other.rating;
    }
}   
    

