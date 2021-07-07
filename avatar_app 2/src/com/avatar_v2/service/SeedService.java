package com.avatar_v2.service;

import com.avatar_v2.dao.DaoFactory;
import com.avatar_v2.dao.SeedDao;
import com.avatar_v2.dto.SeedDto;

public class SeedService {
    private SeedDao seedDao;

    public SeedService() {
        this.seedDao = DaoFactory.getInstance().getSeedDao();
    }


    public SeedDto  findDtoById (Long id) {
        return this.seedDao.findDtoById(id);
    }
    public void     save        (SeedDto seedDto) {
        this.seedDao.save(seedDto);
    }
    public void     update      (Long seedId, SeedDto seedDto) {
    }
}
