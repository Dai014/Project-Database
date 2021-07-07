package com.avatar_v2.service;

import com.avatar_v2.dao.AnimalDao;
import com.avatar_v2.dao.DaoFactory;
import com.avatar_v2.dto.AnimalDto;

public class AnimalService {
    private AnimalDao animalDao;

    public AnimalService() {
        this.animalDao = DaoFactory.getInstance().getAnimalDao();
    }


    public AnimalDto    findDtoById (Long id)                               {
        return this.animalDao.findDtoById(id);
    }
    public void         save        (AnimalDto animalDto)                   {
        this.animalDao.save(animalDto);
    }
    public void         update      (Long animalId, AnimalDto animalDto)    {

    }
}
