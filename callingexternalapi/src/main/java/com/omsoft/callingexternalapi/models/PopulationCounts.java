package com.omsoft.callingexternalapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter@NoArgsConstructor
@AllArgsConstructor
@Table(name = "populationCounts")
public class PopulationCounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4)
    private String year;

    private String value;

    private String sex;

    private String reliabilty;
}
