package com.tower.game.utils;

public class UniversalTimer {
    private int value;
    private int maxValue;



    public UniversalTimer(int maxValue){
        this.value = 0;
        this.maxValue = maxValue;
    }

    public int getValue() {
        return value;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isReady(){
        return value == 0;
    }

    public float getAsPercentage(){
        return 1.0f - ((float) value / maxValue);
    }

    public void progress(){
        if (value > 0){
            value--;
        }
    }
    public void reset(){
        value = maxValue;
    }
}
