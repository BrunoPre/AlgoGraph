package AdjacencyMatrix;

import Abstraction.AbstractMatrixGraph;
import GraphAlgorithms.GraphTools;
import Nodes.AbstractNode;
import Nodes.DirectedNode;
import Abstraction.IDirectedGraph;
import Nodes.UndirectedNode;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the directed graphs structured by an adjacency matrix.
 * It is possible to have simple and multiple graph
 */
public class AdjacencyMatrixDirectedGraph extends AbstractMatrixGraph<DirectedNode> implements IDirectedGraph {

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixDirectedGraph() {
		super();
	}

	public AdjacencyMatrixDirectedGraph(int[][] M) {
		this.order = M.length;
		this.matrix = new int[this.order][this.order];
		for(int i = 0; i<this.order; i++){
			for(int j = 0; j<this.order; j++){
				this.matrix[i][j] = M[i][j];
				this.m += M[i][j];
			}
		}
	}

	public AdjacencyMatrixDirectedGraph(IDirectedGraph g) {
		this.order = g.getNbNodes();
		this.m = g.getNbArcs();
		this.matrix = g.toAdjacencyMatrix();
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------

	@Override
	public int getNbArcs() {
		return this.m;
	}

	public List<Integer> getSuccessors(DirectedNode x) {
		List<Integer> v = new ArrayList<Integer>();
		for(int i =0;i<this.matrix[x.getLabel()].length;i++){
			if(this.matrix[x.getLabel()][i]>0){
				v.add(i);
			}
		}
		return v;
	}

	public List<Integer> getPredecessors(DirectedNode x) {
		List<Integer> v = new ArrayList<Integer>();
		for(int i =0;i<this.matrix.length;i++){
			if(this.matrix[i][x.getLabel()]>0){
				v.add(i);
			}
		}
		return v;
	}
	
	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------		
	
	@Override
	public boolean isArc(DirectedNode from, DirectedNode to) {
		int labelFrom = from.getLabel();
		int labelTo = to.getLabel();
		// just in case, if labels are out-of-bounds
		if (labelFrom > order || labelTo > order) {
			return false;
		}
		int xy = this.getMatrix()[labelFrom][labelTo]; // sufficient since the matrix is symmetric
		return xy != 0; // xy can be any strictly positive integer
	}

	/**
	 * removes the arc (from,to) if there exists at least one between these nodes in the graph.
	 */
	@Override
	public void removeArc(DirectedNode from, DirectedNode to) {
		if(isArc(from,to)) {
			this.matrix[from.getLabel()][to.getLabel()]--;
		}
		/* DEBUG */
		/*
		else {
			System.out.println("Arc ("+from.getLabel()+","+to.getLabel()+") does not exist");
		}
		 */

	}

	/**
	 * Adds the arc (from,to). we allow multiple graph.
	 */
	@Override
	public void addArc(DirectedNode from, DirectedNode to) {
		this.matrix[from.getLabel()][to.getLabel()]++;
	}


	/**
	 * @return the adjacency matrix representation int[][] of the graph
	 */
	public int[][] toAdjacencyMatrix() {
		return this.matrix;
	}

	@Override
	public IDirectedGraph computeInverse() {
		AdjacencyMatrixDirectedGraph am = new AdjacencyMatrixDirectedGraph(new int[this.order][this.order]);

		// swap all nodes
		for (int x=0; x<this.order; x++){
			for (int y=0; y<this.order; y++){
				am.matrix[y][x] = this.matrix[x][y];
				am.matrix[x][y] = this.matrix[y][x];
			}
		}

		return am;
	}

	@Override
	public String toString(){
		StringBuilder s = new StringBuilder("Adjacency Matrix: \n");
		for (int[] ints : matrix) {
			for (int anInt : ints) {
				s.append(anInt).append(" ");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] matrix2 = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
		AdjacencyMatrixDirectedGraph am = new AdjacencyMatrixDirectedGraph(matrix2);
		System.out.println(am);
		List<Integer> t = am.getSuccessors(new DirectedNode(1));
		for (Integer integer : t) {
			System.out.print(integer + ", ");
		}
		System.out.println();
		List<Integer> t2 = am.getPredecessors(new DirectedNode(2));
		for (Integer integer : t2) {
			System.out.print(integer + ", ");
		}


		/* Adding three times the arc (2,5) --> result should be 1+3=4 */
		System.out.println("\nAdding 3 times the arc (2,5):");
		for(int i = 0; i<3;i++)
			am.addArc(new DirectedNode(2), new DirectedNode(5));

		System.out.println("\n"+am);

		/* Removing once the arc (2,5) --> result should be 4-1=3 */
		System.out.println("\nRemoving only once the arc (2,5):");
		am.removeArc(new DirectedNode(2), new DirectedNode(5));
		System.out.println(am);


		/* Edge testing with a non-existing one */
		System.out.println("\n isArc((0,1)): " + am.isArc(new DirectedNode(0),new DirectedNode(1)));

		/* Removing that non-existing edge */
		System.out.println("\nTrying to remove the arc (0,1):");
		am.removeArc(new DirectedNode(0),new DirectedNode(1));
		System.out.println("\n" + am);

		/* Reversing the graph */
		System.out.println("\n Graph inverse is:");
		IDirectedGraph amNew = am.computeInverse();
		System.out.println(amNew);


	}
}
