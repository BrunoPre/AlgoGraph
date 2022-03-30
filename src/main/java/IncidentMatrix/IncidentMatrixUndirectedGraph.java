package IncidentMatrix;

import Abstraction.AbstractIncidentGraph;
import Abstraction.IUndirectedGraph;
import Nodes.UndirectedNode;

public class IncidentMatrixUndirectedGraph extends AbstractIncidentGraph implements IUndirectedGraph {

    @Override
    public int getNbNodes() {
        return 0;
    }

    @Override
    public int[][] toAdjacencyMatrix() {
        return new int[0][];
    }

    @Override
    public int getNbEdges() {
        return 0;
    }

    @Override
    public boolean isEdge(UndirectedNode x, UndirectedNode y) {
        return false;
    }

    @Override
    public void removeEdge(UndirectedNode x, UndirectedNode y) {

    }

    @Override
    public void addEdge(UndirectedNode x, UndirectedNode y) {

    }
}
