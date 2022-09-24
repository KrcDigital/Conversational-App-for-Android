package com.krc.mola;

public class favjava {

    private String id,o,ben;

    public favjava(){

    }

    public favjava(String id, String o, String ben) {
        this.id = id;
        this.o = o;
        this.ben = ben;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String geto() {
        return o;
    }

    public void seto(String o) {
        this.o = o;
    }

    public String getben() {
        return ben;
    }

    public void setben(String ben) {
        this.ben = ben;
    }

}
