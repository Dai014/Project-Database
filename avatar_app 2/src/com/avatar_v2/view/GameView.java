package com.avatar_v2.view;

import com.avatar_v2.controller.GameController;
import com.avatar_v2.dto.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameView {
    private final Scanner scanner = new Scanner(System.in);
    private GameController gameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

// __ method __
    public Map<String, Object>  registrationForm    () {
    Map<String, Object> request = new HashMap<String, Object>();

    System.out.println("__________ information __________");
    System.out.print("name : ");
    request.put("name", this.scanner.next());
    System.out.print("gender (f/m) : ");
    request.put("gender", this.scanner.next());
    System.out.print("address : ");
    request.put("address", this.scanner.next());
    System.out.print("date of birth (YYYY-MM-DD): ");
    request.put("date_of_birth", this.scanner.next());
    System.out.println("__________ account __________");
    System.out.print("username : ");
    request.put("username", this.scanner.next());
    System.out.print("password : ");
    request.put("password", this.scanner.next());

    return request;
}
    public Map<String, Object>  loginForm           () {
        Map<String, Object> request = new HashMap<String, Object>();

        System.out.println("__________ login __________");
        System.out.print("username : ");
        request.put("username", this.scanner.next());
        System.out.print("password : ");
        request.put("password", this.scanner.next());

        return request;
    }
    public Map<String, Long>    orderForm           () {
        Map<String, Long> order = new HashMap<String, Long>();

        System.out.print("id : ");
        order.put("id", this.scanner.nextLong());
        System.out.print("quantity : ");
        order.put("quantity", this.scanner.nextLong());

        return order;
    }

    public AnimalDto            animalForm          () {
        AnimalDto animalDto = new AnimalDto();

        System.out.println("__________animal form__________");
        System.out.print("animal name : ");
        animalDto.setName(this.scanner.next());
        System.out.print("habitat (land/pond) : ");
        animalDto.setHabitat(this.scanner.next());
        System.out.print("unit : ");
        animalDto.setUnit(this.scanner.nextLong());
        System.out.print("grow time (hh:mm:ss): ");
        animalDto.setGrowTime(this.scanner.next());
        System.out.print("cost : ");
        animalDto.setCost(this.scanner.nextDouble());
        System.out.print("saleprice : ");
        animalDto.setSaleprice(this.scanner.nextDouble());

        return animalDto;
    }
    public Map<String, Object>  animalUpdateForm    () {
        Map<String, Object> request = new HashMap<String, Object>();

        System.out.print("id (-1 if add new): ");
        request.put("id", this.scanner.nextLong());
        System.out.print("animal name : ");
        request.put("name", this.scanner.next());
        System.out.print("habitat (pond/land): ");
        request.put("habitat", this.scanner.next());
        System.out.print("unit : ");
        request.put("unit", this.scanner.nextLong());
        System.out.print("grow time (hh-mm-ss): ");
        request.put("grow_time", this.scanner.next());
        System.out.print("cost : ");
        request.put("cost", this.scanner.nextDouble());
        System.out.print("saleprice : ");
        request.put("cost", this.scanner.nextDouble());

        return request;
    }
    public Long                 animalDeleteForm    () {
        System.out.println("__________delete animal__________");
        System.out.print("animal id : ");
        return this.scanner.nextLong();
    }

    public SeedDto              seedForm            () {
        SeedDto seedDto  = new SeedDto();

        System.out.println("__________seed form__________");
        System.out.print("seed name : ");
        seedDto.setName(this.scanner.next());
        System.out.print("unit : ");
        seedDto.setUnit(this.scanner.nextLong());
        System.out.print("grow time (hh:mm:ss): ");
        seedDto.setGrowTime(this.scanner.next());
        System.out.print("cost : ");
        seedDto.setCost(this.scanner.nextDouble());
        System.out.print("saleprice : ");
        seedDto.setSaleprice(this.scanner.nextDouble());

        return seedDto;
    }
    public Map<String, Object>  seedUpdateForm      () {
        Map<String, Object> request = new HashMap<String, Object>();

        System.out.print("id (-1 if add new): ");
        request.put("id", this.scanner.nextLong());
        System.out.print("seed name : ");
        request.put("name", this.scanner.next());
        System.out.print("unit : ");
        request.put("unit", this.scanner.nextLong());
        System.out.print("grow time (hh-mm-ss): ");
        request.put("grow_time", this.scanner.next());
        System.out.print("cost : ");
        request.put("cost", this.scanner.nextDouble());
        System.out.print("saleprice : ");
        request.put("cost", this.scanner.nextDouble());

        return request;
    }
    public Long                 seedDeleteForm      () {
        return null;
    }

    public LevelDto             levelForm           () {
        LevelDto levelDto = new LevelDto();

        System.out.println("__________level form__________");
        System.out.print("level : ");
        levelDto.setLevelId(this.scanner.nextLong());
        System.out.print("farming land space : ");
        levelDto.setFarmingLand(this.scanner.nextLong());
        System.out.print("farming pond space : ");
        levelDto.setFarmingPond(this.scanner.nextLong());
        System.out.print("planting land space : ");
        levelDto.setPlantingLand(this.scanner.nextLong());
        System.out.print("cost to up level : ");
        levelDto.setCostToUp(this.scanner.nextDouble());

        return levelDto;
    }
    public Map<String, Object>  levelUpdateForm     () {
        return null;
    }
    public Long                 levelDeleteForm     () {
        return null;
    }

    public int                  normalMenu          () {
        System.out.println("__________ menu __________");
        System.out.println("1. visit farm");
        System.out.println("2. animal shop");
        System.out.println("3. seed shop");
        System.out.println("4. logout");
        System.out.print("your choice : ");

        return this.scanner.nextInt();
    }
    public int                  adminMenu           () {
        System.out.println("__________ admin menu __________");
        System.out.println("1. show");
        System.out.println("2. add new");
        System.out.println("3. update");
        System.out.println("4. delete");
        System.out.println("5. back");
        System.out.print("your choice : ");

        return this.scanner.nextInt();
    }
    public int                  adminShowMenu       () {
        System.out.println("_________ show menu _________");
        System.out.println("1. animal view");
        System.out.println("2. seed view");
        System.out.println("3. level view");
        System.out.println("4. back");
        System.out.print("your choice : ");

        return this.scanner.nextInt();
    }
    public int                  adminAddNewMenu     () {
        System.out.println("_________ add new _________");
        System.out.println("1. animal");
        System.out.println("2. seed");
        System.out.println("3. level");
        System.out.println("4. back");
        System.out.print("your choice : ");

        return this.scanner.nextInt();
    }
    public int                  adminUpdateMenu     () {
        System.out.println("__________ update menu __________");
        System.out.println("1. animal");
        System.out.println("2. seed");
        System.out.println("3. level");
        System.out.println("4. back");
        System.out.print("your choice : ");

        return this.scanner.nextInt();
    }
    public int                  adminDeleteMenu     () {
        System.out.println("__________ delete menu __________");
        System.out.println("1. animal");
        System.out.println("2. seed");
        System.out.println("3. back");
        System.out.print("your choice : ");

        return this.scanner.nextInt();
    }

    public int  showFarm            (Player player, List<InFarm> inFarmList)        {
        System.out.println("___________________________");
        System.out.println("level  : " + player.getFarmer().getLevelId());
        System.out.println("wallet : " + player.getFarmer().getWallet());
        System.out.println("free farming land  : " + player.getFarm().getFreeFarmingLand());
        System.out.println("free farming pond  : " + player.getFarm().getFreeFarmingPond());
        System.out.println("free planting land : " + player.getFarm().getFreePlantingLand());
        System.out.println("---------------------------");

        if (inFarmList == null) {
            System.out.println("empty");
        } else {
            for (InFarm inFarm : inFarmList) {
                System.out.printf("%-20s %-5.2f (%%)\n", inFarm.getAvatarObjName(), inFarm.getStatus());
            }
        }
        System.out.println("___________________________");
        System.out.println("1. harvest");
        System.out.println("2. up level");
        System.out.println("3. go out");
        System.out.print("your choice : ");

        return this.scanner.nextInt();
    }
    public void showAnimalList      (List<AnimalDto> animalDtoList)                 {
        System.out.println("________________________________________________________________________________");
        System.out.printf("%-4s %-10s %-15s %-15s %-10s %-10s %-10s\n",
                "id", "name", "habitat", "grow time", "unit", "cost", "saleprice");
        System.out.println("--------------------------------------------------------------------------------");
        for (AnimalDto animalDto : animalDtoList) {
            System.out.printf("%-4s %-10s %-15s %-15s %-10s %-10s %-10s\n",
                    animalDto.getAnimal_id(), animalDto.getName(),
                    animalDto.getHabitat(), animalDto.getGrowTime(),
                    animalDto.getUnit(), animalDto.getCost(),
                    animalDto.getSaleprice());
        }
    }
    public void showSeedList        (List<SeedDto> seedDtoList)                     {
        System.out.println("__________________________________________________________________________");
        System.out.printf("%-4s %-10s %-15s %-10s %-10s %-10s\n",
                "id", "name", "grow time", "unit", "cost", "saleprice");
        System.out.println("--------------------------------------------------------------------------");
        for (SeedDto seedDto : seedDtoList) {
            System.out.printf("%-4s %-10s %-15s %-10s %-10s %-10s\n",
                    seedDto.getSeed_id(), seedDto.getName(),
                    seedDto.getGrowTime(), seedDto.getUnit(),
                    seedDto.getCost(), seedDto.getSaleprice());
        }
    }

    public int      shopping            ()                                          {
        System.out.println("______________________");
        System.out.println("1. shopping");
        System.out.println("2. back");
        System.out.print("your choice : ");

        return this.scanner.nextInt();
    }

    public int      upLevelConfirm      (Double costToUp)                           {
        System.out.println("cost : " + costToUp);
        System.out.print("up to level (1/0) ? ");

        return this.scanner.nextInt();
    }

    public void     notice              (String mess)                               {
        if (mess != null) {
            System.out.println("notice : " + mess);
        }
    }
// ________
}
