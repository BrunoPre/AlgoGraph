package GraphAlgorithms.strategies;

import Nodes.AbstractNode;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

public abstract class ExplorationStrategy<T extends AbstractNode> {
    protected int[] firstEncounter;
    protected int[] lastEncounter;
    protected int count;
    public ExplorationStrategy(int size) {
        firstEncounter = new int[size];
        lastEncounter = new int[size];
    }
    public abstract List<List<T>> apply(T node, Function<T, Set<T>> neighbours, Set<T> visited);

    public int[] getFirstEncounter() {
        return firstEncounter;
    }

    public int[] getLastEncounter() {
        return lastEncounter;
    }

    public int getCount() {
        return count;
    }
}