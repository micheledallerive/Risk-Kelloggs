package tui;

import static tui.Utils.askNumber;
import static tui.Utils.askTerritory;
import static tui.Utils.clearConsole;
import static tui.Utils.consolePause;
import static tui.Utils.print;
import static tui.Utils.printFormat;
import static tui.Utils.printInfo;
import static tui.Utils.printMap;
import static tui.Utils.printOptions;

import model.AI;
import model.Card;
import model.Continent;
import model.Die;
import model.Game;
import model.Player;
import model.Territory;
import model.callback.Callback;
import model.callback.GameCallback;
import model.enums.GameStatus;
import tui.command.AttackCommand;
import tui.command.Command;
import tui.command.EndTurnCommand;
import tui.command.MoveArmiesCommand;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * TUI class.
 *
 * @author dallem @usi.ch, moralj@usi.ch
 */
public class Main {

    private Game game;
    private Scanner input;
    private Scanner numInput;

    private ArrayList<Command> commands;

    /**
     * Creates a new TUI game.
     */
    public Main() {
        this.input = new Scanner(System.in);
        this.numInput = new Scanner(System.in);
        init();
    }

    private void init() {
        this.game = new Game();
        this.commands = new ArrayList<>();
        commands.add(new AttackCommand("Attack a territory", game, input, numInput));
        commands.add(new MoveArmiesCommand("Move armies between territories", game, input, numInput));
        commands.add(new EndTurnCommand("End turn", game, input, numInput));
    }

    /**
     * Handles the behaviour of the game in the main menu.
     */
    private boolean mainMenu() {
        print("Welcome to Risk Kellogg's");
        printOptions(
            "Play a new game of Risk Kellogg's",
            "Get information about Risk",
            "Exit the game"
        );
        int choice = numInput.nextInt();
        switch (choice) {
            case 1:
                return true;
            case 2:
                printInfo();
                return false;
            case 3:
                this.game.setStatus(GameStatus.EXIT);
                break;
            default:
                return false;
        }
        return false;
    }

    /**
     * Handles the behaviour of the game while setting up the game.
     */
    private boolean setupGame() {
        /*
        The setup of the game consists of:
            - Create the players
            - Give the armies to the players
            - Make the players move the armies in the territories he wants
         */
        String name;
        do {
            print("Insert your in-game name (0-15 characters):");
            name = input.nextLine();
        }
        while (name.length() < 1 || name.length() > 15);
        ArrayList<Player> players =
            Player.generatePlayersRandomly((byte) 6, (byte) 1, new String[] {name});
        for (Player player : players) {
            game.addPlayer(player);
        }

        game.initArmies();

        print("Every player rolls a die, the player with the highest value starts first");
        ArrayList<Byte> rolls = new ArrayList<>();
        int maxIndex = 0;
        for (int i = 0; i < game.getPlayers().size(); i++) {
            rolls.add(Die.casualRoll());
            if (rolls.get(i) > rolls.get(maxIndex)) {
                maxIndex = i;
            }
            String who = "";
            String colorString = game.getPlayers().get(i).getColor().toString();
            if (game.getPlayers().get(i).isAI()) {
                who = colorString;
            } else {
                who = game.getPlayers().get(i).getName() + " (" + colorString + ")";
            }
            printFormat("%-30s %-5s\n", who + ":", rolls.get(i).toString());
        }
        print(game.getPlayers().get(maxIndex) + " start" + (game.getPlayers().get(maxIndex).isAI() ? "s" : ""));
        game.setTurn(maxIndex);
        game.setPlayerStarting(game.getPlayers().get(maxIndex));

        consolePause(input);

        boolean finishedTerritories = false;
        while (!game.getPlayers().stream().allMatch(player -> player.getFreeArmies().size() == 0)) {
            Player currentPlayer = game.getPlayers().get(game.getTurn());
            if (currentPlayer.getFreeArmies().size() > 0) {
                clearConsole();
                if (currentPlayer.isAI()) {
                    ((AI) currentPlayer).placeArmy(game, !finishedTerritories);
                    //print(currentPlayer.getColor().toString() + " chose " + chosen.getName().toString());
                } else {
                    placeArmies(!finishedTerritories);
                }
            }
            finishedTerritories = game.getBoard().getTerritories()
                .stream().filter(t -> t.getOwner() == null).count() == 0;
            game.nextTurn();
        }

        game.setTurn(maxIndex);
        game.setTurnsPlayed(0);
        return true;
    }

    private void placeArmies(boolean freeTerritories) {
        printMap(game);

        Player currentPlayer = game.getPlayers().get(game.getTurn());


        String chosenName = askTerritory(
            "Enter the territory where you want to place your armies: ",
            input,
            (tn) -> ((freeTerritories && game.getBoard().getTerritories().get(game.getBoard().getTerritoryIdx(tn))
                .getOwner() == null)
                || (!freeTerritories && game.getBoard().getTerritories().get(game.getBoard().getTerritoryIdx(tn))
                .getOwner() == currentPlayer)),
            game.getBoard());

        int armies = 1;
        if (!freeTerritories) {
            armies = askNumber("Enter the amount of armies you want to place"
                    + (freeTerritories ? "" : " (You have " + currentPlayer.getFreeArmies().size() + " free armies)"),
                numInput, 1, currentPlayer.getFreeArmies().size(), null);
        }

        Territory territory = game.getBoard().getTerritories().get(game.getBoard().getTerritoryIdx(chosenName));
        if ((territory.getOwner() == currentPlayer && !freeTerritories)
            || territory.getOwner() == null) {
            currentPlayer.placeArmies(territory, armies);
        }
    }

