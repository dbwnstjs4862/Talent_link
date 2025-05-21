package com.talentlink.talentlink.talentrequest.dto;

import java.time.LocalDateTime;

public class TalentRequestRequest {
    private String title;
    private String description;
    private int budget;
    private LocalDateTime deadline;

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getBudget() { return budget; }
    public LocalDateTime getDeadline() { return deadline; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setBudget(int budget) { this.budget = budget; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }
}
