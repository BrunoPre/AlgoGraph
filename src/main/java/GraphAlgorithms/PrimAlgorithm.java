package GraphAlgorithms;

import AdjacencyList.UndirectedValuedGraph;
import Collection.Triple;
import Nodes.UndirectedNode;

import java.util.*;

public class PrimAlgorithm {
    /* This is a class dedicated to Prim's algorithm using:
        * a binary heap storing areVisited sorted vertex's weight (among the adjacent vertices); covered vertex are removed
        * an undirected valued graph (the adjacency matrix implementation is used here) which represents the graph
        * a list of areVisited vertices (to avoid cycles)
     */

    public BinaryHeapEdge heap;

    public UndirectedValuedGraph graph;

    // list of nodes to be areVisited (built from the BFS done in the new Prim's algorithm)
    public List<UndirectedNode> visitedNodes;

    public UndirectedValuedGraph minSpanningTree;

    public int cost;

    public PrimAlgorithm(UndirectedValuedGraph graph){
        this.heap = new BinaryHeapEdge();
        this.visitedNodes = new ArrayList<>();
        this.graph = graph;
        this.minSpanningTree = new UndirectedValuedGraph(new int[this.graph.getNbNodes()][this.graph.getNbNodes()]);
        this.cost = 0;
    }

    public void execPrimAlgorithm(UndirectedNode entryPointNode){

        // init the algorithm   O(1)
        UndirectedNode visitingNode = entryPointNode;
        visitingNode = this.graph.getNodes().get(visitingNode.getLabel());
        visitedNodes.add(visitingNode);

        // stop when all vertices are areVisited
        /*
            Overall complexity:
             * at the most n (= number of nodes) iterations
             * sum of vertex's degrees equals 2*m
             --> O((n+m)*log_2(m))
             quite better than original Prim's algorithm: O(n³) or O(n²)
         */
        while ( this.visitedNodes.size() != this.graph.getNbNodes() ) {
            // visit all vertices that are adjacent to `visitingNode`   O(d(visitingNode)*log_2(m))
            for (UndirectedNode neighbour : visitingNode.getNeighbours().keySet()) {
                int weight = visitingNode.getNeighbours().get(neighbour); // get the right reference
                this.heap.insert(visitingNode, neighbour, weight);  // O(log_2(m)), where m is the number of vertex
            }

            // pick min value from heap     O(log_2(m))
            Triple<UndirectedNode, UndirectedNode, Integer> minEdge = this.heap.remove();
            UndirectedNode from = minEdge.getFirst(), to = minEdge.getSecond();
            int edgeWeight = minEdge.getThird();

            // check if this picked edge makes a cycle
            // if yes, pick another one until it's the right one
            // O(m*log_2(m)) which happens rarely. Take rather O(log_2(m))
            while (visitedNodes.contains(from) && visitedNodes.contains(to)) { // O(n)
                minEdge = this.heap.remove();
                from = minEdge.getFirst();
                to = minEdge.getSecond();
                edgeWeight = minEdge.getThird();
            }

            // set the selected adjacent node as areVisited    O(1)
            if (visitedNodes.contains(from)) {
                this.visitedNodes.add(to);
                visitingNode = to;
            }
            else {
                this.visitedNodes.add(from);
                visitingNode = from;
            }

            // get the right reference from new areVisited node    O(1)
            visitingNode = this.graph.getNodes().get(visitingNode.getLabel());

            // add found weight to total cost
            cost += edgeWeight;

            // complete minimum spanning tree   O(1)
            this.minSpanningTree.addEdge(from,to,edgeWeight);
        }
    }

    public int getCost() {
        return this.cost;
    }

    public UndirectedValuedGraph getMinSpanningTree(){
        return this.minSpanningTree;
    }

    public static void main(String[] args) {
        // Graph from course's example
        int A = 0, B=1, C=2, D=3, E=4, F=5, G=6, H=7;
        UndirectedValuedGraph graph = new UndirectedValuedGraph(new int[8][8]);
        graph.addEdge(new UndirectedNode(A), new UndirectedNode(B),4);
        graph.addEdge(new UndirectedNode(A), new UndirectedNode(D),6);
        graph.addEdge(new UndirectedNode(A), new UndirectedNode(G),2);
        graph.addEdge(new UndirectedNode(B), new UndirectedNode(E),5);
        graph.addEdge(new UndirectedNode(B), new UndirectedNode(C),2);
        graph.addEdge(new UndirectedNode(C), new UndirectedNode(E),6);
        graph.addEdge(new UndirectedNode(C), new UndirectedNode(H),5);
        graph.addEdge(new UndirectedNode(C), new UndirectedNode(D),7);
        graph.addEdge(new UndirectedNode(D), new UndirectedNode(F),8);
        graph.addEdge(new UndirectedNode(F), new UndirectedNode(G),7);
        graph.addEdge(new UndirectedNode(F), new UndirectedNode(H),3);
        graph.addEdge(new UndirectedNode(H), new UndirectedNode(G),5);
        graph.addEdge(new UndirectedNode(G), new UndirectedNode(E),4);
        System.out.println(graph.toString());

        // run Prim's Algorithm
        PrimAlgorithm primInstance = new PrimAlgorithm(graph);
        primInstance.execPrimAlgorithm(new UndirectedNode(E));
        int cost = primInstance.getCost();
        System.out.println("cost = " + cost);
        System.out.println("resulting minimum spanning tree:\n" + primInstance.getMinSpanningTree());


    }
}
