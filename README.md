# Graph-Framework

FIL A1

LECOLAZET Aymeric

PREKA Bruno

VAUTIER Paul

---

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

