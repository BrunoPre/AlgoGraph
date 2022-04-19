package GraphAlgorithms;

import java.util.ArrayList;
import java.util.List;

import Collection.Triple;
import Nodes.UndirectedNode;

public class BinaryHeapEdge {

	/**
	 * A list structure for a faster management of the heap by indexing
	 * 
	 */
	private  List<Triple<UndirectedNode,UndirectedNode,Integer>> binh;

    public BinaryHeapEdge() {
        this.binh = new ArrayList<>();
    }

    public boolean isEmpty() {
        return binh.isEmpty();
    }

    /**
	 * Insert a new edge in the binary heap
	 * 
	 * @param from one node of the edge
	 * @param to one node of the edge
	 * @param val the edge weight
	 */
    public void insert(UndirectedNode from, UndirectedNode to, int val) {

		// 0. init triple `element`
		Triple<UndirectedNode,UndirectedNode,Integer> element = new Triple<>(from, to, val);

		// 1. Add `element` to the first available leaf
		// append `element` at the end of the array
		int i = this.binh.size();
		this.binh.add(element);

		// 2. Percolate-up if needed
		while (i != 0 &&
				this.binh.get(this.getParentIndex(i)).getThird() >
						this.binh.get(i).getThird()) { // worst case in complexity: O(log_2(this.pos)), since a heap is a binary tree
			this.swap(i, this.getParentIndex(i)); // complexity in O(1)
			i = this.getParentIndex(i); // same as well
		}

	}

	private int getParentIndex(int childIndex) {
		return childIndex != 0 ? (childIndex-1)/2 : 0; // quotient floor-rounded by default (between integers)
	}


