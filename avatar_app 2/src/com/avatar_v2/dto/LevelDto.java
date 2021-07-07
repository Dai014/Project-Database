package com.avatar_v2.dto;

public class LevelDto {
    private Long levelId;
    private Long farmingLand;
    private Long farmingPond;
    private Long plantingLand;
    private Double costToUp;

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public Long getFarmingLand() {
        return farmingLand;
    }

    public void setFarmingLand(Long farmingLand) {
        this.farmingLand = farmingLand;
    }

    public Long getFarmingPond() {
        return farmingPond;
    }

    public void setFarmingPond(Long farmingPond) {
        this.farmingPond = farmingPond;
    }

    public Long getPlantingLand() {
        return plantingLand;
    }

    public void setPlantingLand(Long plantingLand) {
        this.plantingLand = plantingLand;
    }

    public Double getCostToUp() {
        return costToUp;
    }

    public void setCostToUp(Double costToUp) {
        this.costToUp = costToUp;
    }
}
