package com.example.cimbexcel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Component;

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
@IdClass(MapNonSwitfKey.class)
@Component
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

}
