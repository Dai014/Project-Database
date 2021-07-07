package com.avatar_v2.dto;

public class SeedDto {
    private Long seed_id;
    private String name;
    private Long unit;
    private String growTime;
    private Double cost;
    private Double saleprice;

    public Long getSeed_id() {
        return seed_id;
    }

    public void setSeed_id(Long seed_id) {
        this.seed_id = seed_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUnit() {
        return unit;
    }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public String getGrowTime() {
        return growTime;
    }

    public void setGrowTime(String growTime) {
        this.growTime = growTime;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(Double saleprice) {
        this.saleprice = saleprice;
    }
}
