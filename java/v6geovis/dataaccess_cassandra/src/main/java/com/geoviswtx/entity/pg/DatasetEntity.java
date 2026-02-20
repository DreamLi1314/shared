package com.geoviswtx.entity.pg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "t_c_dataset")
@ToString
@NoArgsConstructor
public class DatasetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dataset_sequence")
    @SequenceGenerator(name = "dataset_sequence", sequenceName = "dataset_sequence", allocationSize = 1)
    private Long id;

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
