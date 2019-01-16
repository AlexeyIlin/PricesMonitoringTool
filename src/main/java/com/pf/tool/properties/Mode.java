package com.pf.tool.properties;


public enum Mode {

    Level2(2),
    Level3(1),
    Level2and3(3);


    private int mode;

    Mode(int mode) {
        this.mode = mode;
    }

    public int getMode(){
        return mode;
    }

}
