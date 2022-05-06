package model;

public interface Callback {
    void onPlayerAttacked(Player attacker, Player attacked,
                          Territory fromTerritory, Territory attackedTerritory);
    void onAIAttacked(Player attacker, Player attacked,
                      Territory fromTerritory, Territory attackedTerritory);
}
