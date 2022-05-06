package model;

/**
 * Interface to create event when a Player gets attacked (including AI).
 * @author dallem@usi.ch
 */
public interface Callback {
    /**
     * Event - trigger when player is attacked.
     * @param attacker The player who attacks.
     * @param attacked The player being attacked.
     * @param fromTerritory The territory from where the attacker attacks.
     * @param attackedTerritory The territory attacked by the attacker.
     */
    void onPlayerAttacked(Player attacker, Player attacked,
                          Territory fromTerritory, Territory attackedTerritory);

    /**
     * Event - trigger when AI is attacked.
     * @param attacker The player who attacks.
     * @param attacked The player being attacked.
     * @param fromTerritory The territory from where the attacker attacks.
     * @param attackedTerritory The territory attacked by the attacker.
     */
    void onAIAttacked(Player attacker, Player attacked,
                      Territory fromTerritory, Territory attackedTerritory);
}
