package com.avatar_v2.dao;

import com.avatar_v2.dto.FarmDto;
import com.avatar_v2.dto.InFarm;
import com.avatar_v2.entity.Farm;

import java.util.List;

public interface FarmDao {
    Farm            findById    (Long id)                                       throws DaoException;
    FarmDto         findDtoById (Long id)                                       throws DaoException;

    void            addAnimal   (Long farmId, Long animalId, Long quantity)     throws DaoException;
    void            addSeed     (Long farmId, Long seedId, Long quantity)       throws DaoException;
    void            update      (Long id, Farm farm)                            throws DaoException;

    List<InFarm>    farmView    (Long farmID)                                   throws DaoException;
    List<Long>      harvest     (int farmId)                                    throws DaoException;
}
