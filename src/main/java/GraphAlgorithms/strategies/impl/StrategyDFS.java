package GraphAlgorithms.strategies.impl;

import GraphAlgorithms.strategies.ExplorationStrategy;
import Nodes.AbstractNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Stratégie de parcours d'un graphe en profondeur
 * @param <T> le type de sommets parcourus
 */
public class StrategyDFS<T extends AbstractNode> extends ExplorationStrategy<T> {
    public StrategyDFS(int size, Function<T, Set<T>> childExtractor) {
        super(size, childExtractor);
    }

    @Override
    public List<List<T>> apply(T node, Set<T> visited) {
        visited.add(node);
        firstEncounter[node.getLabel()] = count;
        count++;
        List<List<T>> paths = new ArrayList<>();
        for (T voisin : childExtractor.apply(node)) {
            if (!visited.contains(voisin)) {
                apply(voisin, visited).forEach(array->{
                    List<T> curr = new ArrayList<>();
                    curr.add(node);
                    curr.addAll(array);
                    paths.add(curr);
                });
            }
        }
        lastEncounter[node.getLabel()] = count;
        count++;
        if (paths.isEmpty()) {
            return List.of(List.of(node));
        }
        return paths;
    }
}
