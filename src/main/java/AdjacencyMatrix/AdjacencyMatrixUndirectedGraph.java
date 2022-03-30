package AdjacencyMatrix;

import Abstraction.AbstractMatrixGraph;
import GraphAlgorithms.GraphTools;
import Nodes.AbstractNode;
import Nodes.UndirectedNode;
import Abstraction.IUndirectedGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents the undirected graphs structured by an adjacency matrix.
 * It is possible to have simple and multiple graph
 */
public class AdjacencyMatrixUndirectedGraph extends AbstractMatrixGraph<UndirectedNode> implements IUndirectedGraph {
	
	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 
	
	public AdjacencyMatrixUndirectedGraph() {
		super();
	}
	
	public AdjacencyMatrixUndirectedGraph(int[][] mat) {
		order=mat.length;
		matrix = new int[order][order];
		for(int i = 0; i<this.order; i++){
			for(int j = i; j<this.order; j++){
				this.matrix[i][j] = mat[i][j];
				this.matrix[j][i] = mat[i][j];
				this.m += mat[i][j];
			}
		}	
	}
	
	public AdjacencyMatrixUndirectedGraph(IUndirectedGraph g) {
		this.order = g.getNbNodes(); 				
		this.m = g.getNbEdges(); 				
		this.matrix = g.toAdjacencyMatrix(); 
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------

	@Override
	public int getNbEdges() {
		return this.m;
	}

	public List<Integer> getNeighbours(AbstractNode x) {
		List<Integer> l = new ArrayList<>();
		for(int i = 0; i<matrix[x.getLabel()].length; i++){
			if(matrix[x.getLabel()][i]>0){
				l.add(i);
			}
		}
		return l;
	}
	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------		
	
	@Override
	public boolean isEdge(UndirectedNode x, UndirectedNode y) {
		int labelX = x.getLabel();
		int labelY = y.getLabel();
		// just in case, if labels are out-of-bounds
		if (labelX > order || labelY > order) {
			return false;
		}
		int xy = this.getMatrix()[labelX][labelY]; // sufficient since the matrix is symmetric
		return xy != 0; // xy can be any strictly positive integer
	}
	
	/**
     * removes the edge (x,y) if there exists at least one between these nodes in the graph.
     */
	@Override
	public void removeEdge(UndirectedNode x, UndirectedNode y) {
		if(isEdge(x,y)) {
			this.matrix[x.getLabel()][y.getLabel()]--;
			this.matrix[y.getLabel()][x.getLabel()]--;
		}
		/* DEBUG */
		/*
		else {
			System.out.println("Edge ("+x.getLabel()+","+y.getLabel()+") does not exist");
		}
		 */
	}

	/**
     * adds the edge (x,y), we allow the multi-graph. If we have three edges between two nodes, we have the value 3.
     */
	@Override
	public void addEdge(UndirectedNode x, UndirectedNode y) {
		this.matrix[x.getLabel()][y.getLabel()]++;
	}

	
	/**
     * @return the adjacency matrix representation int[][] of the graph
     */
	public int[][] toAdjacencyMatrix() {
		return this.matrix;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("Adjacency Matrix: \n");
		for (int[] ints : this.matrix) {
			for (int anInt : ints) {
				s.append(anInt).append(" ");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		/* Instantiate a undirected graph */
		int[][] mat2 = GraphTools.generateGraphData(10, 35, false, true, false, 100001);
		//GraphTools.afficherMatrix(mat2); // print the matrix as an int tab
		AdjacencyMatrixUndirectedGraph am = new AdjacencyMatrixUndirectedGraph(mat2);
		System.out.println(am);			// print the graph as-is (toString())

		/* Edge testing: (2,5) (or (5,2)) */
		System.out.println("\n isEdge : " + am.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
		
		/* Get `2`'s neighbours */
		System.out.println("N = "+am.getNbNodes()+ "\n M = "+am.getNbEdges());
		List<Integer> t2 = am.getNeighbours(new UndirectedNode(2));
		for (Integer integer : t2) {
			System.out.print(integer + ", ");
		}
		

		/* Adding three times the edge (2,5) --> result should be 1+3=4 */
		for(int i = 0; i<3;i++)
			am.addEdge(new UndirectedNode(2), new UndirectedNode(5));

		System.out.println("\n"+am);

		/* Removing once the edge (2,5) --> result should be 4-1=3 */
		am.removeEdge(new UndirectedNode(2), new UndirectedNode(5));
		System.out.println(am);


		/* Edge testing with a non-existing one */
		System.out.println("\n isEdge((0,1)): " + am.isEdge(new UndirectedNode(0),new UndirectedNode(1)));

		/* Removing that non-existing edge */
		am.removeEdge(new UndirectedNode(0),new UndirectedNode(1));
		System.out.println(am);
		 
	}
}
