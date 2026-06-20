
package models;

public class User {
    private int id;
    private RatingNode ratingsRoot;
    
    private class RatingNode{
        int movieId;
        int rating;
        RatingNode left,right;

        public RatingNode(int movieId, int rating) {
            this.movieId = movieId;
            this.rating = rating;
            this.left = null;
            this.right = null;
        }
    }

    public User(int id) {
        this.id = id;
        this.ratingsRoot = null;
    }
    
    public void addRating(int movieId,int rating){
        ratingsRoot=insertRec(ratingsRoot,movieId,rating);
    }
   
    private RatingNode insertRec(RatingNode root,int movieId,int rating){
        if(root==null){
            return new RatingNode(movieId,rating);
        }
        if(movieId<root.movieId){
            root.left=insertRec(root.left,movieId,rating);
        } else if(movieId>root.movieId){
            root.right=insertRec(root.right,movieId,rating);
        } else{
            root.rating = rating;
        }
        return root;
    }
    
    private int searchRec(RatingNode root,int movieId){
        if(root==null) return 0;
        if(root.movieId==movieId)return root.rating;
        
        if(movieId<root.movieId){
            return searchRec(root.left,movieId);
        }
        return searchRec(root.right,movieId);
    }
    
    public int getRatingForMovie(int movieId) {
        return searchRec(ratingsRoot, movieId);
    }
    
    public int getId(){
        return id;
    }
       
    public void setId(int id) { 
        this.id = id; 
    }
}
