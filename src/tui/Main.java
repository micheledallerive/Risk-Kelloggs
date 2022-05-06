package tui;

import model.*;
import model.enums.DieColor;
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
            String who = "";
            String colorString = game.getPlayers().get(i).getColor().toString();
            if (game.getPlayers().get(i).isAI()) {
                who = colorString;
            } else {
                who = "You (" + colorString + ")";
            }
            printFormat("%-20s %-5s\n", who+":", rolls.get(i).toString());
        }
        if(game.getPlayers().get(maxIndex).isAI())
            print(game.getPlayers().get(maxIndex).getColor().toString()  + " starts");
        else
            print("You start");
        game.setTurn(maxIndex);

        consolePause(input);

        boolean finishedTerritories = false;
        while (!game.getPlayers().stream().allMatch(player -> player.getFreeArmies().size()==0)) {
            Player currentPlayer = game.getPlayers().get(game.getTurn());
            if (currentPlayer.getFreeArmies().size() > 0) {
                clearConsole();
                if (currentPlayer.isAI()) {
                    Territory chosen = ((AI)currentPlayer).placeArmy(game, !finishedTerritories);
                    print(currentPlayer.getColor().toString() + " chose " + chosen.getName().toString());
                } else {
                    printMap(game);
                    String territoryStr;
                    int amount = -10;
                    boolean validName = true;
                    do{
                        print("Enter the territory where you want to place your armies: ");
                        territoryStr = input.nextLine().toUpperCase();
                        if (finishedTerritories) {
                            print("Enter the amount of armies you want to place (you have "
                                + currentPlayer.getFreeArmies().size() + " armies): ");
                            amount = numInput.nextInt();
                        } else {
                            amount = 1;
                        }
                        final String finalTerritoryStr = territoryStr;
                        validName = Arrays.stream(TerritoryName.values()).anyMatch((n)->n.name().equals(finalTerritoryStr));
                    }while(amount < 1
                            || amount > currentPlayer.getFreeArmies().size()
                            || !validName);
                    TerritoryName territoryName = TerritoryName.valueOf(territoryStr);
                    Territory territory = game.getBoard().getTerritories().get(territoryName.ordinal());
                    if ((territory.getOwner() == currentPlayer && finishedTerritories)
                            || (territory.getOwner() == null)){
                        currentPlayer.placeArmies(territory, amount);
                    }
                }
            }
            finishedTerritories = game.getBoard().getTerritories()
                            .stream().filter(t -> t.getOwner() == null).count() == 0;
            //System.out.println(finishedTerritories);
            game.nextTurn();
        }

        game.setTurn(maxIndex);
        game.setStatus(GameStatus.PLAYING);

    }

    /**
     * Handles the behaviour of the game while it is being played.
     */
    private void playing() {
        while (!game.isWorldConquered()) {
            Player player = game.getPlayers().get(game.getTurn());
            if (player.isAI()) {
                playingAttack();
            } else {
                boolean endTurn = false;
                while (!endTurn) {
                    printOptions(
                            "Attack a territory",
                            "Move armies between territories",
                            "End turn"
                    );
                    int choice = numInput.nextInt();
                    switch(choice) {
                        case 1:
                            playingAttack();
                            break;
                        case 2:
                            playingMove();
                            break;
                        case 3:
                            endTurn = true;
                            break;
                    }
                }
            }
            game.nextTurn();
        }
    }

    private void playingAttack() {
        Player player = game.getPlayers().get(game.getTurn());
        if (player.isAI()) {
            //print("AI");
            for(int attacks = 0; attacks < 1; attacks++) {
                ((AI)player).attack(new Callback(){
                    @Override
                    public void onPlayerAttacked(Player attacker, Player attacked,
                                                 Territory fromTerritory, Territory attackedTerritory) {
                        print(player.getColor().toString() + " is attacking you in " +
                                        attackedTerritory.getName().toString() + "!");
                        print("How many armies do you want to defend with (max. "
                                + Math.min(attackedTerritory.getArmiesCount(), 3)
                                + ")?");
                        int defend = numInput.nextInt();
                        Integer[] losses = attacker.attack(fromTerritory, attackedTerritory,
                                Math.min(fromTerritory.getArmiesCount(), 3), defend);
                        print(
                                attacker.getColor().toString() + " lost " + losses[0] + " armies",
                                "You lost " + losses[1] + " armies"
                        );
                        consolePause(input);
                    }

                    @Override
                    public void onAIAttacked(Player attacker, Player attacked,
                                             Territory fromTerritory, Territory attackedTerritory) {
                        if (fromTerritory.getArmiesCount() < 2) return;
                        print(attacker.getColor() + " attacked " + attacked.getColor()
                            + " in " + attackedTerritory.getName().toString()
                            + " from " + fromTerritory.getName().toString());
                        attacker.attack(fromTerritory, attackedTerritory,
                                Math.min(fromTerritory.getArmiesCount()-1, 3),
                                Math.min(attackedTerritory.getArmiesCount(), 3));
                    }
                });
            }
        } else {
            print("Player");
            printMap(game);
            print("What territory do you want to attack?");
            String toAttack = input.nextLine().toUpperCase();
            if (!toAttack.equalsIgnoreCase("none")) {
                Territory attackedTerritory = game.getBoard().getTerritories().get(
                        TerritoryName.valueOf(toAttack).ordinal()
                );
                print("What territory do you want to attack from?");
                String fromStr = input.nextLine().toUpperCase();
                Territory fromTerritory = game.getBoard().getTerritories().get(
                        TerritoryName.valueOf(fromStr).ordinal()
                );
                boolean canAttack = false;
                if (fromTerritory != null) {
                    canAttack = fromTerritory.getArmiesCount() > 1;
                }
                if (fromTerritory != null && canAttack) {
                    int attackerMaxArmies = Math.min(fromTerritory.getArmiesCount() - 1, 3);
                    print("How many armies do you want to use to attack (1 - "
                            + attackerMaxArmies + ")");
                    int attackerArmies = numInput.nextInt();
                    Integer[] losses = player.attack(fromTerritory, attackedTerritory, attackerArmies);
                    print("You lost " + losses[0] + " armies");
                    print(attackedTerritory.getOwner().getColor().toString()
                            + " lost " + losses[1] + " armies");
                } else {
                    print("You can't attack that territory...");
                }
            }
            consolePause(input);
        }
    }

    private void playingMove() {
        Player player = game.getPlayers().get(game.getTurn());
        print("Where do you want to move the armies from?");
        String fromStr = input.nextLine();
        print("Where do you want to move the armies to?");
        String toStr = input.nextLine();
        print("How many armies do you want to move?");
        int amount = numInput.nextInt();
        Territory fromTerritory = game.getBoard().getTerritories().get(
                TerritoryName.valueOf(fromStr.toUpperCase()).ordinal()
        );
        Territory toTerritory = game.getBoard().getTerritories().get(
                TerritoryName.valueOf(toStr.toUpperCase()).ordinal()
        );
        player.moveArmies(amount, fromTerritory, toTerritory);
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
