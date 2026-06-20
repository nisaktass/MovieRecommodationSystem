
package datastructures;

import models.UserSimilarity;

public class HeapNode {
    public UserSimilarity data;
    public HeapNode left;
    public HeapNode right;
     public HeapNode parent;

    public HeapNode(UserSimilarity data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.parent=null;
    }
    
}
