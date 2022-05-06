package model.enums;

/**
 * Describes the color of the army piece.
 * @author dallem@usi.ch
 */
public enum ArmyColor {
    RED("\033[0;31m"),
    BLACK("\033[0;30m"),
    BLUE("\033[0;34m"),
    GREEN("\033[0;32m"),
    YELLOW("\033[0;33m"),
    PINK("\033[0;35m");

    private String colorCode;

    ArmyColor(String code) {
        this.colorCode = code;
    }

    public String getColorCode() {
        return this.colorCode;
    }

    public String getReset() {
        return "\033[0m";
    }
}
