package com.nutribite.ai.dto;

import java.util.List;

public class OnboardingRequest {

    private String activity;
    private List<String> goals;
    private Height height;
    private String unit;
    private Integer age;

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public List<String> getGoals() {
        return goals;
    }

    public void setGoals(List<String> goals) {
        this.goals = goals;
    }

    public Height getHeight() {
        return height;
    }

    public void setHeight(Height height) {
        this.height = height;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static class Height {

        private Integer feet;
        private Integer inches;
        private Integer cm;

        public Integer getFeet() {
            return feet;
        }

        public void setFeet(Integer feet) {
            this.feet = feet;
        }

        public Integer getInches() {
            return inches;
        }

        public void setInches(Integer inches) {
            this.inches = inches;
        }

        public Integer getCm() {
            return cm;
        }

        public void setCm(Integer cm) {
            this.cm = cm;
        }
    }
}