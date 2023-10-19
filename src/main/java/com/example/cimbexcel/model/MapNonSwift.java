package com.example.cimbexcel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
@Entity
@Table(name = "MAP_NON_SWIFT", schema = "REFERENCE_DATA_SERVICE")
public class MapNonSwift {
    @Id
    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @Id
    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "CORRIDOR_CODE")
    private String corridorCode;

    @Column(name = "IS_DELETE")
    private Boolean isDelete;
}

