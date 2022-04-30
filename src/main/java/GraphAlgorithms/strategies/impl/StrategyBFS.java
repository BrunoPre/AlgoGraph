package GraphAlgorithms.strategies.impl;

import GraphAlgorithms.strategies.ExplorationStrategy;
import Nodes.AbstractNode;

import java.util.*;
import java.util.function.Function;

public class StrategyBFS<T extends AbstractNode> extends ExplorationStrategy<T> {
    public StrategyBFS(int size) {
        super(size);
    }

    @Override
    public List<List<T>> apply(T node, Function<T, Set<T>> neighboursExtractor, Set<T> visited) {
        Queue<T> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            T current = queue.poll();
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            Set<T> neighbours = neighboursExtractor.apply(current);
            queue.addAll(neighbours);
        }
        return Collections.emptyList();
    }
}