package com.geoviswtx.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "grid_data")
@ToString
@NoArgsConstructor
//@TypeDef(name = "double-precision-array", typeClass = DoublePrecisionArrayType.class)
public class GridDataEntity {

    @Id
    private UUID id;

    @Column(name = "meta_id")
    private UUID metaId;

    @Column(name = "chuck_idx")
    private Integer chuckIdx ;

    @Column(name = "datas", columnDefinition = "double precision[]")
    @Type(type = "double-precision-array")
    private double[] datas;

}
