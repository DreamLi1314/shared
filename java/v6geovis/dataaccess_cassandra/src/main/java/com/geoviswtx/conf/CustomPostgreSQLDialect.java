package com.geoviswtx.conf;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.PostgreSQL94Dialect;

import java.sql.Types;

public class CustomPostgreSQLDialect extends PostgreSQL94Dialect {

    public CustomPostgreSQLDialect() {
        super();
        registerColumnType(Types.ARRAY, "double precision[]");
    }

    @Override
    public void contributeTypes(TypeContributions typeContributions, org.hibernate.service.ServiceRegistry serviceRegistry) {
        super.contributeTypes(typeContributions, serviceRegistry);
        typeContributions.contributeType(new DoublePrecisionArrayType());
    }


}

