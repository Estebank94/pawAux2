package ar.edu.itba.paw.models;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by estebankramer on 02/09/2018.
 */

public class ListItem {

    String name;
    Integer id;

    @Autowired
    public ListItem(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
