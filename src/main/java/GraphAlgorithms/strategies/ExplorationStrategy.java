package GraphAlgorithms.strategies;

import Nodes.AbstractNode;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Stratégie d'exploration d'un graphe orienté
 * @param <T> le
 */
public abstract class ExplorationStrategy<T extends AbstractNode> {
    /**
     * La variable de première visite
     */
    protected int[] firstEncounter;
    /**
     * La variable de dernière visite (quand tout les enfants ont été visités)
     */
    protected int[] lastEncounter;
    /**
     * L'incrémentation pour {@code lastEncounter} et {@code firstEncounter}
     */
    protected int count;
    /**
     * La fonction de récupération des enfants
     */
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