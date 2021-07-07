package com.avatar_v2.dao;

public class DaoConfigurationException extends RuntimeException{

    public DaoConfigurationException(String mess)                   {
        super(mess);
    }
    public DaoConfigurationException(String mess, Throwable cause)  {
        super(mess, cause);
    }
    public DaoConfigurationException(Throwable cause)               {
        super(cause);
    }
}
