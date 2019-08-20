package com.blackbeard.teach.models;

import com.blackbeard.teach.controllers.GameController;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapModel {
    private final GameController gameController;

    public MapModel(GameController gameController) {
        this.gameController = gameController;
    }

    public void drawMap() {
        int wholeArea;

        wholeArea = gameController.getWidth() + 2;
        gameController.setMapView(new char[wholeArea][wholeArea]);
        for (int y = 0; y < wholeArea; y++) {
            for (int x = 0; x < wholeArea; x++) {
                gameController.getMapView()[y][x] = ' ';
            }
        }
        gameController.getMapView()[gameController.getPlayerModel().getY() + 1][gameController.getPlayerModel().getX() + 1] = 'P';
        for (PlayerModel tempEnemy : gameController.getEnemies()) {
            gameController.getMapView()[tempEnemy.getY() + 1][tempEnemy.getX() + 1] = ' ';
        }
        for (int y = 0; y < wholeArea; y++) {
            gameController.getMapView()[y][0] = '*';
            gameController.getMapView()[y][wholeArea - 1] = '*';
            if (y == 0 || (y == wholeArea - 1)) {
                for (int x = 1; x < (wholeArea - 1); x++) {
                    gameController.getMapView()[y][x] = '*';
                }
            }
        }
    }

    public char[][] getMapView() {
        return gameController.getMapView();
    }
}