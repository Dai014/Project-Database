package com.avatar_v2.service;

import com.avatar_v2.dao.DaoFactory;
import com.avatar_v2.dao.FarmerDao;
import com.avatar_v2.entity.Account;
import com.avatar_v2.entity.Farmer;

public class FarmerSevice {
    private FarmerDao farmerDao;

    public FarmerSevice() {
        this.farmerDao = DaoFactory.getInstance().getFarmerDao();
    }


    public boolean  createFarmer    (Farmer farmer)             {
        return this.farmerDao.save(farmer);
    }
    public Farmer   findById        (Long farmerId)             {
        return this.farmerDao.findById(farmerId);
    }
    public void     update          (Long id, Farmer farmer)    {
        this.farmerDao.update(id, farmer);
    }
    public void     updateLevel     (Long id, Long level)       {
        this.farmerDao.updateLevel(id, level);
    }
}
