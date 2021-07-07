package com.avatar_v2.dao;

import com.avatar_v2.entity.Account;

import java.util.List;

public interface AccountDao {
    boolean         save            (Account account)                   throws DaoException;
    Account         findByUsername  (String username)                   throws DaoException;
    Account         findByFarmerId  (Long id)                           throws DaoException;
    List<Account>   findAll         ()                                  throws DaoException;
    void            updateDateCreate(Long farmerId, String dateCreate)  throws DaoException;
}
