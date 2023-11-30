import java.util.Scanner;

public class tictactoe_non_ai {

    public static void main(String[] args) {
        String[] gameGrid = new String[9];
        initialiseGame(gameGrid);

        display(gameGrid);

        int currentTurn = 0;
        Scanner read = new Scanner(System.in);
        int slot;

        while (!isFull(gameGrid)) {
            if (currentTurn % 2 == 0) {
                // human's turn
                System.out.println("Your Turn");
                System.out.println("enter the slot");
                slot = read.nextInt();

                while (!isEmpty(gameGrid[slot])) {
                    System.out.println("slot occupied.please select another slot");
                    slot = read.nextInt();
                }
                gameGrid[slot] = "O";

                if (checkWon(gameGrid)) {
                    display(gameGrid);
                    System.out.println("You Won");
                    break;
                }
                currentTurn++;

            } else { // computer's turn
                if (currentTurn == 1) {
                    if (isEmpty(gameGrid[4])) {

                        gameGrid[4] = "X";
                    } else {
                        gameGrid[0] = "X";
                    }

                } else if (currentTurn == 3) {
                    if (psblwin(gameGrid, "X")) {
                        int pos = findpost(gameGrid, "X");
                        gameGrid[pos] = "X";
                    } else if (psblwin(gameGrid, "O")) {
                        int pos = findpost(gameGrid, "O");
                        gameGrid[pos] = "X";
                    } else if (isEmpty(gameGrid[2])) {
                        gameGrid[2] = "X";
                    } else {
                        if (psblwin(gameGrid, "O")) {
                            int pos = findpost(gameGrid, "O");
                            gameGrid[pos] = "X";
                        } else if (isEmpty(gameGrid[6])) {
                            gameGrid[6] = "X";
                        } else {
                            gameGrid[8] = "X";
                        }
                    }
                } else {
                    if (psblwin(gameGrid, "X")) {
                        int pos = findpost(gameGrid, "X");
                        gameGrid[pos] = "X";
                    }
                    // checking possoblity of human's winning
                    else if (psblwin(gameGrid, "O")) {
                        int pos = findpost(gameGrid, "O");
                        gameGrid[pos] = "X";
                    } else {
                        if (psblwin(gameGrid, "X")) {
                            int pos = findpost(gameGrid, "X");
                            gameGrid[pos] = "X";
                        } else {
                            for (int i = 0; i < gameGrid.length; i++) {
                                if (isEmpty(gameGrid[i])) {
                                    gameGrid[i] = "X";
                                }
                            }
                        }
                    }
                }

                currentTurn++;
                if (checkWon(gameGrid)) {
                    display(gameGrid);
                    System.out.println("You loose");
                    break;
                }

            }
            display(gameGrid);
        }
        if (!checkWon(gameGrid) && isFull(gameGrid)) {
            System.out.println("Draw");
        }
        read.close();
    }

    static void initialiseGame(String[] gameGrid) {
        for (int i = 0; i < gameGrid.length; i++) {
            gameGrid[i] = " ";
        }
    }

    static void display(String[] gameGrid) {
        System.out.println("+---+---+---+");
        for (int i = 0; i < gameGrid.length; i += 3) {
            System.out.print("| ");
            for (int j = i; j < i + 3; j++) {
                System.out.print(gameGrid[j] + " | ");
            }
            System.out.println("\n+---+---+---+");
        }
    }

    static Boolean isEmpty(String input) {
        return input.equals(" ");
    }

    static boolean psblwin(String[] gameGrid, String X) {
        return (rowWon(gameGrid, X) || columnWon(gameGrid, X) || diagonalWon(gameGrid, X));
    }

    static boolean rowWon(String[] gameGrid, String X) {
        for (int i = 0; i < gameGrid.length; i += 3) {
            if (gameGrid[i] == X && gameGrid[i + 1] == X && isEmpty(gameGrid[i + 2])) {
                return true;
            } else if (gameGrid[i] == X && gameGrid[i + 2] == X && isEmpty(gameGrid[i + 1])) {
                return true;
            } else if (gameGrid[i + 1] == X && gameGrid[i + 2] == X && isEmpty(gameGrid[i])) {
                return true;
            }
        }
        return false;
    }

    static boolean columnWon(String[] gameGrid, String X) {
        for (int i = 0; i < 3; i++) {
            if (gameGrid[i] == X && gameGrid[i + 3] == X && isEmpty(gameGrid[i + 6])) {
                return true;
            } else if (gameGrid[i] == X && gameGrid[i + 6] == X && isEmpty(gameGrid[i + 3])) {
                return true;
            } else if (gameGrid[i + 3] == X && gameGrid[i + 6] == X && isEmpty(gameGrid[i])) {
                return true;
            }
        }
        return false;
    }

