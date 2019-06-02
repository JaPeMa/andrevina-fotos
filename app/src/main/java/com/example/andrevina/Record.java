package com.example.andrevina;

import android.net.Uri;

import java.io.File;

public class Record implements Comparable<Record>{
    public int intentos;
    public String nombre;
    public Uri image;

    public Record(String nombre, int intentos, Uri image){
        this.nombre = nombre;
        this.intentos = intentos;
        this.image = image;
    }

    public int compareTo(Record o){
        if(intentos < o.intentos){
            return -1;
        }
        if(intentos > o.intentos){
            return 1;
        }
        return 0;
    }

}
