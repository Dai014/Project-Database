package com.avatar_v2.dto;

public class AnimalDto {
    private Long    animal_id;
    private String  name;
    private String  habitat;
    private Long    unit;
    private String  growTime;
    private Double  cost;
    private Double  saleprice;

    public Long     getAnimal_id    ()               {
        return animal_id;
    }

    public void     setAnimal_id    (Long animal_id) {
        this.animal_id = animal_id;
    }

    public String   getName         ()               {
        return name;
    }

    public void     setName         (String name)    {
        this.name = name;
    }

    public String   getHabitat      ()               {
        return habitat;
    }

    public void     setHabitat      (String habitat) {
        this.habitat = habitat;
    }

    public Long     getUnit         ()               {
        return unit;
    }

    public void     setUnit         (Long unit)      {
        this.unit = unit;
    }

    public String   getGrowTime     ()               {
        return growTime;
    }

    public void     setGrowTime     (String growTime) {
        this.growTime = growTime;
    }

    public Double   getCost         ()               {
        return cost;
    }

    public void     setCost         (Double cost)    {
        this.cost = cost;
    }

    public Double   getSaleprice    ()                 {
        return saleprice;
    }

    public void     setSaleprice    (Double saleprice) {
        this.saleprice = saleprice;
    }
}
