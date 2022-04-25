package GraphAlgorithms;

import AdjacencyList.DirectedValuedGraph;
import Nodes.DirectedNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BellmanAlgorithm {

    // given directed valued graph
    public DirectedValuedGraph graph;

    // source node
    public DirectedNode src;

    public Map<DirectedNode,DirectedNode> pred;

    public Map<DirectedNode,Integer> dist;

    public BellmanAlgorithm(DirectedValuedGraph graph, DirectedNode src){
        this.graph = graph;
        this.src = graph.getNodes().get(src.getLabel());
        this.pred = new HashMap<DirectedNode,DirectedNode>();
        this.dist = new HashMap<DirectedNode,Integer>();
    }

    public void execBellmanAlgorithm(){
        // Complexity: O(n*m) (n,m < n*m for all n,m integers)

        // init Bellman's tables    O(n)
        for(DirectedNode node : this.graph.getNodes()) { // O(n), with n := this.graph.order
            this.dist.put(node, Integer.MAX_VALUE); // O(1) with HashMap
        }

        // init zero dist from source to source     O(1)
        this.dist.put(this.src,0);
        this.pred.put(this.src,this.src);


        // n-1 iterations   O(n*m)
        for (int k=1 ; k<=this.graph.getNbNodes()-1 ; k++){
            // come across all arcs (u,v) of the graph  O(m), with m the number of arcs
            for (DirectedNode u : this.graph.getNodes()){
                for (DirectedNode v : u.getSuccs().keySet()){
                    // values from previous iteration
                    Integer dv = this.dist.get(v);
                    Integer du = this.dist.get(u);
                    Integer weight_uv = u.getSuccs().get(v);
                    // if there's an improving distance, update it      O(1)
                    if (dv > du + weight_uv){
                        this.dist.put(v, du + weight_uv);
                        this.pred.put(v, u);
                    }
                }
            }
        }

        // check it there's a negative-weight cycle     O(m)
        for (DirectedNode u : this.graph.getNodes()){
            for (DirectedNode v : u.getSuccs().keySet()){
                // values from previous iteration
                Integer dv = this.dist.get(v);
                Integer du = this.dist.get(u);
                Integer weight_uv = u.getSuccs().get(v);
                // if there's an improving distance, update it
                if (dv > du + weight_uv){
                    System.out.println("There's a negative weight cycle!");
                }
            }
        }

    }

    public Map<DirectedNode,DirectedNode> getPred(){
        return this.pred;
    }

    public Map<DirectedNode,Integer> getDist() {
        return this.dist;
    }

    public void printBellman() throws Exception{
        if (getDist().isEmpty() || getPred().isEmpty())
            throw new Exception("Pred and/or Dist are empty, please run the algorithm first");

        System.out.println("Source entry-point node is: " + this.src);
        System.out.println();

        for (DirectedNode node : this.graph.getNodes()){
            int d = this.getDist().get(node);
            DirectedNode p = this.getPred().get(node);
            System.out.println(node.toString() + ": dist = " + d + "   ;    pred = " + p.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        // graph from course's example
        DirectedValuedGraph graph = new DirectedValuedGraph(new int[7][7]);
        graph.addArc(new DirectedNode(0), new DirectedNode(1),3);
        graph.addArc(new DirectedNode(0), new DirectedNode(2),1);
        graph.addArc(new DirectedNode(1), new DirectedNode(4),-2);
        graph.addArc(new DirectedNode(1), new DirectedNode(5),1);
        graph.addArc(new DirectedNode(2), new DirectedNode(4),-2);
        graph.addArc(new DirectedNode(2), new DirectedNode(3),-2);
        graph.addArc(new DirectedNode(3), new DirectedNode(5),2);
        graph.addArc(new DirectedNode(4), new DirectedNode(5),6);
        graph.addArc(new DirectedNode(4), new DirectedNode(6),4);
        graph.addArc(new DirectedNode(5), new DirectedNode(2),3);
        graph.addArc(new DirectedNode(5), new DirectedNode(6),-3);
        graph.addArc(new DirectedNode(6), new DirectedNode(1),2);
        graph.addArc(new DirectedNode(6), new DirectedNode(0),4);

        // Bellman's algorithm (with node 0 as source)
        BellmanAlgorithm instanceBellman = new BellmanAlgorithm(graph,new DirectedNode(0));
        instanceBellman.execBellmanAlgorithm();
        instanceBellman.printBellman();

    }


}
