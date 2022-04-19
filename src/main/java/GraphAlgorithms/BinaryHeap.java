package GraphAlgorithms;


import java.util.Arrays;

public class BinaryHeap {

    private int[] nodes;
    private int pos;

    public BinaryHeap() {
        this.nodes = new int[32];
        for (int i = 0; i < nodes.length; i++) {
            this.nodes[i] = Integer.MAX_VALUE;
        }
        this.pos = 0;
    }

    public void resize() { // linear complexity
        int[] tab = new int[this.nodes.length + 32];
        for (int i = 0; i < nodes.length; i++) {
            tab[i] = Integer.MAX_VALUE;
        }
        System.arraycopy(this.nodes, 0, tab, 0, this.nodes.length);
        this.nodes = tab;
    }

    public boolean isEmpty() {
        return pos == 0;
    }


    /**
     * Inserts an element into the heap
     * @implNote Complexity: O(log_2(this.pos)) (check the detail in the code comments)
     *
     */
    public void insert(int element) {

        // if this tree is full, it must be resized
        if (this.pos == this.nodes.length){
            this.resize(); // complexity in O(this.pos)
        }


        // 1. Add `element` to the first available leaf
        // append `element` at the end of the array
        int i = this.pos;
        this.pos++;
        this.nodes[i] = element;

        // 2. Percolate-up if needed
        while (i != 0 && this.nodes[this.getParentIndex(i)] > this.nodes[i]) { // worst case in complexity: O(log_2(this.pos)), since a heap is a binary tree
            this.swap(i, this.getParentIndex(i)); // complexity in O(1)
            i = this.getParentIndex(i); // same as well
        }

    }

    /**
     * Removes the smallest value of the heap
     * @implNote Complexity: O(log_2(this.pos)) (check the detail in the code comments)
     *
     */
    public int remove() {
    	return this.removeMin();
    }

    /**
     * Removes the smallest value of the heap
     * @implNote Complexity: O(log_2(this.pos)) (check the detail in the code comments)
     *
     */
    public int removeMin(){
        if (isEmpty())
            return Integer.MAX_VALUE;

        // 1. swap root and last-used leaf (i.e. the (this.pos-1)-th element in the array)
        this.nodes[0] = this.nodes[this.pos-1];
        this.nodes[this.pos-1] = Integer.MAX_VALUE;
        this.pos--;
        int i = 0;

        // 2. swap downwardly (percolate-down)
        while (!isLeaf(i)){ // navigating downwards is done in O(log_2(this.pos)), since a heap is a binary tree
            /* // not necessary since the loop condition covers this case as well
            // if no left child, then no right child
            if (2*i+1 > this.pos)
                break;
            */
            // if there's a left child but no right child
            // --> compare element with left child
            if (2*i+2 >= this.pos) {
                if (this.nodes[i] > this.nodes[2 * i + 1]) {
                    this.swap(i, 2 * i + 1);
                    i = 2 * i + 1;
                }
            }
            // usual case
            else {
                if (this.nodes[i] > this.nodes[2*i+1] || this.nodes[i] > this.nodes[2*i+2]) {
                    /* in order to keep the binary heap's order property true,
                        picking the smallest child is mandatory */
                    if (this.nodes[2 * i + 1] < this.nodes[2 * i + 2]) {
                        this.swap(i, 2 * i + 1);
                        i = 2 * i + 1;
                    } else {
                        this.swap(i, 2 * i + 2);
                        i = 2 * i + 2;
                    }
                }
                // if element is well-placed, we stop here
                else {
                    break;
                }
            }
        }
        return i;
    }


    /**
     * Get a child's parent index
     *
     * @returns the parent's index
     *
     */
    private int getParentIndex(int childIndex){
        return childIndex != 0 ? (childIndex-1)/2 : 0; // quotient floor-rounded by default (between integers)
    }

    
    /**
	 * Test if the node is a leaf in the binary heap
	 *
     * @param src is the index of the node in the array
	 * @returns true if it's a leaf or false else
	 * 
	 */	
    private boolean isLeaf(int src) {
        // if no children, then it's a leaf
        return 2* src +1 >= this.pos && 2* src +2 >= this.pos;
    }

    /**
     * Swaps given indexes of a father and one of its child
     *
     * @param father
     * @param child
     * @implNote Child becomes father, and inversely
     *
     */
    private void swap(int father, int child) {
        int temp = nodes[father];
        nodes[father] = nodes[child];
        nodes[child] = temp;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < pos; i++) {
            s.append(nodes[i]).append(", ");
        }
        return s.toString();
    }

    /**
	 * Recursive test to check the validity of the binary heap
	 * 
	 * @returns a boolean equal to True if the binary tree is compact from left to right
	 * 
	 */
    public boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= pos) { // if no right child node -> check left side
                return nodes[left] >= nodes[root] && testRec(left);
            } else { // check both sides
                return nodes[left] >= nodes[root] && testRec(left) && nodes[right] >= nodes[root] && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        // init a random binary heap
        BinaryHeap jarjarBin = new BinaryHeap();
        System.out.println("is it empty: " + jarjarBin.isEmpty()+"\n");
        int k = 20;
        int m = k;
        int min = 2;
        int max = 20;

        // fill and print inserted values
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));
            System.out.print("insert " + rand + "  ");
            jarjarBin.insert(rand);            
            k--;
        }

        // print the heap's array
        System.out.println("\n" + jarjarBin);
        //System.out.println("this.nodes: " + Arrays.toString(jarjarBin.nodes)); // debug: print the whole array

        // test if the heap's valid
        System.out.println("Is the heap valid : " + jarjarBin.test());

        // remove the min value
        System.out.println();
        System.out.println("\n--- Remove the min value ---");
        int indexLastElem = jarjarBin.removeMin();
        System.out.println("indexLastElem = " + indexLastElem);
        System.out.println("\n" + jarjarBin);
        System.out.println("Is the heap valid : " + jarjarBin.test());

        // MOCK: force append negative infinity to test heap's validity
        System.out.println("\nmock test");
        jarjarBin.nodes[jarjarBin.pos] = Integer.MIN_VALUE;
        jarjarBin.pos++;
        System.out.println("pos = " + jarjarBin.pos);
        System.out.println("parent = " +jarjarBin.nodes[jarjarBin.getParentIndex(jarjarBin.pos-1)]);
        System.out.println("\n" + jarjarBin);
        System.out.println("Is the heap valid : " + jarjarBin.test());
    }

}
