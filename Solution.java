import java.util.*;
public class Solution<Key extends Comparable<Key>, Value> {
    private Node root;             // root of BST
    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }
    /**
     * Initializes an empty symbol table.
     */
    public Solution() {
        root = null;
    }
    /**
     * Returns true if this symbol table is empty.
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        if(size()==0){
            return true;
        }
        return false;
        }
    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
       return size(root);
    }
    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
       if(x == null){
            return 0;
        }
        else{
            return x.size;
        }
    }
    /**
     * Does this symbol table contain the given key?
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
    Node curNode = root;
       if(key == null){
            throw new IllegalArgumentException("There is not key conatin in the root");
       }
       else{
            while(curNode.key != key){
                int cmp = key.compareTo(curNode.key);
                if(cmp < 0){
                    curNode = curNode.left;
                }
                else{
                    curNode = curNode.right;
                }
            }
            return true;
       }
    }
    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
         Node x = root;
        while (x != null) {
            int res = key.compareTo(x.key);
            if      (res < 0) x = x.left;
            else if (res > 0) x = x.right;
            else return x.val;
        }
        return null;    
    }
    /*private Value get(Node x, Key key) {
    }*/
    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("Key Cannot be Null");
        if (root == null) 
        {
            root = new Node(key, val, 1);
            return;
        }
        if(value(root, key, val)) 
            return;
        Node node = null, x = root, z = new Node(key, val, 1);
        while (x != null) {
            node = x;
            int temp = key.compareTo(node.key);
            if(temp < 0){
                node.size++;
                x=x.left;
            }
            if (temp > 0){
                node.size++;
                x=x.right;
            }
        }
        int temp = key.compareTo(node.key);
        if (temp < 0){
            node.left  = z;
        } 
        else{
            node.right = z;
        }         
        node.size = 1+size(node.left)+size(node.right);
    }
    private boolean value(Node x, Key key, Value val){
        while(x!=null){
            int temp = key.compareTo(x.key);
            if(temp<0){
                x=x.left;
            } 
            else if(temp>0){
                x=x.right;
            }
            else{
                x.val = val;
                return true;
            }
        }
        return false;
    }
    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
      if(isEmpty()){
        throw new NoSuchElementException("There is no element in the tree");
       }
       else{
            Node curNode = root;
            while(curNode.left != null){
                curNode = curNode.left;
            }
            return curNode.key;
       }
    } 
    private Node min(Node x) { 
        if(x.left == null){
            return x; 
        }
        for(x=x;x!=null;x=x.left){
            if(x.left == null){
                return x;
            }
        }
        return x;
    } 
    /**
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     *
     * @param  key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(Key key) {
         if (key == null) throw new IllegalArgumentException("argument to floor() is null");
         if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
         Node x = floor(root, key);
         if (x == null) throw new NoSuchElementException("argument to floor() is too small");
         else return x.key;
     } 
    private Node floor(Node x, Key key) {
         if (x == null) return null;
         int cmp = key.compareTo(x.key);
         if (cmp == 0) return x;
         if (cmp <  0) return floor(x.left, key);
         Node t = floor(x.right, key); 
         if (t != null) return t;
         else return x; 
     } 
    /**
     * Return the key in the symbol table whose rank is {@code k}.
     * This is the (k+1)st smallest key in the symbol table.
     *
     * @param  k the order statistic
     * @return the key in the symbol table of rank {@code k}
     * @throws IllegalArgumentException unless {@code k} is between 0 and
     *        <em>n</em>–1
     */
    /**
     * Returns all keys in the symbol table in the given range,
     * as an {@code Iterable}.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return all keys in the symbol table between {@code lo} 
     *         (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new LinkedList<Key>();
        keys(root, queue, lo, hi);  
        return queue;
    } 
    private void keys(Node x,Queue<Key> queue, Key lo, Key hi) { 
        if (x == null)  
        return;  
        Node cur = x;
        int com = lo.compareTo(cur.key);
        int cmp = hi.compareTo(cur.key);
        while (cur != null) {  
    
            if (cur.left == null)  
            {   
                if (com <= 0 && cmp >= 0)  queue.offer(cur.key);    
                cur = cur.right;  
            }  
            else {  
                Node pre = cur.left;    
                while (pre.right != null && pre.right != cur)  
                    pre = pre.right;  
                if (pre.right == null)  
                {  
                    pre.right = cur;  
                    cur = cur.left;  
                }  
                else {  
                    pre.right = null;    
                    if (com <= 0 && cmp >= 0)  queue.offer(cur.key);   
                    cur = cur.right;  
                }  
            }
        }
    } 
    public static void main(String[] args) { 

        Solution<String, Integer> obj = new Solution<String, Integer>();

        obj.put("ABDUL",1);
        System.out.println(obj.get("ABDUL"));

        obj.put("HRITHIK",2);
        obj.put("SAI",3);
        obj.put("SAMAL",6);
        System.out.println(obj.get("SAI"));

        obj.put("TASHI",4);
        System.out.println(obj.size());
        System.out.println(obj.min());
        System.out.println(obj.floor("HRITHIK"));
        System.out.println(obj.floor("HAHA"));

        for (String s : obj.keys("ABDUL","TASHI"))
            System.out.print(s+" ");
        System.out.println();

        obj.put("CHIMI",5);
        obj.put("SAMAL",4);
        System.out.println(obj.get("SAMAL"));
        obj.put("NIMA",7);
        System.out.println(obj.size());
        System.out.println(obj.get("CHIMI"));
        System.out.println(obj.floor("CHIMI"));
        obj.put("SONAM",8);
        for (String s : obj.keys("ABDUL","TASHI"))
            System.out.print(s+" ");
        System.out.println();
    }
}