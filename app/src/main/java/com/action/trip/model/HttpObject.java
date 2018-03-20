package com.action.trip.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by hanyuezi on 17/12/7.
 */
@JsonObject
public class HttpObject {

    @JsonField
    private List<TripModel> list;

    public List<TripModel> getList() {
        return list;
    }

    public void setList(List<TripModel> list) {
        this.list = list;
    }
}
