package com.avatar_v2.service;

import com.avatar_v2.dao.DaoFactory;
import com.avatar_v2.dao.GameDao;
import com.avatar_v2.dto.*;
import com.avatar_v2.entity.Account;
import com.avatar_v2.entity.Farmer;

import java.util.List;
import java.util.Map;

public class GameService {
    private final FarmerSevice    farmerSevice;
    private final AccountService  accountService;
    private final AnimalService   animalService;
    private final SeedService     seedService;
    private final FarmService     farmService;

    private final GameDao         gameDao;

    public GameService() {
        this.farmService = new FarmService();
        this.farmerSevice = new FarmerSevice();
        this.accountService = new AccountService();
        this.animalService = new AnimalService();
        this.seedService = new SeedService();
        this.gameDao = DaoFactory.getInstance().getGameDao();
    }


    public boolean  registration    (Map<String, Object> request)                   {
    Farmer farmer = new Farmer();
    farmer.setName(((String) request.get("name")));
    farmer.setGender(((String) request.get("gender")));
    farmer.setAddress(((String) request.get("address")));
    farmer.setDateOfBirth(((String) request.get("date_of_birth")));

    this.farmerSevice.createFarmer(farmer);

    Account account = new Account();
    account.setUsername(((String) request.get("username")));
    account.setPassword(((String) request.get("password")));
    account.setFarmerId(farmer.getFarmerId());

    this.accountService.createAccount(account);

    return true;
}
    public Player   login           (Map<String, Object> request)                   {
    Account account = this.accountService.getAccountByUsername(((String) request.get("username")));
    Player player = new Player();

    if (account != null && account.getPassword().equals((request.get("password")))) {
        player.setUsername(account.getUsername());
        if (account.getUsername().equals("admin") && account.getPassword().equals("admin")) {
            player.setAdmin(true);
        } else {
            player.setAdmin(false);
            Farmer farmer = this.farmerSevice.findById(account.getFarmerId());
            player.setFarmer(farmer);
            player.setFarm(this.farmService.findById(farmer.getFarmId()));
        }
    } else {
        return null;
    }

    return player;
}
    public void     logout          (Player player)                                 {
        this.farmService.update(player.getFarm().getFarmId(), player.getFarm());
        this.farmerSevice.update(player.getFarmer().getFarmerId(), player.getFarmer());
    }

    public void     harvest         (Player player)                                 {
        List<Long> avatarObjectIdList = this.farmService.harvest(player.getFarm().getFarmId().intValue());
        if (avatarObjectIdList == null) {
            return;
        }

        for (Long avatarObjectId : avatarObjectIdList) {
            AnimalDto animalDto = this.animalService.findDtoById(avatarObjectId);
            if (animalDto == null) {
                SeedDto seedDto = this.seedService.findDtoById(avatarObjectId);
                player.getFarmer().setWallet(player.getFarmer().getWallet() + seedDto.getSaleprice());
                player.getFarm().setFreePlantingLand(player.getFarm().getFreePlantingLand() + seedDto.getUnit());
            } else {
                player.getFarmer().setWallet(player.getFarmer().getWallet() + animalDto.getSaleprice());
                if (animalDto.getHabitat().equals("land")) {
                    player.getFarm().setFreeFarmingLand(player.getFarm().getFreeFarmingLand() +
                            animalDto.getUnit());
                } else {
                    player.getFarm().setFreeFarmingPond(player.getFarm().getFreeFarmingPond() +
                            animalDto.getUnit());
                }
            }
        }
    }
    public void     upLevel         (Player player)                                 {
        LevelDto oldLevel = this.gameDao.getLevelDto(player.getFarmer().getLevelId());
        LevelDto newLevel = this.gameDao.getLevelDto(player.getFarmer().getLevelId() + 1);

        if (player.getFarmer().getWallet() >= oldLevel.getCostToUp() && newLevel != null) {
            player.getFarmer().setWallet(player.getFarmer().getWallet() - oldLevel.getCostToUp());
            player.getFarmer().setLevelId(newLevel.getLevelId());
            player.getFarm().setFreeFarmingLand(player.getFarm().getFreeFarmingLand() +
                    newLevel.getFarmingLand() - oldLevel.getFarmingLand());
            player.getFarm().setFreeFarmingPond(player.getFarm().getFreeFarmingPond() +
                    newLevel.getFarmingPond() - oldLevel.getFarmingPond());
            player.getFarm().setFreePlantingLand(player.getFarm().getFreePlantingLand() +
                    newLevel.getPlantingLand() - oldLevel.getPlantingLand());
        }
    }

