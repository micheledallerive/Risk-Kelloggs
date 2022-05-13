package model.callback;

public interface GameCallback {
    boolean onMainMenu();
    boolean onGameSetup();
    boolean onGamePlay();
    boolean onGamePause();
    boolean onGameEnd();
    void onGameExit();
}
