package com.talentlink.talentlink.talent.dto;

public class TalentRequest {

    private String title;
    private String description;
    private int price;

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getPrice() { return price; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(int price) { this.price = price; }
}