    /**
     * Handles the behaviour of the game while it is being played.
     */
    private boolean playing() {
        Player player = game.getPlayers().get(game.getTurn());
        int numOfTerritories = player.getTerritories().size();
        if (player.isAI()) {
            ((AI) player).attack(game.getBoard(), new Callback() {
                @Override
                public void onPlayerAttacked(Player attacker, Player attacked,
                                             Territory fromTerritory, Territory attackedTerritory) {
                    print(player + " is attacking you in "
                        + attackedTerritory.getName().toString() + "!");
                    print("How many armies do you want to defend with (max. "
                        + Math.min(attackedTerritory.getArmiesCount(), 3)
                        + ")?");
                    int defend = numInput.nextInt();
                    int[] losses = attacker.getAttackOutcome(fromTerritory, attackedTerritory,
                        Math.min(fromTerritory.getArmiesCount(), 3), defend);
                    print(
                        attacker + " lost " + losses[0] + " armies",
                        attacked + " lost " + losses[1] + " armies"
                    );
                    consolePause(input);
                }

                @Override
                public void onAIAttacked(Player attacker, Player attacked,
                                         Territory fromTerritory, Territory attackedTerritory) {
                    if (fromTerritory.getArmiesCount() < 2) {
                        return;
                    }
                    attacker.getAttackOutcome(fromTerritory, attackedTerritory,
                        Math.min(fromTerritory.getArmiesCount() - 1, 3),
                        Math.min(attackedTerritory.getArmiesCount(), 3));
                }
            });
        } else {
            print("----- IT'S YOUR TURN -----");
            if (game.getTurnsPlayed() > 0) {
                playingSetup();
                consolePause(input);
            }
            boolean endTurn = false;
            while (!endTurn) {
                printMap(game);
                print();
                printOptions(
                    commands.stream().map(Command::getName).collect(Collectors.toList())
                );
                int choice = numInput.nextInt();
                Command chosen = commands.get(choice - 1);
                endTurn = chosen.execute();
            }
            if (player.getTerritories().size() > numOfTerritories) {
                print("You conquered "
                    + (player.getTerritories().size() - numOfTerritories)
                    + " territories, so you can pick a card!");
                Card pickedCard = player.pickCard(game);
                print("You picked the card: " + pickedCard.toString());
                consolePause(input);
            }
        }
        game.nextTurn();
        // if the worls is conquered it needs to go on, otherwise keep playing
        return this.game.isWorldConquered();
    }

    private void playingSetup() {
        Player player = game.getPlayers().get(game.getTurn());
        ArrayList<Card[]> combinations = player.getCardCombinations();
        int choice = -1;
        if (combinations.size() > 0) {
            print("You have the following card combinations:");
            for (int i = 0; i < combinations.size(); i++) {
                print((i + 1)
                    + ": "
                    + combinations.get(i)[0].toString()
                    + ", "
                    + combinations.get(i)[1].toString()
                    + ", "
                    + combinations.get(i)[2].toString());
            }
            do {
                print("Which one do you want to use? (-1 to use none)");
                choice = numInput.nextInt();
            }
            while (choice < -1 || choice > combinations.size());
        }
        Card[] playedCombination = null;
        if (choice != -1) {
            playedCombination = combinations.get(choice - 1);
        }
        int[] bonus = game.giveBonus(player, choice);

        print("You got " + bonus[0] + " armies because you own " + player.getTerritories().size() + " territories.");

        ArrayList<Continent> continents = player.getContinents(game);
        if (continents.size() > 0) {
            String continentStr = continents.stream().map(Continent::toString).collect(Collectors.joining(", "));
            print("You got " + bonus[1] + " armies because you own " + continentStr + ".");
        }

        if (choice != -1) {
            String cardsStr = playedCombination[0].getType().toString()
                + ", " + playedCombination[1].getType().toString()
                + ", " + playedCombination[2].getType().toString();
            print("You got " + bonus[2] + " armies because you played the combination: " + cardsStr + ".");
        }

        print("You have " + player.getFreeArmies().size() + " armies to place.");

        consolePause(input);
        while (player.getFreeArmies().size() > 0) {
            placeArmies(false);
        }

    }

    /**
     * Handles the behaviour of the game after the game is over.
     */
    private boolean end() {
        print("The game is over.");
        printOptions("Player another game.", "Exit the game");
        int choice = numInput.nextInt();
        // Wants to play again.
        return choice != 1;
    }

    /**
     * Procedure - handles game flow.
     */
    public void run() {
        this.game.play(new GameCallback() {
            @Override
            public boolean onMainMenu() {
                return mainMenu();
            }

            @Override
            public boolean onGameSetup() {
                return setupGame();
            }

            @Override
            public boolean onGamePlay() {
                return playing();
            }

            @Override
            public boolean onGamePause() {
                return true;
            }

            @Override
            public boolean onGameEnd() {
                boolean playAgain = end();
                if (playAgain) {
                    game = new Game();
                    game.play(this);
                }
                return true;
            }

            @Override
            public void onGameExit() {

            }
        });
    }

    /**
     * Procedure - static main method.
     *
     * @param args arguments to set terminal interface
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}