    static boolean diagonalWon(String[] gameGrid, String X) {
        if (gameGrid[0] == X && gameGrid[4] == X && isEmpty(gameGrid[8])) {
            return true;
        } else if (gameGrid[4] == X && gameGrid[8] == X && isEmpty(gameGrid[0])) {
            return true;
        } else if (gameGrid[0] == X && gameGrid[8] == X && isEmpty(gameGrid[4])) {
            return true;
        } else if (gameGrid[2] == X && gameGrid[4] == X && isEmpty(gameGrid[6])) {
            return true;
        } else if (gameGrid[4] == X && gameGrid[6] == X && isEmpty(gameGrid[2])) {
            return true;
        } else if (gameGrid[2] == X && gameGrid[6] == X && isEmpty(gameGrid[4])) {
            return true;
        }

        return false;
    }

    static int findpost(String[] gameGrid, String X) {
        if (rowWon(gameGrid, X)) {
            for (int i = 0; i < gameGrid.length; i += 3) {
                if (gameGrid[i] == X && gameGrid[i + 1] == X && isEmpty(gameGrid[i + 2])) {
                    return i + 2;
                } else if (gameGrid[i] == X && gameGrid[i + 2] == X && isEmpty(gameGrid[i + 1])) {
                    return i + 1;
                } else if (gameGrid[i + 1] == X && gameGrid[i + 2] == X && isEmpty(gameGrid[i])) {
                    return i;
                }
            }
        } else if (columnWon(gameGrid, X)) {
            for (int i = 0; i < 3; i++) {
                if (gameGrid[i] == X && gameGrid[i + 3] == X && isEmpty(gameGrid[i + 6])) {
                    return i + 6;
                } else if (gameGrid[i] == X && gameGrid[i + 6] == X && isEmpty(gameGrid[i + 3])) {
                    return i + 3;
                } else if (gameGrid[i + 3] == X && gameGrid[i + 6] == X && isEmpty(gameGrid[i])) {
                    return i;
                }
            }
        } else if (diagonalWon(gameGrid, X)) {
            if (gameGrid[0] == X && gameGrid[4] == X && isEmpty(gameGrid[8])) {
                return 8;
            } else if (gameGrid[4] == X && gameGrid[8] == X && isEmpty(gameGrid[0])) {
                return 0;
            } else if (gameGrid[0] == X && gameGrid[8] == X && isEmpty(gameGrid[4])) {
                return 4;
            } else if (gameGrid[2] == X && gameGrid[4] == X && isEmpty(gameGrid[6])) {
                return 6;
            } else if (gameGrid[4] == X && gameGrid[6] == X && isEmpty(gameGrid[2])) {
                return 2;
            } else if (gameGrid[2] == X && gameGrid[6] == X && isEmpty(gameGrid[4])) {
                return 4;
            }
        }
        return 0;
    }

    static boolean isFull(String[] gameGrid) {
        for (int i = 0; i < gameGrid.length; i++) {
            if (gameGrid[i].equals(" ")) {
                return false;
            }
        }
        return true;
    }

    static boolean checkWon(String[] gameGrid) {
        return (rowWon1(gameGrid) || columnWon1(gameGrid) || diagonalWon1(gameGrid));
    }

    static boolean rowWon1(String[] gameGrid) {
        for (int i = 0; i < gameGrid.length; i += 3) {
            String firstInRow = gameGrid[i];
            String secondInRow = gameGrid[i + 1];
            String thirdInRow = gameGrid[i + 2];

            if (!isEmpty(firstInRow) && firstInRow.equals(secondInRow) && secondInRow.equals(thirdInRow)) {
                return true;
            }
        }
        return false;
    }

    static boolean columnWon1(String[] gameGrid) {
        for (int i = 0; i < 3; i++) {
            String firstInColumn = gameGrid[i];
            String secondInColumn = gameGrid[i + 3];
            String thirdInColumn = gameGrid[i + 6];

            if (!isEmpty(firstInColumn) && firstInColumn.equals(secondInColumn)
                    && secondInColumn.equals(thirdInColumn)) {
                return true;
            }
        }
        return false;
    }

    static boolean diagonalWon1(String[] gameGrid) {
        String center = gameGrid[4];
        return (!isEmpty(center) && ((center.equals(gameGrid[0]) && center.equals(gameGrid[8]))
                || (center.equals(gameGrid[2]) && center.equals(gameGrid[6]))));
    }
}


