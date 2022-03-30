package GraphAlgorithms;

import Abstraction.AbstractListGraph;
import AdjacencyList.DirectedGraph;
import Nodes.AbstractNode;
import Nodes.DirectedNode;
import Collection.Pair;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;

public class GraphExplorer {

    public <T extends AbstractNode> void explorerGraphe(AbstractListGraph<T> graph, Function<T, Set<T>> neighbours, ExplorationStrategy<T> strategy) {
        Set<T> nodes = new HashSet<>();
        for(T current : graph.getNodes()) {
            if(!nodes.contains(current)) {
                strategy.apply(current, neighbours, nodes);
            }
        }
    }

    private abstract static class ExplorationStrategy<T extends AbstractNode> {
        protected int[] firstEncounter;
        protected int[] lastEncounter;
        protected int count;
        public ExplorationStrategy(int size) {
            firstEncounter = new int[size];
            lastEncounter = new int[size];
        }
        public abstract void apply(T node, Function<T, Set<T>> neighbours, Set<T> visited);

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

    private static class StrategyBFS<T extends AbstractNode> extends ExplorationStrategy<T> {
        public StrategyBFS(int size) {
            super(size);
        }

        @Override
        public void apply(T node, Function<T, Set<T>> neighboursExtractor, Set<T> visited) {
            List<T> queue = new LinkedList<>();
            queue.add(node);
            CountingQueue<T> neighboursCountingQueue = new CountingQueue<>();
            while(!queue.isEmpty()) {
                T current = queue.remove(0);
                if(visited.contains(current)) {
                    if(neighboursCountingQueue.hasNext()) {
                        T lastChildVisited = neighboursCountingQueue.next();
                        if (lastChildVisited != null) {
                            lastEncounter[lastChildVisited.getLabel()] = count;
                            count++;
                        }
                    }
                    continue;
                }
                firstEncounter[current.getLabel()] = count;
                count++;
                if(neighboursCountingQueue.hasNext()) {
                    T lastChildVisited = neighboursCountingQueue.next();
                    if (lastChildVisited != null) {
                        lastEncounter[lastChildVisited.getLabel()] = count;
                        count++;
                    }
                }
                System.out.println(current.getLabel());
                visited.add(current);
                Set<T> neighbours = neighboursExtractor.apply(current);
                queue.addAll(neighbours);
                neighboursCountingQueue.add(current,neighbours.size());
            }
        }
        private static class CountingQueue<E> {
            private List<E> elements = new LinkedList<>();
            private List<AtomicInteger> ticksRemaining = new LinkedList<>();

            public void add(E element, int ticks) throws IllegalArgumentException {
                if (ticks <=0) {
                    throw new IllegalArgumentException("ticks must be positive");
                }
                elements.add(element);
                ticksRemaining.add(new AtomicInteger(ticks));
            }

            public E next() {
                int tick = ticksRemaining.get(0).decrementAndGet();
                if (tick <= 0) {
                    ticksRemaining.remove(0);
                    return elements.remove(0);
                }
                return null;
            }

            public boolean hasNext() {
                return !elements.isEmpty() && !ticksRemaining.isEmpty();
            }
        }
    }

    private static class StrategyDFS<T extends AbstractNode> extends ExplorationStrategy<T> {
        public StrategyDFS(int size) {
            super(size);
        }

        @Override
        public void apply(T node, Function<T, Set<T>> neighboursExtractor, Set<T> visited) {
            visited.add(node);
            firstEncounter[node.getLabel()] = count;
            count++;
            System.out.println(node.getLabel());
            for(T voisin : neighboursExtractor.apply(node)) {
                if(!visited.contains(voisin)) {
                    apply(voisin, neighboursExtractor, visited);
                }
            }
            lastEncounter[node.getLabel()] = count;
            count++;
        }
    }

    public static void main(String[] args) {
        //int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        int[][] Matrix = new int[8][8]; // graph G from test document (check Moodle)
        //GraphTools.afficherMatrix(Matrix);
        DirectedGraph al = new DirectedGraph(Matrix);
        al.addArc(new DirectedNode(0), new DirectedNode(5)); // (A,F)
        al.addArc(new DirectedNode(1), new DirectedNode(2)); // (B,C)
        al.addArc(new DirectedNode(1), new DirectedNode(4)); // (B,E)
        al.addArc(new DirectedNode(2), new DirectedNode(3)); // (C,D)
        al.addArc(new DirectedNode(4), new DirectedNode(7)); // (E,H)
        al.addArc(new DirectedNode(5), new DirectedNode(6)); // (F,G)
        al.addArc(new DirectedNode(6), new DirectedNode(0)); // (G,A)
        al.addArc(new DirectedNode(6), new DirectedNode(2)); // (G,C)
        al.addArc(new DirectedNode(6), new DirectedNode(3)); // (G,D)
        System.out.println(al);
        ExplorationStrategy<DirectedNode> strategy = new StrategyBFS<>(al.getNbNodes());

        GraphExplorer explorer = new GraphExplorer();
        explorer.explorerGraphe(al, (node)->node.getSuccs().keySet(), strategy);
        System.out.println(Arrays.toString(strategy.getFirstEncounter()));
        System.out.println(Arrays.toString(strategy.getLastEncounter()));
    }
}
