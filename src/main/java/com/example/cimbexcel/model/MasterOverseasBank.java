package com.example.cimbexcel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "MASTER_OVERSEAS_BANK", schema = "REFERENCE_DATA_SERVICE")
@Component
public class MasterOverseasBank {
    @Id
    @Column(name = "SPEEDSEND_CODE")
    private String speedsendCode;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "SPEEDSEND_FLAG")
    private Boolean speedsendFlag;

    @Column(name = "IS_DELETE")
    private Boolean isDelete;

    @Column(name = "BANK_NAME")
    private String bankName;

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
