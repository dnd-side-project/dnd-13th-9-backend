package com.example.dnd_13th_9_be.placeMemo.persistence;

public enum PlaceTag {

    ADVENTAGE("장점"),
    DISADVENTAGE("단점"),
    CONVENIENCE("편의 시설"),
    TRANSPORTATION("교통"),
    SECURITY("치안"),
    NOISE("소음");

    private final String tag;

    PlaceTag(String tag){
        this.tag = tag;
    }

    public String getTag(){
        return tag;
    }
}
