package GraphAlgorithms;

import AdjacencyList.DirectedGraph;
import GraphAlgorithms.strategies.ExplorationStrategy;
import GraphAlgorithms.strategies.impl.StrategyBFS;
import GraphAlgorithms.strategies.impl.StrategyDFS;
import Nodes.AbstractNode;
import Nodes.DirectedNode;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GraphExplorer {

    public <T extends AbstractNode> List<List<T>> explorerGraphe(List<T> graph, ExplorationStrategy<T> strategy) {
        Set<T> nodes = new HashSet<>();
        List<List<T>> paths = new ArrayList<>();
        for(T current : graph) {
            if(!nodes.contains(current)) {
                paths.addAll(strategy.apply(current, nodes));
            }
        }
        return paths;
    }

    public static void main(String[] args) {
        GraphExplorer explorer = new GraphExplorer();

        DirectedGraph graph = explorer.getGraph();

        // On choisit la stratégie de parcours en profondeur
        ExplorationStrategy<DirectedNode> strategy = factoryBFS(graph.getNbNodes());
        explorer.explorerGraphe(graph.getNodes(), strategy);

        int[] fin = strategy.getLastEncounter();
        int[] debut = strategy.getFirstEncounter();

        System.out.println("\nTableau 'début' :");
        System.out.println(Arrays.toString(debut));
        System.out.println("\nTableau 'fin' :");
        System.out.println(Arrays.toString(fin));

        // Inversion du graph
        DirectedGraph inverted = (DirectedGraph) graph.computeInverse();
        strategy = factoryBFS(graph.getNbNodes());

        // Tri selon fin décroissant
        int[] finalFin = fin;
        inverted.getNodes().sort(Comparator.comparingInt(n -> -finalFin[n.getLabel()]));

        // On récupère les différents chemin du parcours
        List<List<DirectedNode>> nodes = explorer.explorerGraphe(inverted.getNodes(), strategy);

        fin = strategy.getLastEncounter();
        debut = strategy.getFirstEncounter();
        System.out.println("\nTableau 'début' du graphe inversé :");
        System.out.println(Arrays.toString(debut));
        System.out.println("\nTableau 'fin' du graphe inversé :");
        System.out.println(Arrays.toString(fin));

        List<String> names = List.of("A","B","C","D","E","F","G","H");
        List<List<String>> paths = nodes.stream().map(array->array.stream().map(node->names.get(node.getLabel())).collect(Collectors.toList())).collect(Collectors.toList());
        System.out.println("\n------------\nComposantes fortement connexes du graphe G : ");
        System.out.println(paths);
    }

    public static ExplorationStrategy<DirectedNode> factoryBFS(int nbNodes) {
        return new StrategyBFS<>(nbNodes,(node)->node.getSuccs().keySet(), (node)->node.getPreds().keySet());
    }

    public static ExplorationStrategy<DirectedNode> factoryDFS(int nbNodes) {
        return new StrategyDFS<>(nbNodes,(node)->node.getSuccs().keySet());
    }


    public DirectedGraph getGraph() {
        int[][] Matrix = new int[8][8]; // graph G from test document (check Moodle)
        DirectedGraph graph = new DirectedGraph(Matrix);
        graph.addArc(new DirectedNode(0), new DirectedNode(5)); // (A,F)

        graph.addArc(new DirectedNode(1), new DirectedNode(2)); // (B,C)
        graph.addArc(new DirectedNode(1), new DirectedNode(4)); // (B,E)

        graph.addArc(new DirectedNode(2), new DirectedNode(3)); // (C,D)

        graph.addArc(new DirectedNode(4), new DirectedNode(7)); // (E,H)

        graph.addArc(new DirectedNode(5), new DirectedNode(6)); // (F,G)

        graph.addArc(new DirectedNode(6), new DirectedNode(0)); // (G,A)
        graph.addArc(new DirectedNode(6), new DirectedNode(2)); // (G,C)
        graph.addArc(new DirectedNode(6), new DirectedNode(3)); // (G,D)

        graph.addArc(new DirectedNode(7), new DirectedNode(0)); // (H,A)
        graph.addArc(new DirectedNode(7), new DirectedNode(1)); // (H,B)
        graph.addArc(new DirectedNode(7), new DirectedNode(3)); // (H,B)
        return graph;
    }
}
