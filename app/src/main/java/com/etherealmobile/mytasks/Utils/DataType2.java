package com.etherealmobile.mytasks.Utils;

public class DataType2 {
    private String type = "";
    private String AUTO_INTEGER = " INTEGER AUTOINCREMENT ";

    public DataType2() {
    }

    public DataType2 _auto_int_() {
        this.type = this.type + this.AUTO_INTEGER;
        return this;
    }

    public String done() {
        return this.type;
    }
}