	/**
	 * Removes the root edge in the binary heap, and swap the edges to keep a valid binary heap
	 *
	 * @return the edge with the minimal value (root of the binary heap)
	 *
	 */
    public Triple<UndirectedNode,UndirectedNode,Integer> remove() {
		if (isEmpty())
			return null;

		// 0. save root to return it at the end
		Triple<UndirectedNode, UndirectedNode, Integer> root = this.binh.get(0);

		// 1. pop root and replace it with the last-used leaf
		Triple<UndirectedNode, UndirectedNode, Integer> lastUsedLeaf = this.binh.remove(this.binh.size()-1);
		this.binh.remove(0);
		this.binh.add(0, lastUsedLeaf);
		int i = 0;

		// 2. swap downwardly (percolate-down)
		while (!isLeaf(i)){ // navigating downwards is done in O(log_2(this.pos)), since a heap is a binary tree
			// if there's a left child but no right child
			// --> compare element with left child
			if (2*i+2 >= this.binh.size()) {
				if (this.binh.get(i).getThird() > this.binh.get(2 * i + 1).getThird()) {
					this.swap(i, 2 * i + 1);
					i = 2 * i + 1;
				}
			}
			// usual case
			else {
				if (this.binh.get(i).getThird() > this.binh.get(2 * i + 1).getThird() ||
						this.binh.get(i).getThird() > this.binh.get(2 * i + 2).getThird()) {
                /* in order to keep the binary heap's order property true,
                    picking the smallest child is mandatory */
					if (this.binh.get(2 * i + 1).getThird() < this.binh.get(2 * i + 2).getThird()) {
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

		return root;
    }
    

    /**
	 * From an edge indexed by src, find the child having the least weight and return it
	 * 
	 * @param src an index of the list edges
	 * @return the index of the child edge with the least weight
	 */
    private int getBestChildPos(int src) {
        if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
            return Integer.MAX_VALUE;
        } else {
			int lastIndex = binh.size()-1;
			int leftChildValue, rightChildValue;
			leftChildValue = 2*src+1 <= lastIndex ? this.binh.get(2 * src + 1).getThird() : Integer.MAX_VALUE;
			rightChildValue = 2*src+2 <= lastIndex ? this.binh.get(2 * src + 2).getThird() : Integer.MAX_VALUE;
			return leftChildValue < rightChildValue ? 2*src+1 : 2*src+2;
        }
    }

    private boolean isLeaf(int src) {
		// if no children, then it's a leaf
		return 2* src +1 >= this.binh.size() && 2* src +2 >= this.binh.size();
    }

    
    /**
	 * Swap two edges in the binary heap
	 * 
	 * @param father an index of the list edges
	 * @param child an index of the list edges
	 */
    private void swap(int father, int child) {         
    	Triple<UndirectedNode,UndirectedNode,Integer> temp = new Triple<>(binh.get(father).getFirst(), binh.get(father).getSecond(), binh.get(father).getThird());
    	binh.get(father).setTriple(binh.get(child));
    	binh.get(child).setTriple(temp);
    }

    
    /**
	 * Create the string of the visualisation of a binary heap
	 * 
	 * @return the string of the binary heap
	 */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Triple<UndirectedNode,UndirectedNode,Integer> no: binh) {
            s.append(no).append(", ");
        }
        return s.toString();
    }
    
    
    private String space(int x) {
		StringBuilder res = new StringBuilder();
		for (int i=0; i<x; i++) {
			res.append(" ");
		}
		return res.toString();
	}
	
	/**
	 * Print a nice visualisation of the binary heap as a hierarchy tree
	 * 
	 */	
	public void lovelyPrinting(){
		int nodeWidth = this.binh.get(0).toString().length();
		int depth = 1+(int)(Math.log(this.binh.size())/Math.log(2));
		int index=0;
		
		for(int h = 1; h<=depth; h++){
			int left = ((int) (Math.pow(2, depth-h-1)))*nodeWidth - nodeWidth/2;
			int between = ((int) (Math.pow(2, depth-h))-1)*nodeWidth;
			int i =0;
			System.out.print(space(left));
			while(i<Math.pow(2, h-1) && index<binh.size()){
				System.out.print(binh.get(index) + space(between));
				index++;
				i++;
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	// ------------------------------------
    // 					TEST
	// ------------------------------------

	/**
	 * Recursive test to check the validity of the binary heap
	 * 
	 * @return a boolean equal to True if the binary tree is compact from left to right
	 * 
	 */
    private boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
    	int lastIndex = binh.size()-1; 
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= lastIndex) {
                return binh.get(left).getThird() >= binh.get(root).getThird() && testRec(left);
            } else {
                return binh.get(left).getThird() >= binh.get(root).getThird() && testRec(left)
                    && binh.get(right).getThird() >= binh.get(root).getThird() && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeapEdge jarjarBin = new BinaryHeapEdge();
        System.out.println(jarjarBin.isEmpty()+"\n");
        int k = 10;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));                        
            jarjarBin.insert(new UndirectedNode(k), new UndirectedNode(k+30), rand);            
            k--;
        }
        // TODO: Complete


		// print the heap's array
		//System.out.println("\n" + jarjarBin);
		jarjarBin.lovelyPrinting();
		//System.out.println("this.nodes: " + Arrays.toString(jarjarBin.binh)); // debug: print the whole array

		// test if the heap's valid
		System.out.println("Is the heap valid : " + jarjarBin.test());

		// remove the min value
		System.out.println();
		System.out.println("\n--- Remove the min value ---");
		Triple<UndirectedNode, UndirectedNode, Integer> lastElem = jarjarBin.remove();
		System.out.println("lastElem = " + lastElem);
		//System.out.println("\n" + jarjarBin);
		jarjarBin.lovelyPrinting();
		System.out.println("Is the heap valid : " + jarjarBin.test());

		// MOCK: force append negative infinity to test heap's validity
		System.out.println("\nmock test");
		jarjarBin.binh.add(new Triple<>(null,null, Integer.MIN_VALUE));
		System.out.println("size = " + jarjarBin.binh.size());
		System.out.println("parent = " +jarjarBin.binh.get(jarjarBin.getParentIndex(jarjarBin.binh.size())-1));
		//System.out.println("\n" + jarjarBin);
		jarjarBin.lovelyPrinting();
		System.out.println("Is the heap valid : " + jarjarBin.test());
    }

}

