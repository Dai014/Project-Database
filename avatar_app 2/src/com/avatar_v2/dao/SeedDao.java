package com.avatar_v2.dao;

import com.avatar_v2.dto.SeedDto;

public interface SeedDao {
    SeedDto findDtoById (Long id)           throws DaoException;
    void    save        (SeedDto seedDto)   throws DaoException;
}
