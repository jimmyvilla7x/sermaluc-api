package com.sermaluc.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class PhoneModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @JsonIgnore
    private UUID id;
    
    private String number;
    private String citycode;
    private String countrycode;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", updatable = true, insertable = true)
    private UserModel user;

}