package com.avatar_v2.service;

import com.avatar_v2.dao.AccountDao;
import com.avatar_v2.dao.DaoFactory;
import com.avatar_v2.entity.Account;

import java.util.List;

public class AccountService {
    private AccountDao accountDao;

    public AccountService() {
        this.accountDao = DaoFactory.getInstance().getAccountDao();
    }


    public boolean createAccount(Account account)                   {
        return this.accountDao.save(account);
    }

    public Account          getAccountByUsername(String username)   {
        return this.accountDao.findByUsername(username);
    }
    public Account          getAccountByFarmerId(Long id)           {
        return this.accountDao.findByFarmerId(id);
    }
    public List<Account>    getAllAccount       ()                  {
        return this.accountDao.findAll();
    }

    public void updateDateCreate(Long farmerId, String dateCreate)  {
        this.accountDao.updateDateCreate(farmerId, dateCreate);
    }
}
