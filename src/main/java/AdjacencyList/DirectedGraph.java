package AdjacencyList;

import Abstraction.AbstractListGraph;
import Abstraction.IDirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

import java.util.*;
import java.util.function.BiFunction;

public class DirectedGraph extends AbstractListGraph<DirectedNode> implements IDirectedGraph {

	private static int _DEBBUG =0;
		
    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public DirectedGraph(){
		super();
		this.nodes = new ArrayList<DirectedNode>();
	}

    public DirectedGraph(int[][] matrix) {
        this.order = matrix.length;
        this.nodes = new ArrayList<DirectedNode>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(i, this.makeNode(i));
        }
        for (DirectedNode n : this.getNodes()) {
            for (int j = 0; j < matrix[n.getLabel()].length; j++) {
            	DirectedNode nn = this.getNodes().get(j);
                if (matrix[n.getLabel()][j] != 0) {
                    n.getSuccs().put(nn,0);
                    nn.getPreds().put(n,0);
                    this.m++;
                }
            }
        }
    }

    public DirectedGraph(DirectedGraph g) {
        super();
        this.nodes = new ArrayList<>();
        this.order = g.getNbNodes();
        this.m = g.getNbArcs();
        for(DirectedNode n : g.getNodes()) {
            this.nodes.add(makeNode(n.getLabel()));
        }
        for (DirectedNode n : g.getNodes()) {
        	DirectedNode nn = this.getNodes().get(n.getLabel());
            for (DirectedNode sn : n.getSuccs().keySet()) {
                DirectedNode snn = this.getNodes().get(sn.getLabel());
                nn.getSuccs().put(snn,0);
                snn.getPreds().put(nn,0);
            }
        }

    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    @Override
    public int getNbArcs() {
        return this.m;
    }

    @Override
    public boolean isArc(DirectedNode from, DirectedNode to) {
    	return getNodeOfList(from).getSuccs().containsKey(getNodeOfList(getNodeOfList(to)))
                && getNodeOfList(to).getPreds().containsKey(getNodeOfList(getNodeOfList(from))) ; // might be redundant but did it just in case
    }

    @Override
    public void removeArc(DirectedNode from, DirectedNode to) {
        if(isArc(from,to)){
            this.getNodeOfList(from).getSuccs().remove(getNodeOfList(to));
            this.getNodeOfList(to).getPreds().remove(getNodeOfList(from));
        }
    }

    @Override
    public void addArc(DirectedNode from, DirectedNode to) {
    	if(!isArc(from,to)){
            DirectedNode nFrom = this.getNodes().get(from.getLabel());
            DirectedNode nTo = this.getNodes().get(to.getLabel());
            nFrom.getSuccs().put(nTo,0);
            nTo.getPreds().put(nFrom,0);
        }
    }
    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------

    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends DirectedNode
     */
    @Override
    public DirectedNode makeNode(int label) {
        return new DirectedNode(label);
    }

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public DirectedNode getNodeOfList(DirectedNode src) {
        return this.getNodes().get(src.getLabel());
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[order][order];
        for (int i = 0; i < order; i++) {
            for (DirectedNode j : nodes.get(i).getSuccs().keySet()) {
                int IndSucc = j.getLabel();
                matrix[i][IndSucc] = 1;
            }
        }
        return matrix;
    }

    @Override
    public IDirectedGraph computeInverse() {

        // start with a shallow copy of this graph
        DirectedGraph g = new DirectedGraph(this);

        // all arcs like (srcNode, succNode) are reverted as (succNode, srcNode)
        for (DirectedNode srcNode : this.getNodes()){ // avoid concurrent modification, so we read the current "this" graph
            for (DirectedNode succNode : srcNode.getSuccs().keySet()){
                g.addArc(succNode, srcNode);
                g.removeArc(srcNode, succNode);
            }
        }
        return g;

        /* alternative method: transposing the matrix. First way should be faster in small graphs
        // transpose original matrix to inverse all the arcs
        int[][] mat = this.toAdjacencyMatrix();
        int[][] transposedMatrix = new int[mat.length][mat.length];
        for (int i=0; i< mat.length; i++){
            for (int j=0; j< mat.length; j++){
                transposedMatrix[i][j] = mat[j][i];
            }
        }
        return new DirectedGraph(transposedMatrix);

         */
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(DirectedNode n : nodes){
            s.append("successors of ").append(n).append(" : ");
            for(DirectedNode sn : n.getSuccs().keySet()){
                s.append(sn).append(" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        GraphTools.afficherMatrix(Matrix);
        DirectedGraph al = new DirectedGraph(Matrix);
        System.out.println(al);


        /* check if (2,5) is an arc */
        System.out.println("\n isArc((2,5)): " + al.isArc(new DirectedNode(2), new DirectedNode(5)));

        /* Get `2`'s successors */
        System.out.println("N = "+al.getNbNodes()+ "\n M = "+al.getNbArcs());
        Set<DirectedNode> t2 = al.getNodeOfList(new DirectedNode(2)).getSuccs().keySet();
        System.out.println("2's successors: ");
        for (DirectedNode node : t2) {
            System.out.print(node.getLabel() + ", ");
        }

        System.out.println();

        /* Add the arc (2,5) */
        System.out.println("\naddArc((2,5)):");
        /* Adding the arc (2,5) (this class implements a simple graph) */
        al.addArc(new DirectedNode(2), new DirectedNode(5));

        GraphTools.afficherMatrix(al.toAdjacencyMatrix());
        System.out.println("\n"+al);
        System.out.println("\nisArc((2,5)):" + al.isArc(new DirectedNode(2), new DirectedNode(5)));

        /* check if (5,2) is an arc */
        System.out.println("\n isArc((5,2)): " + al.isArc(new DirectedNode(5), new DirectedNode(2)));


        /* Removing once the arc (2,5) */
        System.out.println("\nremoveArc((2,5)):");
        al.removeArc(new DirectedNode(2), new DirectedNode(5));
        System.out.println("\n"+al);
        GraphTools.afficherMatrix(al.toAdjacencyMatrix());

        /* Arc testing with a non-existing one */
        System.out.println("\n isArc((0,1)): " + al.isArc(new DirectedNode(0),new DirectedNode(1)));

        /* Removing that non-existing arc */
        System.out.println("\n removeArc((0,1)): ");
        al.removeArc(new DirectedNode(0),new DirectedNode(1));
        System.out.println(al);

        System.out.println();

        /* Invert the graph */
        System.out.println("\n Graph inverse:");
        IDirectedGraph invertAl = al.computeInverse();
        GraphTools.afficherMatrix(invertAl.toAdjacencyMatrix());
        System.out.println("\n"+invertAl);

        
    }
}
