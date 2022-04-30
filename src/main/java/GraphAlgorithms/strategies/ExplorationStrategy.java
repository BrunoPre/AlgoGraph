package GraphAlgorithms.strategies;

import Nodes.AbstractNode;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Stratégie d'exploration d'un graphe orienté
 * @param <T>
 */
public abstract class ExplorationStrategy<T extends AbstractNode> {
    protected int[] firstEncounter;
    protected int[] lastEncounter;
    protected int count;
    protected Function<T, Set<T>> childExtractor;

    public ExplorationStrategy(int size, Function<T, Set<T>> childExtractor) {
        firstEncounter = new int[size];
        Arrays.fill(firstEncounter, -1);
        lastEncounter = new int[size];
        Arrays.fill(lastEncounter, -1);
        this.childExtractor = childExtractor;
    }
    public abstract List<List<T>> apply(T node, Set<T> visited);

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