package com.blackbeard.teach.controllers;

import com.blackbeard.teach.models.PlayerModel;

import java.util.Iterator;
import java.util.Random;

class FightController {
    private final GameController gameController;
    static int enemyEncounter;
    private PlayerController fightController;

    FightController(GameController gameController) {
        this.gameController = gameController;
    }

    void positionPlayerRandomly(PlayerModel tempEnemy) {
        Random rand = new Random();
        boolean conflict;
        conflict = isConflict(tempEnemy, rand);
        while (conflict) {
            conflict = isConflict(tempEnemy, rand);
        }
    }

    private boolean isConflict(PlayerModel tempEnemy, Random rand) {
        int i;
        int maxEnemies;
        boolean conflict;
        i = -1;
        maxEnemies = gameController.getEnemies().size();
        tempEnemy.setPosition(rand.nextInt(gameController.getWidth()), rand.nextInt(gameController.getHeight()));
        conflict = ((tempEnemy.getX() == gameController.getPlayerModel().getX()) &&
                (tempEnemy.getY() == gameController.getPlayerModel().getY()));
        while (!conflict && ++i < maxEnemies) {
            conflict = ((tempEnemy.getX() == gameController.getEnemies().get(i).getX()) &&
                    tempEnemy.getY() == gameController.getEnemies().get(i).getY());
        }
        return conflict;
    }

    void simulateFight(PlayerModel fightEnemy) {
        String prepareFight;

        enemyEncounter = 0;
        gameController.setEnemyModel(fightEnemy);
        prepareFight = gameController.getPlayerModel().getName() + " vs " +
                fightEnemy.getName() + "\n";
        prepareFight += "======================================";
        gameController.getGameView().prepareFight(prepareFight);
        fightController = new PlayerController(gameController.getPlayerModel(), fightEnemy, gameController.getGameView(), gameController);
        fightController.simulateFight();
    }

    void fightOver() {
        if (gameController.getPlayerModel().getHP() > 0) {
            fightController.addExperience(gameController.getPlayerModel(), gameController.getEnemyModel());
        }
        if (gameController.getEnemyModel().getHP() <= 0) {
            for (Iterator<PlayerModel> iter = gameController.getEnemies().listIterator(); iter.hasNext(); ) {
                PlayerModel tempEnemy = iter.next();
                if (gameController.getEnemyModel() == tempEnemy) {
                    iter.remove();
                }
            }
        }
        if (gameController.getPlayerModel().getHP() > 0) {
            gameController.getGameView().gameLoop(gameController.getPlayerModel().getName() + " won the fight ", false);
            if (gameController.getCurrentLevel() != gameController.getPlayerModel().getLevel()) {
                gameController.setCurrentLevel(gameController.getPlayerModel().getLevel());
                gameController.updatePlay();
                gameController.getGameView().updateMap(gameController.getMapView());
                return;
            }
            gameController.drawMap();
            gameController.getGameView().updateMap(gameController.getMapView());
            return;
        }
        gameController.getGameView().gameLoop(gameController.getPlayerModel().getName() + " lost fight", true);
    }
}