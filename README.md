# Graph-Framework

School Java project implementing a graph framework, with various (un)directed graph representations (adjacency list/matrix, incident matrix and nodes).

Project owners are:

LECOLAZET Aymeric [@aym33rick](https://github.com/aym33rick)

PREKA Bruno [@BrunoPre](https://github.com/BrunoPre)

VAUTIER Paul [@PeP-dev](https://github.com/PeP-dev)

---
# English explanation

## DFS/BFS
Called algorithms are in the main class of `GraphAlgorithms/GraphExplorer.java`

Both search algorithms are implemented as strategies in the `GraphAlgorithms.strategies.impl` package.

Both return a list of lists of nodes (abstract class `AbstractNode` in the `Nodes` package) 
representing the discovered path, which allows to retrieve the strongly connected components of the graph. 

Breadth-first search algorithm (resp. Depth-first search) is written in `StrategyBFS` (resp. `StrategyDFS`) class. 

## PRIM's algorithm
**PRIM**'s algorithm is written in `PrimAlgorithm` class, located in `GraphAlgorithms` package.

It uses a binary heap (see `BinaryHeapEdge` class in the same package).

### Run
Run `PrimAlgorithm` class's `main` function, which runs the algorithm on a graph example as instance. 

## Dijkstra and Bellman's algorithms

In the same way, respective `main` functions in `DijkstraAlgorithm` and `BellmanAlgorithm` classes
allows to watch the results.

---

# French explanation

## Partie 1 (DFS/BFS)
Les algorithmes sont utilisés dans le main de `GraphAlgorithms/GraphExplorer.java`

Les algorithmes de parcours sont implémentés dans les stratégies présentes dans le package `GraphAlgorithms.strategies.impl`.

Le parcours en largeur (BFS) est présent dans la classe `StrategyBFS`, le calcul des visites totales se fait en remontant vers les parents et en vérifiant récursivement si ses enfants sont visités.

Le parcours en profondeur (DFS) se situe dans la classe `StrategyDFS` et est défini suivant le pseudocode présent dans le cours.

Les deux stratégies renvoient une liste de listes de sommets, représentant un chemin exploré, ce qui permet de retrouver les composantes fortement connexes du graphe.

## Partie 2 (Algorithme de PRIM)

L'algorithme de **PRIM** est décrit et implémenté dans la classe `PrimAlgorithm` sous le package `GraphAlgorithms`.

Il est basé sur la classe `BinaryHeapEdge` du même package.

Il suffit de lancer la fonction `main` de la classe `PrimAlgorithm`, qui lance une instance de cette algorithme sur une instance du graphe d'exemple du cours (cf exemple sur Moodle).

## Partie 3-4 (Algorithmes de Dijkstra et Bellman)

De la même façon, les fonctions `main` respectifs des classes `DijkstraAlgorithm` et `BellmanAlgorithm` permettent d'observer les résultats des algorithmes correspondants. 

