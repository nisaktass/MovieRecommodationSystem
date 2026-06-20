
package datastructures;

public class MaxHeap<T extends Comparable<T>> {
  
    private static class HeapNode<T>{
        T data;
        HeapNode<T> left,right,parent;

        public HeapNode(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }
    
    private HeapNode <T> root;
    private int size;
    
    public MaxHeap(){
        this.root=null;
        this.size=0;
    }
    
    public int getSize(){
    return size;
}
    public boolean isEmpty(){
        return size==0;
        
    }
    
    public void insert(T item){
       HeapNode<T> newNode=new HeapNode<>(item);
       size++;
       if(root==null){
           root=newNode;
           return;
       }
       HeapNode<T>parentNode=findParentForIndex(size);
       newNode.parent=parentNode;
       
       if(parentNode.left==null){
           parentNode.left=newNode;
       } else{
           parentNode.right=newNode;
       }
       bubbleUp(newNode);
    }
    
    public T extractMax(){
        if(root==null) return null;
        T maxItem=root.data;
        if(size==1){
            root=null;
            size--;
            return maxItem;
        }
        HeapNode <T> lastNode=findNodeAndDisconnect(size);
        root.data=lastNode.data;
        size--;
        bubbleDown(root);
        return maxItem;
    }
    
    private void bubbleUp(HeapNode<T> node) {
        while (node.parent != null && node.data.compareTo(node.parent.data) > 0) {
            T temp = node.data;
            node.data = node.parent.data;
            node.parent.data = temp;
            node = node.parent;
        }
    }
    
    private void bubbleDown(HeapNode<T> node) {
        HeapNode<T> largestChild;
        while (node.left != null) {
            largestChild = node.left;
            if (node.right != null && node.right.data.compareTo(node.left.data) > 0) {
                largestChild = node.right;
            }
            if (node.data.compareTo(largestChild.data) >= 0) {
                break;
            }
            T temp = node.data;
            node.data = largestChild.data;
            largestChild.data = temp;
            node = largestChild;
        }
    }
    
    private HeapNode<T> findParentForIndex(int index) {
        String binary = Integer.toBinaryString(index);
        HeapNode<T> current = root;
        for (int i = 1; i < binary.length() - 1; i++) {
            if (binary.charAt(i) == '0') {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return current;
    }
    
    private HeapNode<T> findNodeAndDisconnect(int index) {
        String binary = Integer.toBinaryString(index);
        HeapNode<T> current = root;
        for (int i = 1; i < binary.length(); i++) {
            if (binary.charAt(i) == '0') {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        if (current.parent != null) {
            if (current.parent.left == current) {
                current.parent.left = null;
            } else {
                current.parent.right = null;
            }
        }
        return current;
    }
}
