package com.example.cimbexcel.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class MapNonSwitfKey implements Serializable {
    private String countryCode;
    private String currency;
}
