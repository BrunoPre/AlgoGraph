package GraphAlgorithms.strategies.impl;

import GraphAlgorithms.strategies.ExplorationStrategy;
import Nodes.AbstractNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class StrategyDFS<T extends AbstractNode> extends ExplorationStrategy<T> {
    public StrategyDFS(int size) {
        super(size);
    }

    @Override
    public List<List<T>> apply(T node, Function<T, Set<T>> neighboursExtractor, Set<T> visited) {
        visited.add(node);
        firstEncounter[node.getLabel()] = count;
        count++;
        List<List<T>> paths = new ArrayList<>();
        for (T voisin : neighboursExtractor.apply(node)) {
            if (!visited.contains(voisin)) {
                apply(voisin, neighboursExtractor, visited).forEach(array->{
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
