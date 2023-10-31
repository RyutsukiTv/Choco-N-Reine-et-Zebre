import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import java.util.Scanner;

public class NReines {

    public static void main(String[] args) {
        // Création du modèle

        while(true) {
            Model model = new Model("Reine");
            // Demandez à l'utilisateur de spécifier le nombre de reines
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez le nombre de reines à placer : ");
            int q = scanner.nextInt();

            // Taille du plateau d'échecs
            int n = q;

            // Création des variables
            IntVar[] queens = model.intVarArray("Queen", q, 1, n);

            // 1.Contrainte pour que chaque reine soit sur une ligne différente
            model.allDifferent(queens).post();

            // 2.Contrainte pour que chaque reine soit sur une colonne différente et des diagonales différentes
            for (int i = 0; i < q; i++) {
                for (int j = i + 1; j < q; j++) {
                    model.arithm(queens[i], "!=", queens[j]).post();
                    model.arithm(queens[i], "-", queens[j], "!=", i - j).post();
                    model.arithm(queens[i], "-", queens[j], "!=", j - i).post();
                }
            }

            // Calcul de toutes les solutions
            System.out.println("\n\n*** Solutions ***");
            long startTime = System.currentTimeMillis();
            int solutionCount = 0;

            while (model.getSolver().solve()) {
                solutionCount++;
                if (solutionCount % 100 == 0) {
                    long currentTime = System.currentTimeMillis();
                    double elapsedTime = (currentTime - startTime) / 1000.0;
                    double solutionsPerSecond = solutionCount / elapsedTime;
                    System.out.print("\rSolutions trouvées : " + solutionCount +
                            " (Solutions par seconde : " + solutionsPerSecond + ")");
                }
            }

            // Affichage de l'ensemble des caractéristiques de résolution
            System.out.println("\n\n*** Bilan ***");
            model.getSolver().printStatistics();
        }
    }

    // Fonction d'affichage des solutions
    public static void printVisualModel(IntVar[] queens) {
        int n = queens.length;
        char[][] board = new char[n][n];

        // Remplir le tableau avec des espaces vides
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = ' ';
            }
        }

        // Placer les reines sur le tableau
        for (int i = 0; i < n; i++) {
            int row = queens[i].getValue() - 1;
            board[i][row] = 'X';
        }

        // Afficher le tableau visuel
        System.out.println("Tableau Visuel du Modèle :");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("|" + board[i][j]);
            }
            System.out.println("|");
        }
    }
}
