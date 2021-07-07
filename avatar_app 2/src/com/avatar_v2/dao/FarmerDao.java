package com.avatar_v2.dao;

import com.avatar_v2.entity.Farmer;

public interface FarmerDao {
    boolean save        (Farmer farmer)             throws DaoException;
    Farmer  findById    (Long id)                   throws DaoException;
    void    update      (Long id, Farmer farmer)    throws DaoException;
    void    updateLevel (Long id, Long level)       throws DaoException;
}
