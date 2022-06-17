package com.example.roomwebapp.enums;

public enum Position {
    TEACHING,SECURITY, CLEANING, COOKING;


    public String toString() {
        switch (this){
            case TEACHING:
                return "Teaching";
            case SECURITY:
                return "Security";
            case CLEANING:
                return "Cleaning";
            case COOKING:
                return "Cooking";
        }
        return "";
    }
}