    public void     animalShopping  (Player player, Map<String, Long> animalOrder)  {
        AnimalDto animalDto = this.animalService.findDtoById(animalOrder.get("id"));

        if (animalDto == null) {
            return;
        }

        if (animalDto.getCost() * animalOrder.get("quantity") <= player.getFarmer().getWallet()) {
            switch (animalDto.getHabitat()) {
                case "land":
                    if (animalDto.getUnit() * animalOrder.get("quantity") <= player.getFarm().getFreeFarmingLand()) {
                        this.farmService.addAnimal(player.getFarm().getFarmId(),
                                animalOrder.get("id"), animalOrder.get("quantity"));
                        player.getFarm().setFreeFarmingLand(player.getFarm().getFreeFarmingLand() -
                                animalDto.getUnit() * animalOrder.get("quantity"));
                        player.getFarmer().setWallet(player.getFarmer().getWallet() -
                                animalDto.getCost() * animalOrder.get("quantity"));
                    }
                    break;
                case "pond":
                    if (animalDto.getUnit() * animalOrder.get("quantity") <= player.getFarm().getFreeFarmingPond()) {
                        this.farmService.addAnimal(player.getFarm().getFarmId(),
                                animalOrder.get("id"), animalOrder.get("quantity"));
                        player.getFarm().setFreeFarmingPond(player.getFarm().getFreeFarmingPond() -
                                animalDto.getUnit() * animalOrder.get("quantity"));
                        player.getFarmer().setWallet(player.getFarmer().getWallet() -
                                animalDto.getCost() * animalOrder.get("quantity"));
                    }
                    break;
            }
        }
    }
    public void     seedShopping    (Player player, Map<String, Long> seedOrder)    {
        SeedDto seedDto = this.seedService.findDtoById(seedOrder.get("id"));
        if (seedDto == null) {
            return;
        }

        if (seedDto.getCost() * seedOrder.get("quantity") <= player.getFarmer().getWallet() &&
                seedDto.getUnit() * seedOrder.get("quantity") <= player.getFarm().getFreePlantingLand()) {
            this.farmService.addSeed(player.getFarm().getFarmId(),
                    seedOrder.get("id"), seedOrder.get("quantity"));
            player.getFarmer().setWallet(player.getFarmer().getWallet() -
                    seedDto.getCost() * seedOrder.get("quantity"));
            player.getFarm().setFreePlantingLand(player.getFarm().getFreePlantingLand() -
                    seedDto.getUnit() * seedOrder.get("quantity"));
        }
    }

    public List<InFarm>     getFarm      (Long farmID)  {
        return this.farmService.farmView(farmID);
    }
    public List<AnimalDto>  getAllAnimal ()             {
        return this.gameDao.getAllAnimal();
    }
    public List<SeedDto>    getAllSeed   ()             {
        return this.gameDao.getAllSeed();
    }
    public LevelDto         getLevelDto  (Long levelId) {
        return this.gameDao.getLevelDto(levelId);
    }

    public void addNewSeed   (SeedDto seedDto)     {
        this.gameDao.addSeed(seedDto);
    }
    public void addNewAnimal (AnimalDto animalDto) {
        this.gameDao.addAnimal(animalDto);
    }
    public Long addNewLevel  (LevelDto levelDto)   {
        return this.gameDao.addNewLevel(levelDto);
    }

    public void updateAnimal(Map<String, Object> request) {
        AnimalDto animalDto = new AnimalDto();
        animalDto.setAnimal_id(((Long) request.get("id")));
        animalDto.setName(((String) request.get("name")));
        animalDto.setHabitat(((String) request.get("habitat")));
        animalDto.setUnit(((Long) request.get("unit")));
        animalDto.setGrowTime(((String) request.get("grow_time")));
        animalDto.setCost(((Double) request.get("cost")));
        animalDto.setSaleprice(((Double) request.get("saleprice")));

        this.animalService.update(animalDto.getAnimal_id(), animalDto);
    }
    public void updateSeed  (Map<String, Object> request) {
        SeedDto seedDto = new SeedDto();
        seedDto.setSeed_id(((Long) request.get("id")));
        seedDto.setName(((String) request.get("name")));
        seedDto.setUnit(((Long) request.get("unit")));
        seedDto.setGrowTime(((String) request.get("grow_time")));
        seedDto.setCost(((Double) request.get("cost")));
        seedDto.setSaleprice(((Double) request.get("saleprice")));

        this.seedService.update(seedDto.getSeed_id(), seedDto);
    }
    public void updateLevel (Map<String, Object> request) {

    }

    public void deleteAnimal(Long animalId) {
        this.gameDao.deleteAnimal(animalId);
    }
    public void deleteSeed  (Long seedId)   {
    }
    public void deleteLevel (Long levelId)  {
    }


// for insert data into database
//    public Account getAccountByUsername(String username) {
//        this.accountService = new AccountService();
//        return this.accountService.getAccountByUsername(username);
//    }
//    public Account getAccountByFarmerId(Long id)         {
//        this.accountService = new AccountService();
//        return this.accountService.getAccountByFarmerId(id);
//    }
//
//    public void updateDateCreate        (Long farmerId, String dateCreate) {
//        this.accountService = new AccountService();
//        this.accountService.updateDateCreate(farmerId, dateCreate);
//    }
//    public void updateDateTransactional (Long id, String date)             {
//        this.gameDao.updateDateTransactional(id, date);
//    }
}
