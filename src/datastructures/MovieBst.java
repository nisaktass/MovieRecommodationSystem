
package datastructures;

import models.Movie;

public class MovieBst {
    
    private Node root;
    
    private static class Node{
        Movie movie;
        Node left,right;
        Node(Movie movie){
            this.movie=movie;
            this.left=this.right=null;
        }
    }
   
    public void insert(Movie movie){
        root=insertRec(root,movie);
    }
    
    private Node insertRec(Node root, Movie movie){
        if(root==null){
            return new Node(movie);
        }
        if(movie.getId()<root.movie.getId()){
            root.left=insertRec(root.left,movie);
        }else if(movie.getId()>root.movie.getId()){
            root.right=insertRec(root.right,movie);
        }
        return root;
    }
   
    private Node searchRec(Node root, int id){
        if(root==null||root.movie.getId()==id){
            return root;
        }
        if(id<root.movie.getId()){
            return searchRec(root.left,id);
        }
        return searchRec(root.right,id);
    }
    
    public String searchName(int id){
        Node result=searchRec(root,id);
        if(result!=null){
           return result.movie.getName();
        }else{
           return null;
        }
    }
    
}
