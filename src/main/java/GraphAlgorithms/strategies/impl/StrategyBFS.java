package GraphAlgorithms.strategies.impl;

import GraphAlgorithms.strategies.ExplorationStrategy;
import Nodes.AbstractNode;

import java.util.*;
import java.util.function.Function;

/**
 * Stratégie de parcours d'un graphe orienté en largeur.
 * @param <T> le type de sommets parcourus
 */
public class StrategyBFS<T extends AbstractNode> extends ExplorationStrategy<T> {
    private Function<T, Set<T>> parentExtractor;

    public StrategyBFS(int size, Function<T, Set<T>> childExtractor, Function<T, Set<T>> parentExtractor) {
        super(size, childExtractor);
        this.parentExtractor = parentExtractor;
    }

    @Override
    public List<List<T>> apply(T node, Set<T> visited) {
        Queue<List<T>> queue = new LinkedList<>();
        queue.add(List.of(node));
        List<List<T>> allPaths = new ArrayList<>();

        while (!queue.isEmpty()) {
            List<T> currentPath = queue.poll();
            T current = currentPath.get(currentPath.size()-1);
            if (visited.contains(current)) {
                continue;
            }
            firstEncounter[current.getLabel()] = count;
            count++;
            visited.add(current);
            Set<T> children = childExtractor.apply(current);
            if (children.isEmpty() || visited.containsAll(children)) {
                setFullyVisitedAndPropagate(current, visited, new HashSet<>());
                allPaths.add(currentPath);
            }
            children.forEach(child->{
                List<T> clone = new ArrayList<>(currentPath);
                clone.add(child);
                queue.add(clone);
            });
        }
        return allPaths;
    }

    /**
     * Définie un sommet comme étant entièrement parcouru, et vérifie si son parent devient entièrement parcouru (càd {@code node} est le dernier enfant parcouru de son parent
     * @param node La node entièrement parcourue
     * @param visited La liste de tout les sommets visités
     * @param propagationList La liste des sommets visités lors de la propagation (évite la boucle de récursion)
     */
    private void setFullyVisitedAndPropagate(T node,Set<T> visited, Set<T> propagationList) {
        propagationList.add(node);
        lastEncounter[node.getLabel()] = count;
        count++;
        Set<T> parents = parentExtractor.apply(node);
        if (parents.isEmpty()) {
            return;
        }
        for (T parent : parents) {
            if(propagationList.contains(parent)) {
                continue;
            }
            if (visited.containsAll(childExtractor.apply(parent))) {
                setFullyVisitedAndPropagate(parent, visited, propagationList);
            }
        }
    }
}