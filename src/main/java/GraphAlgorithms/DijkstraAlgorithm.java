package GraphAlgorithms;

import AdjacencyList.DirectedValuedGraph;
import Nodes.DirectedNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DijkstraAlgorithm {

    // given directed valued graph
    public DirectedValuedGraph graph;

    // source node
    public DirectedNode src;

    public Map<DirectedNode,DirectedNode> pred;

    public Map<DirectedNode,Integer> dist;

    public ArrayList<DirectedNode> nonVisitedYet;

    public Map<DirectedNode,Boolean> areVisited;

    public DijkstraAlgorithm(DirectedValuedGraph graph, DirectedNode src){
        this.graph = graph;
        this.src = graph.getNodes().get(src.getLabel());
        this.pred = new HashMap<DirectedNode,DirectedNode>();
        this.dist = new HashMap<DirectedNode,Integer>();
        this.nonVisitedYet = new ArrayList<DirectedNode>();
        this.areVisited = new HashMap<DirectedNode, Boolean>();
    }

    public void execDijkstraAlgorithm(){
        // Complexity: O(n(n+m))

        // init Dijkstra's tables    O(n)
        for(DirectedNode node : this.graph.getNodes()) { // O(n), with n := this.graph.order
            // O(1) with HashMap & ArrayList
            this.dist.put(node, Integer.MAX_VALUE);
            this.areVisited.put(node,false);
            this.nonVisitedYet.add(node);
        }


        // init zero dist from source to source     O(1)
        this.dist.put(this.src,0);
        this.pred.put(this.src,this.src);
        DirectedNode x = this.src;

        // while there's a remaining non-visited node
        while (! this.nonVisitedYet.isEmpty()){ // O(n(n+m))
            // seek non-visited with min-cost `x` node
            int min = Integer.MAX_VALUE;

            for (DirectedNode y : this.graph.getNodes()){ // O(n)
                if(!this.areVisited.get(y) && this.dist.get(y) < min){
                    x = y;
                    min = this.dist.get(y);
                }
            }

            // x is areVisited, let's remove it from the non-visited nodes
            this.nonVisitedYet.remove(x); // O(n)

            // update non-visited successors of `x`
            if (min < Integer.MAX_VALUE){
                this.areVisited.put(x,true); // O(1)
                for (DirectedNode y : x.getSuccs().keySet()){ // O(d⁺(x))
                    if (!this.areVisited.get(y) && this.dist.get(x) + x.getSuccs().get(y) < this.dist.get(y)){
                        this.dist.put(y, this.dist.get(x) + x.getSuccs().get(y));
                        this.pred.put(y,x);
                    }
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

    public void printDijkstra() throws Exception{
        if (getDist().isEmpty() || getPred().isEmpty())
            throw new Exception("Pred and/or Dist are empty, please run the algorithm first");

        System.out.println("Source entry-point node is: " + this.src);
        System.out.println();

        for (DirectedNode node : this.graph.getNodes()){
            int d = this.getDist().get(node);
            DirectedNode p = this.getPred().get(node);
            System.out.println(node + ": dist = " + d + "   ;    pred = " + p);
        }
    }

    public static void main(String[] args) throws Exception {
        // Graph from course's example
        int A = 0, B=1, C=2, D=3, E=4, F=5, G=6, H=7;
        DirectedValuedGraph graph = new DirectedValuedGraph(new int[8][8]);
        graph.addArc(new DirectedNode(A), new DirectedNode(B),2);
        graph.addArc(new DirectedNode(A), new DirectedNode(C),6);
        graph.addArc(new DirectedNode(B), new DirectedNode(D),1);
        graph.addArc(new DirectedNode(B), new DirectedNode(H),1);
        graph.addArc(new DirectedNode(C), new DirectedNode(B),3);
        graph.addArc(new DirectedNode(C), new DirectedNode(G),2);
        graph.addArc(new DirectedNode(C), new DirectedNode(F),2);
        graph.addArc(new DirectedNode(D), new DirectedNode(C),2);
        graph.addArc(new DirectedNode(D), new DirectedNode(G),6);
        graph.addArc(new DirectedNode(D), new DirectedNode(E),7);
        graph.addArc(new DirectedNode(E), new DirectedNode(B),3);
        graph.addArc(new DirectedNode(E), new DirectedNode(H),2);
        graph.addArc(new DirectedNode(F), new DirectedNode(D),1);
        graph.addArc(new DirectedNode(F), new DirectedNode(E),4);
        graph.addArc(new DirectedNode(G), new DirectedNode(A),1);
        graph.addArc(new DirectedNode(G), new DirectedNode(F),2);
        graph.addArc(new DirectedNode(H), new DirectedNode(F),3);
        System.out.println(graph);

        // Dijkstra's algorithm (with node 0 as source)
        DijkstraAlgorithm instanceDijkstra = new DijkstraAlgorithm(graph,new DirectedNode(A));
        instanceDijkstra.execDijkstraAlgorithm();
        instanceDijkstra.printDijkstra();

    }


}
