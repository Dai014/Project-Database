package com.avatar_v2.controller;

import com.avatar_v2.application.GameHelper;
import com.avatar_v2.dto.*;
import com.avatar_v2.service.GameService;
import com.avatar_v2.view.GameView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameController {
    private GameView    gameView;
    private GameService gameService;

    public GameController() {
        this.gameService = new GameService();
        this.gameView = new GameView();
        this.gameView.setGameController(this);
    }

// __ method __
    public void registration        () {
        Map<String, Object> request = this.gameView.registrationForm();

        if (this.gameService.registration(request)) {
            this.gameView.notice("registration is successful");
        } else {
            this.gameView.notice("failed to registration");
        }
    }

    public void login               ()              {
        Map<String, Object> request = this.gameView.loginForm();

        Player player = this.gameService.login(request);
        if (player == null) {
            this.gameView.notice("failed to login");
        } else if (player.isAdmin()) {
            this.gameView.notice("admin mode");
            this.loginAdminMode();
        } else {
            this.gameView.notice("login is successful");
            this.loginNormalMode(player);
        }
    }
    public void loginAdminMode      ()              {
        final int SHOW      = 1;
        final int ADD_NEW   = 2;
        final int UPDATE    = 3;
        final int DELETE    = 4;
        final int BACK      = 5;
        int event = 0;

        while (event != BACK) {
            event = this.gameView.adminMenu();
            switch (event) {
                case SHOW:
                    this.adminShow();
                    break;
                case ADD_NEW:
                    this.adminAddNew();
                    break;
                case UPDATE:
                    this.adminUpdate();
                    break;
                case DELETE:
                    this.adminDelete();
                    break;
                case BACK:
                    break;
                default:
                    this.gameView.notice("not valid, enter again");
            }
        }
    }
    public void loginNormalMode     (Player player) {
        final int VISIT_FARM    = 1;
        final int ANIMAL_SHOP   = 2;
        final int SEED_SHOP     = 3;
        final int LOGOUT        = 4;
        int event = 0;

        while (event != LOGOUT) {
            event = this.gameView.normalMenu();
            switch (event) {
                case VISIT_FARM:
                    this.visitFarm(player);
                    break;
                case ANIMAL_SHOP:
                    this.animalShopping(player);
                    break;
                case SEED_SHOP:
                    this.seedShopping(player);
                    break;
                case LOGOUT:
                    this.logout(player);
                    break;
                default:
                    this.gameView.notice("not valid, enter again");
            }
        }
    }
    public void logout              (Player player) {
        this.gameService.logout(player);
    }

    public void adminShow           ()              {
        final int SHOW_ANIMAL_VIEW  = 1;
        final int SHOW_SEED_VIEW    = 2;
        final int SHOW_LEVEL_VIEW   = 3;
        final int BACK              = 4;
        int event = 0;

        while (event != BACK) {
            event = this.gameView.adminShowMenu();
            switch (event) {
                case SHOW_ANIMAL_VIEW:
                    break;
                case SHOW_SEED_VIEW:
                    break;
                case SHOW_LEVEL_VIEW:
                    break;
                case BACK:
                    break;
                default:
                    this.gameView.notice("not valid, enter again");
            }
        }
    }
    public void adminAddNew         ()              {
        final int ADD_NEW_ANIMAL = 1;
        final int ADD_NEW_SEED   = 2;
        final int ADD_NEW_LEVEL  = 3;
        final int BACK           = 4;
        int event = 0;

        while (event != BACK) {
            event = this.gameView.adminAddNewMenu();
            switch (event) {
                case ADD_NEW_ANIMAL:
                    AnimalDto animalDto = this.gameView.animalForm();
                    this.gameService.addNewAnimal(animalDto);
                    break;
                case ADD_NEW_SEED:
                    SeedDto seedDto = this.gameView.seedForm();
                    this.gameService.addNewSeed(seedDto);
                    break;
                case ADD_NEW_LEVEL:
                    LevelDto levelDto = this.gameView.levelForm();
                    this.gameService.addNewLevel(levelDto);
                    break;
                case BACK:
                    break;
                default:
                    this.gameView.notice("not valid, enter again");
            }
        }
    }
    public void adminUpdate         ()              {
        final int UPDATE_ANIMAL = 1;
        final int UPDATE_SEED   = 2;
        final int UPDATE_LEVEL  = 3;
        final int BACK          = 4;
        int event = 0;

        Map<String, Object> request;
        while (event != BACK) {
            event = this.gameView.adminUpdateMenu();
            switch (event) {
                case UPDATE_ANIMAL:
                    request = this.gameView.animalUpdateForm();
                    this.gameService.updateAnimal(request);
                    break;
                case UPDATE_SEED:
                    request = this.gameView.seedUpdateForm();
                    this.gameService.updateSeed(request);
                    break;
                case UPDATE_LEVEL:
                    request = this.gameView.levelUpdateForm();
                    this.gameService.updateLevel(request);
                    break;
                case BACK:
                    break;
                default:
                    this.gameView.notice("not valid, enter again");
            }
        }
    }
    public void adminDelete         ()              {}

    public void visitFarm           (Player player) {
        final int HARVEST   = 1;
        final int UP_LEVEL  = 2;
        final int GO_OUT    = 3;
        int event = 0;

        while (event != GO_OUT) {
            event = this.gameView.showFarm(player, this.gameService.getFarm(player.getFarm().getFarmId()));
            switch (event) {
                case HARVEST:
                    this.gameService.harvest(player);
                    break;
                case UP_LEVEL:
                    LevelDto levelDto = this.gameService.getLevelDto(player.getFarmer().getLevelId());
                    int confirm = this.gameView.upLevelConfirm(levelDto.getCostToUp());
                    if (confirm == 1) {
                        this.gameService.upLevel(player);
                    } else {
                        this.gameView.notice("failed to up level");
                    }
                    break;
                case GO_OUT:
                    break;
                default:
                    this.gameView.notice("not valid, enter again");
            }
        }
    }

    public void animalShopping      (Player player) {
        final int SHOPPING  = 1;
        final int BACK      = 2;
        int event = 0;

        this.getAllAnimal();
        while (event != BACK) {
            event = this.gameView.shopping();
            switch (event) {
                case SHOPPING:
                    Map<String, Long> animalOrder = this.gameView.orderForm();
                    this.gameService.animalShopping(player, animalOrder);
                    break;
                case BACK:
                    break;
                default:
                    this.gameView.notice("not valid, enter again");
            }
        }
    }
    public void seedShopping        (Player player) {
        final int SHOPPING  = 1;
        final int BACK      = 2;
        int event = 0;

        this.getAllSeed();
        while (event != BACK) {
            event = this.gameView.shopping();
            switch (event) {
                case SHOPPING:
                    Map<String, Long> seedOrder = this.gameView.orderForm();
                    this.gameService.seedShopping(player, seedOrder);
                    break;
                case BACK:
                    break;
                default:
                    this.gameView.notice("not valid, enter again");
            }
        }
    }

    public void getAllAnimal        ()              {
        List<AnimalDto> animalDtoList = this.gameService.getAllAnimal();
        this.gameView.showAnimalList(animalDtoList);
    }
    public void getAllSeed          ()              {
        List<SeedDto> seedDtoList = this.gameService.getAllSeed();
        this.gameView.showSeedList(seedDtoList);
    }
// ________

// function to insert template data for database
//    public void insertDataForAccountAndFarmerTable(int number, int male) {
//        String user, pass, name, gender, address, dob;
//        Map<String, Object> request = new HashMap<String, Object>();
//        Account account = null;
//        int i = 1;
//
//        while (i <= number) {
//            do {
//                user = GameHelper.randomString(10);
//                account = this.gameService.getAccountByUsername(user);
//            } while (account != null);
//            pass = GameHelper.randomString(10);
//            name = GameHelper.randomString(10);
//            if(i <= male) {
//                gender = "m";
//            } else {
//                gender = "f";
//            }
//            address = GameHelper.randomString(10);
//            dob = GameHelper.randomDate();
//
//            request.put("username", user);
//            request.put("password", pass);
//            request.put("name", name);
//            request.put("gender", gender);
//            request.put("address", address);
//            request.put("date_of_birth", dob);
//
//            this.gameService.registration(request);
//
//            System.out.println(i);
//            i++;
//        }
//    }
//    public void autoBuyAnimal(int iter, int maxFarmerId) {
//        int i = 1;
//        Random random = new Random();
//
//        while (i <= iter) {
//            Account account = this.gameService.getAccountByFarmerId(Long.valueOf(
//                    random.nextInt(maxFarmerId - 2) + 2));
//            if (account == null) {
//                continue;
//            }
//            Map<String, Object> request = new HashMap<String, Object>();
//            request.put("username", account.getUsername());
//            request.put("password", account.getPassword());
//            Player player = this.gameService.login(request);
//
//            Map<String, Long> order = new HashMap<String, Long>();
//            order.put("id", Long.valueOf(random.nextInt(11) + 20));
//            order.put("quantity", Long.valueOf(1));
//
//            this.gameService.animalShopping(player, order);
//            this.gameService.updateAfterExit(player);
//
//            System.out.println(i);
//            i++;
//        }
//    }
//    public void autoBuySeed(int iter, int maxFarmerId) {
//        int i = 1;
//        Random random = new Random();
//
//        while (i <= iter) {
//            Account account = this.gameService.getAccountByFarmerId(Long.valueOf(
//                    random.nextInt(maxFarmerId - 2) + 2));
//            if (account == null) {
//                continue;
//            }
//            Map<String, Object> request = new HashMap<String, Object>();
//            request.put("username", account.getUsername());
//            Player player = this.gameService.login(request);
//
//            Map<String, Long> order = new HashMap<String, Long>();
//            order.put("id", Long.valueOf(random.nextInt(14) + 24));
//            order.put("quantity", Long.valueOf(1));
//
//            this.gameService.seedShopping(player, order);
//            this.gameService.updateAfterExit(player);
//
//            System.out.println(i);
//            i++;
//        }
//    }
//    public void autoHarvest(int iter, int maxFarmerId) {
//        int i = 1;
//        Random random = new Random();
//
//        while (i <= iter) {
//            Account account = this.gameService.getAccountByFarmerId(Long.valueOf(
//                    random.nextInt(maxFarmerId - 2) + 2));
//            if (account == null) {
//                continue;
//            }
//            Map<String, Object> request = new HashMap<String, Object>();
//            request.put("username", account.getUsername());
//            request.put("password", account.getPassword());
//            Player player = this.gameService.login(request);
//
//            for (int j = 0; j < 2; j++) {
//                this.gameService.harvest(player);
//            }
//            this.gameService.updateAfterExit(player);
//
//            System.out.println(i);
//            i++;
//        }
//    }
//    public void updateDateCreateForAllAccount(int from, int maxFarmerID) {
//        Long farmerId = Long.valueOf(from);
//        while (farmerId <= maxFarmerID) {
//            this.gameService.updateDateCreate(farmerId, GameHelper.randomDate());
//            System.out.println(farmerId);
//            farmerId++;
//        }
//    }
//    public void updateDateForTransactionalTable(int iter) {
//        int i = 1;
//        Random random = new Random();
//        while (i <= iter) {
//            this.gameService.updateDateTransactional(Long.valueOf(random.nextInt(24000) + 1),
//                    GameHelper.randomDate());
//            System.out.println(i);
//            i++;
//        }
//    }
//    public void updateWallet(int iter, int maxFarmerID, int maxValue) {
//        Random random = new Random();
//        int i = 1;
//
//        while (i <= iter) {
//            Account account = this.gameService.getAccountByFarmerId(Long.valueOf(
//                    random.nextInt(maxFarmerID - 2) + 2));
//            if (account == null) {
//                System.out.println("next");
//                continue;
//            }
//            Map<String, Object> request = new HashMap<String, Object>();
//            request.put("username", account.getUsername());
//            request.put("password", account.getPassword());
//            Player player = this.gameService.login(request);
//
//            player.getFarmer().setWallet(player.getFarmer().getWallet() +
//                        Double.valueOf(random.nextInt(maxValue) + 1));
//            this.gameService.updateAfterExit(player);
//
//            System.out.println(i);
//            i++;
//        }
//    }
//    public void updateLevel(int iter, int maxFarmerId) {
//        Random random = new Random();
//        int i = 1;
//
//        while (i <= iter) {
//            Account account = this.gameService.getAccountByFarmerId(Long.valueOf(
//                    random.nextInt(maxFarmerId - 2) + 2));
//            if (account == null) {
//                System.out.println("next");
//                continue;
//            }
//            Map<String, Object> request = new HashMap<String, Object>();
//            request.put("username", account.getUsername());
//            request.put("password", account.getPassword());
//            Player player = this.gameService.login(request);
//
//            player.getFarmer().setLevelId(player.getFarmer().getLevelId() + 1);
//            this.gameService.updateAfterExit(player);
//
//            System.out.println(i);
//            i++;
//        }
//    }

}
