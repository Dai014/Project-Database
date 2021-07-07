package com.avatar_v2.dao;

public class DaoException extends RuntimeException{
    public DaoException(String mess)                    {
        super(mess);
    }

    public DaoException(String mess, Throwable cause)   {
        super(mess, cause);
    }

    public DaoException(Throwable cause)                {
        super(cause);
    }
}
