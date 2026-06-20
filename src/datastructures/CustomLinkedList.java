
package datastructures;

public class CustomLinkedList<T> {
    private Node <T> head;
    private int size;
    
    public static class Node<T> {
        public T data;
        public Node<T> next;
        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public CustomLinkedList() {
        this.head=null;
        this.size=0;
    }
    
    public void add(T data){
        Node <T> newNode=new Node<>(data);
        if(head==null){
            head=newNode;
        } else{
            Node<T>current=head;
            while(current.next!=null){
                current=current.next;
            }
            current.next=newNode;
        }
        size++;
    }
    
    public void addfirst(T data){
        Node<T>newNode=new Node<>(data);
        newNode.next=head;
        head=newNode;
        size++;
    }
    
    public Node<T> getHead(){
        return head;
    }
    
    public int getSize(){
        return size;
    }
}

