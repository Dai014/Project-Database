package com.avatar_v2.entity;

public class Farm {
    private Long farmId;
    private Long freeFarmingLand;
    private Long freeFarmingPond;
    private Long freePlantingLand;

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    public Long getFreeFarmingLand() {
        return freeFarmingLand;
    }

    public void setFreeFarmingLand(Long freeFarmingLand) {
        this.freeFarmingLand = freeFarmingLand;
    }

    public Long getFreeFarmingPond() {
        return freeFarmingPond;
    }

    public void setFreeFarmingPond(Long freeFarmingPond) {
        this.freeFarmingPond = freeFarmingPond;
    }

    public Long getFreePlantingLand() {
        return freePlantingLand;
    }

    public void setFreePlantingLand(Long freePlantingLand) {
        this.freePlantingLand = freePlantingLand;
    }
}
