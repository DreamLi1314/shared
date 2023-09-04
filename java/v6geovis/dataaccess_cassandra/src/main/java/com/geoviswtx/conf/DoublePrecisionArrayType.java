package com.geoviswtx.conf;

import com.vladmihalcea.hibernate.type.array.internal.ArraySqlTypeDescriptor;
import com.vladmihalcea.hibernate.type.array.internal.DoubleArrayTypeDescriptor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;

public class DoublePrecisionArrayType extends AbstractSingleColumnStandardBasicType<double[]> {

    public DoublePrecisionArrayType() {
        super(
                ArraySqlTypeDescriptor.INSTANCE,
                new DoubleArrayTypeDescriptor()
        );
    }

    @Override
    public String getName() {
        return "double-precision-array";
    }
}

