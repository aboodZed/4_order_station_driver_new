package com.AbdUlla.a4_order_station_driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoObject extends Message {

    @SerializedName("file_name")
    @Expose
    private String file_name;

    @SerializedName("file_path")
    @Expose
    private String file_path;

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    @Override
    public String toString() {
        return "PhotoObject{" +
                ", file_name='" + file_name + '\'' +
                ", file_path='" + file_path + '\'' +
                '}';
    }
}
