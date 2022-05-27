package model;

import model.enums.GameStatus;

public interface StatusListener {
    void changed(final GameStatus status);
}
