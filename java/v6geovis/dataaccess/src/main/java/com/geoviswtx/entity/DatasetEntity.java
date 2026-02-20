package com.geoviswtx.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
@Table(name = "dataset")
@ToString
@NoArgsConstructor
public class DatasetEntity {

    @Id
    private UUID id;

    @Column(unique = true)
    private String type;

    @Column(name = "start_x", columnDefinition = "numeric")
    private Double startX;
    @Column(name = "start_y", columnDefinition = "numeric")
    private Double startY;
    @Column(name = "end_x", columnDefinition = "numeric")
    private Double endX;
    @Column(name = "end_y", columnDefinition = "numeric")
    private Double endY;
    @Column(name = "increment_x", columnDefinition = "numeric")
    private Double incrementX;
    @Column(name = "increment_y", columnDefinition = "numeric")
    private Double incrementY;

    @Column(name = "count_x", columnDefinition = "int")
    private Integer countX;
    @Column(name = "count_y", columnDefinition = "int")
    private Integer countY;
    @Column(name = "chuck_number", columnDefinition = "int")
    private Integer chuckNumber = 2500;
    @Column(name = "insert_time")
    private Date insertTime;

}
