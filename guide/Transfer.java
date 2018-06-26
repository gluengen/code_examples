package com.youhone.yjsboilingmachine.guide;

import java.io.Serializable;

/**
 * Created by Glen Luengen on 4/28/2018.
 */

public class Transfer implements Serializable {
    private int htime;
    private int mtime;
    private int temper;

    public Transfer(int htime, int mtime, int temper){
        this.htime = htime;
        this.mtime = mtime;
        this.temper = temper;
    }

    public int getHtime() {
        return htime;
    }

    public int getMtime() {
        return mtime;
    }

    public int getTemper() {
        return temper;
    }
}
