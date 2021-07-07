package com.avatar_v2;

import com.avatar_v2.application.AvatarApp;
import com.avatar_v2.application.GameHelper;
import com.avatar_v2.controller.GameController;

public class Main {

    public static void main(String[] args) {
        AvatarApp avatarApp = new AvatarApp();

        avatarApp.run();

// insert data for database
//        GameController gameController = new GameController();
//        gameController.insertDataForAccountAndFarmerTable(10000, 4000);
//        gameController.updateDateCreateForAllAccount(10000, 14000);
//        gameController.updateWallet(6000, 26004, 200);
//        gameController.updateLevel(8000, 26004);
//        gameController.autoBuyAnimal(12000, 26004);
//        gameController.updateDateForTransactionalTable(8000);
//        gameController.autoBuySeed(8000, 26004);
//        gameController.autoHarvest(10000, 26004);
    }
}
