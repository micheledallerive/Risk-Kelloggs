package tui;

import model.*;
import model.enums.GameStatus;
import model.enums.TerritoryName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static tui.Utils.*;

/**
 * TUI class.
 * 
 * @author moralj@usi.ch
 */
public class Main {

    private Game game;
    private Scanner input;
    private Scanner numInput;

    /**
     * Creates a new TUI game.
     */
    public Main() {
        init();
        this.input = new Scanner(System.in);
        this.numInput = new Scanner(System.in);
    }

    private void init() {
        this.game = new Game();
    }

    /**
     * Handles the behaviour of the game in the main menu.
     */
    private void mainMenu() {
        print("Welcome to Risk Kellogg's");
        printOptions(
                "Play a new game of Risk Kellogg's",
                "Get information about Risk",
                "Exit the game"
        );
        int choice = numInput.nextInt();
        switch(choice) {
            case 1:
                this.game.setStatus(GameStatus.SETUP);
                break;
            case 2:
                printInfo();
                break;
            case 3:
                this.game.setStatus(GameStatus.EXIT);
        }
    }

    /**
     * Handles the behaviour of the game while setting up the game.
     */
    private void setupGame() {
        /*
        The setup of the game consists of:
            - Create the players
            - Give the armies to the players
            - Make the players move the armies in the territories he wants
         */
        ArrayList<Player> players = Player.generatePlayersRandomly(6);
        for (Player player : players) {
            game.addPlayer(player);
        }

        game.initArmies();

        print("Every player rolls a die, the player with the highest value starts first");
        ArrayList<Integer> rolls = new ArrayList<>();
        int maxIndex = 0;
        for (int i = 0; i < game.getPlayers().size(); i++) {
            rolls.add(Die.rollNormalDie());
            if (rolls.get(i) > rolls.get(maxIndex)) {
                maxIndex = i;
            }
            String who = game.getPlayers().get(i).isAI() ? "Player " + (i + 1) : "You";
            printFormat("%-10s %-5s\n", who+":", rolls.get(i).toString());
        }
        if(game.getPlayers().get(maxIndex).isAI())
            print("Player " + (maxIndex + 1) + " starts");
        else
            print("You start");
        game.setTurn(maxIndex);

        consolePause(input);

        while (!game.getPlayers().stream().allMatch(player -> player.getFreeArmies().size()==0)) {
            clearConsole();
            printMap(game);
            Player currentPlayer = game.getPlayers().get(game.getTurn());
            if (currentPlayer.getFreeArmies().size() > 0) {
                if (currentPlayer.isAI()) {
                    ((AI)currentPlayer).placeArmy(game);
                } else {
                    String territoryStr;
                    int amount;
                    boolean validName = true;
                    do{
                        print("Enter the territory where you want to place your armies: ");
                        territoryStr = input.nextLine().toUpperCase();
                        print("Enter the number of armies you want to place there (you have "+
                                currentPlayer.getFreeArmies().size() + " free armies): ");
                        amount = numInput.nextInt();
                        final String finalTerritoryStr = territoryStr;
                        validName = Arrays.stream(TerritoryName.values()).anyMatch((n)->n.name().equals(finalTerritoryStr));
                    }while(amount < -1
                            || amount > currentPlayer.getFreeArmies().size()
                            || !validName);
                    TerritoryName territoryName = TerritoryName.valueOf(territoryStr);
                    Territory territory = game.getBoard().getTerritories().get(territoryName.ordinal());
                    currentPlayer.placeArmies(territory, amount);
                }
            }
            game.nextTurn();
        }

    }

    /**
     * Handles the behaviour of the game while it is being played.
     */
    private void playing() {

    }

    /**
     * Handles the behaviour of the game after the game is over.
     */
    private void end() {
        print("The game is over.");
        printOptions("Player another game.", "Exit the game");
        int choice = numInput.nextInt();
        if (choice == 1) {
            // Wants to play again.
            init();
            this.play();
        } else {
            this.game.setStatus(GameStatus.EXIT);
        }
    }


    public void play() {
        while(game.getStatus() != GameStatus.EXIT) {
            switch(game.getStatus()) {
                case MENU:
                    mainMenu();
                    break;
                case SETUP:
                    setupGame();
                case PLAYING:
                    playing();
                case END:
                    end();
            }
        }
    }

    /**
     * Procedure - static main method.
     * @param args arguments to set terminal interface
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.play();
    }
}
