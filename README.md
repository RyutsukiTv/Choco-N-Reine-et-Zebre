# Exercice A: Configuration

## Prérequis

- Java JDK (version 8 ou supérieure)
- Choco Solver (assurez-vous de l'ajouter à votre projet)


# Exercice B: Modéliser le problème du Zèbre en extension


### Quelle est la première solution trouvée ? Est-elle correcte ?
| Maison | Couleur | Nationalité | Boisson | Cigarette | Animal |
| ------ | ------- | ----------- | ------- | --------- | ------ |
| 1      | Yellow  | Norwegian   | Water   | Kool      | Fox    |
| 2      | Blue    | Ukrainian   | Tea     | Chesterfield | Horse  |
| 3      | Red     | English     | Milk    | Old Gold   | Snail  |
| 4      | Ivory   | Spanish     | Orange Juice | Lucky Strike | Dog    |
| 5      | Green   | Japanese    | Coffee  | Parliament | Zebra  |

Oui, elle est correct.
### Calculez toutes les solutions ? Combien en trouvez-vous ? Sont-elles toutes correctes ?

Il n'y a qu'une seul solution possible à cette énigme.

# Exercice C: Modéliser le problème du Zèbre en intension
| Maison | Couleur | Nationalité | Boisson | Cigarette | Animal |
| ------ | ------- | ----------- | ------- | --------- | ------ |
| 1      | Yellow  | Norwegian   | Water   | Kool      | Fox    |
| 2      | Blue    | Ukrainian   | Tea     | Chesterfield | Horse  |
| 3      | Red     | English     | Milk    | Old Gold   | Snail  |
| 4      | Ivory   | Spanish     | Orange Juice | Lucky Strike | Dog    |
| 5      | Green   | Japanese    | Coffee  | Parliament | Zebra  |
### Verification des résultats

Les résultats sont similaires avec le programme en extension et en intension, ce qui est logique car le problème est le même, mais la façon de le programmer est différente.
# Différence entre Extension et Intension

### Contraintes en Extension :

Ces contraintes sont définies en spécifiant explicitement les tuples de valeurs autorisées ou interdites pour les variables concernées.
Elles énumèrent les combinaisons possibles de valeurs de variables qui sont valides (tuples autorisés) ou invalides (tuples interdits).
Par exemple,
```java
int[][] validTuples = {
{1, 2},
{2, 3},
{3, 4},
{4, 5}
};
Tuples tuplesAutorises = new Tuples(validTuples, true);
model.table(new IntVar[]{ivo, gre}, tuplesAutorises).post();
```
`validTuples` énumère toutes les possibilités entre `ivo` et `gre` puis l'ajoute au `model.table()`

### Contraintes en Intension (ou en Algorithme) :

Ces contraintes sont définies en termes d'algorithmes ou de formules mathématiques.
Elles spécifient les relations entre les variables en utilisant des expressions ou des équations.
Par exemple, `model.arithm(gre, "=", ivo, "+", 1).post();` est une contrainte en intension. Elle indique que la variable `ivo` doit etre a droite de `gre`.


# Exercice D: Modélisation du Problème des N-Reines

## Introduction

Le problème des n-reines consiste à positionner n reines sur un échiquier de nxn cases de manière à ce qu'elles ne se menacent pas les unes les autres. Pour résoudre ce problème, nous allons utiliser le solveur Choco Solver en Java.

## Contenu de la partie D

- **NReines.java** : Classe principale qui implémente la modélisation du problème et la résolution.

## Instructions

### 1. Définition des Variables

Pour modéliser le problème, nous utilisons une variable Ri par reine, où Ri représente la ligne i et sa valeur représente la colonne où la reine sera positionnée. Nous utilisons la méthode `intVarArray` pour créer un tableau de `IntVar` ayant tous le même domaine.
Afin de creer notre tableau, nous avons definie les variable de `intVarArray`.

"Queen" : C'est le nom donné au tableau de variables. 

q : C'est le nombre de variables dans le tableau. Dans le contexte du problème des n-reines, q représente le nombre de reines placer sur l'échiquier.

1 : C'est la valeur minimale que chaque variable du tableau peut prendre. Dans le contexte des n-reines, il s'agit généralement de 1, car les colonnes sont numérotées à partir de 1.

n : C'est la valeur maximale que chaque variable du tableau peut prendre. Dans le contexte des n-reines, n est généralement égal à q, car il s'indique la taille de l'échiquier (nxn), et chaque reine peut être placée dans une colonne de 1 à n.
```java
IntVar[] queens = model.intVarArray("Queen", q, 1, n);
```
### 2. Contraintes pour les Lignes

Nous ajoutons la contrainte `model.allDifferent(queens).post()` pour s'assurer que chaque reine est positionnée sur une ligne différente.

### 3. Contraintes pour les Colonnes

Pour s'assurer qu'aucunes deux reines ne soient sur la même colonne, nous utilisons une boucle pour comparer toutes les paires de reines et ajoutons la contrainte `model.arithm(queens[i], "!=", queens[j]).post()`.

### 4. Contrainte arithmétique entre Ri et Rj pour les Diagonales

Pour garentir les contrainte diagonales d'une reine il ya deux contraintre a prendre en compte la diagonal principales (Descendantes) et secondaires (ascendante).

Pour les diagonales principales (descendantes) :

- Ri - Rj != i - j

Pour les diagonales secondaires (ascendantes) :

- Ri - Rj != j - i

#### Expliquons ces contraintes en détail :

- "Ri" représente la colonne de la reine sur la ligne i.

- "Rj" représente la colonne de la reine sur la ligne j.

- "i" est le numéro de la ligne de la reine Ri.

- "j" est le numéro de la ligne de la reine Rj.

La contrainte s'assure que la différence entre les colonnes des reines Ri et Rj n'est pas égale à la différence entre leurs lignes respectives. Cela garantit que les reines ne se trouvent pas sur la même diagonale.




### 5. Contraintes pour les Diagonales

Pour assurer ces contraintes, les contraintes arithmétique si-dessus sont convertie en choco. Nous ajoutons les contraintes suivantes :
- `model.arithm(queens[i], "-", queens[j], "!=", i - j).post()` pour les diagonales principales.
- `model.arithm(queens[i], "-", queens[j], "!=", j - i).post()` pour les diagonales secondaires.

### 6. Remarque sur N-Reines et Solutions

Pour différentes valeurs de n, telles que n=1, 2, 3, 4, 8, 12, 16.

| Nombre de Reine | N=1                | N=2   | N=3     | N=4 | N=8  | N=12 | N=16 |
| ---             |--------------------| ---   |---------| --- | ---  | ---  | ---  |
| Variables :     | 2                  | 2     | 3       | 4   | 8    | 12   | 16   |
| Constraints :   | 1                  | 4     | 10      | 19  | 85   | 199  | 361  |
| Building time : | 1,119s             | 9,507s| 52,561s | 11,695s| 14,133s| 16,071s| 3,733s |
| Complete search |  1 solution found. |  | |         |2 solution(s) found. | 14,200 solution(s) found. |  14,772,512 solution(s) found. |
| Solutions:      | 1                  | 0     | 0       | 2   | 92   | 14,200  | 14,772,512 |
| Nodes:          | 1 (45,0 n/s)       | 1 (193,3 n/s) | 2 (1,292,5 n/s) | 5 (1,622,7 n/s) | 495 (7,949,3 n/s) | 132,512 (35,961,5 n/s) | 135,781,503 (47,534,4 n/s) |
| Backtracks:     | 1                  | 3     | 5       | 7   | 807  | 236,625 | 242,017,983 |
| Backjumps:      | 0                  | 0     | 0       | 0   | 0    | 0       | 0 |
| Fails:          | 0                  | 2     | 3       | 2   | 312  | 104,113 | 106,236,480 |
| Restarts:       | 0                  | 0     | 0       | 0   | 0    | 0       | 0 |

Le nombre de nœuds explorés augmente significativement avec n. Cela indique que la recherche de solutions devient plus complexe à mesure que l'échiquier s'agrandit.

Le nombre de backtracks augmente également, ce qui est lié à la complexité croissante de la recherche.

Les échecs augmentent également avec n.Le nombre d'échecs est un indicateur de la complexité du problème et de la recherche de solutions, car il mesure le nombre de chemins infructueux que le solveur doit explorer avant de trouver une solution valide.

### 7. Ajouts et Améliorations 
- Visual console pour voir le nombre de solutions trouvées au fur et à mesure du temps, et le nombre de solutions trouvées par seconde.
- Scanner pour demander le nombre de reines à placer.
- Fonction `printVisualModel()` pour un affichage des solutions au fur et à mesure.

