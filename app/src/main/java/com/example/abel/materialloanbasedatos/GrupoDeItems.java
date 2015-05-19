package com.example.abel.materialloanbasedatos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abel on 18/05/2015.
 */
public class GrupoDeItems {
    public String string;
    public final List<String> children = new ArrayList<String>();

    public GrupoDeItems(String string) {
        this.string = string;
    }
}
