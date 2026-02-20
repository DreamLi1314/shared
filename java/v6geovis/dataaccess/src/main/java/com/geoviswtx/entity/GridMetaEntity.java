package com.geoviswtx.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "grid_meta")
@ToString
@NoArgsConstructor
public class GridMetaEntity {

    @Id
    private UUID id;

    @Column(name = "dataset_id")
    private UUID datasetId;

    @Column(name = "feature", columnDefinition = "text")
    private String feature;

    @Column(name = "z", columnDefinition = "numeric")
    private Double z;

    @Column(name = "qbsj")
    private Date qbsj;

    @Column(name = "ybsk")
    private Date ybsk;

}
