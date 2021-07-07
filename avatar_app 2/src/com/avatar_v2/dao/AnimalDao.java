package com.avatar_v2.dao;

import com.avatar_v2.dto.AnimalDto;

public interface AnimalDao {
    AnimalDto   findDtoById (Long id)               throws DaoException;
    void        save        (AnimalDto animalDto)   throws DaoException;
}
