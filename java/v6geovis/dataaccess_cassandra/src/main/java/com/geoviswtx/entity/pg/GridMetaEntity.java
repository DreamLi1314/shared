package com.geoviswtx.entity.pg;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "t_c_grid_meta")
@ToString
@NoArgsConstructor
public class GridMetaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grid_meta_sequence")
    @SequenceGenerator(name = "grid_meta_sequence", sequenceName = "grid_meta_sequence", allocationSize = 1)
    private Long id;

    private Long dsId;

    private String elem;

    private Double z;

    private Date baseTime;

    private Date forecastTime;

}
