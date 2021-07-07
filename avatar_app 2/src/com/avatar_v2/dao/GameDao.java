package com.avatar_v2.dao;

import com.avatar_v2.dto.*;

import java.util.List;

public interface GameDao {
    void addAnimal  (AnimalDto animalDto)   throws DaoException;
    void addSeed    (SeedDto seedDto)       throws DaoException;
    Long addNewLevel(LevelDto levelDto)     throws DaoException;

    AnimalDto       getAnimalById   (Long animalId) throws DaoException;
    SeedDto         getSeedById     (Long seedId)   throws DaoException;
    LevelDto        getLevelDto     (Long levelId)  throws DaoException;
    List<AnimalDto> getAllAnimal    ()              throws DaoException;
    List<SeedDto>   getAllSeed      ()              throws DaoException;

    void            deleteAnimal    (Long animalId) throws DaoException;

    void updateDateTransactional(Long id, String date) throws DaoException;
}
