package com.avatar_v2.dto;

public class InFarm {
    private String avatarObjName;
    private Double status;

    public InFarm() {
        this.status = Double.valueOf(100);
    }

    public String getAvatarObjName() {
        return avatarObjName;
    }

    public void setAvatarObjName(String avatarObjName) {
        this.avatarObjName = avatarObjName;
    }

    public Double getStatus() {
        return status;
    }

    public void setStatus(Double status) {
        this.status = status;
    }
}
