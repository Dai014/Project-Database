package com.avatar_v2.service;

import com.avatar_v2.dao.DaoFactory;
import com.avatar_v2.dao.FarmDao;
import com.avatar_v2.dto.InFarm;
import com.avatar_v2.entity.Farm;

import java.util.List;

public class FarmService {
    private FarmDao farmDao;

    public FarmService() {
        this.farmDao = DaoFactory.getInstance().getFarmDao();
    }


    public Farm findById    (Long id) {
        return this.farmDao.findById(id);
    }

    public void addAnimal   (Long farmId, Long animalId, Long quantity) {
        this.farmDao.addAnimal(farmId, animalId, quantity);
    }
    public void addSeed     (Long farmId, Long seedId, Long quantity) {
        this.farmDao.addSeed(farmId, seedId, quantity);
    }

    public void         update  (Long id, Farm farm) {
        this.farmDao.update(id, farm);
    }
    public List<Long>   harvest (int farmId) {
        return this.farmDao.harvest(farmId);
    }
    public List<InFarm> farmView(Long farmId) {
        return this.farmDao.farmView(farmId);
    }
}
