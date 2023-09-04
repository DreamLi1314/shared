package com.geoviswtx.dao.cassandra;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @param <E>  domain class
 * @param <ID> PrimaryKey class
 */
@NoRepositoryBean
public interface BaseCassandraRepository<E, ID extends Serializable>
   extends CassandraRepository<E,ID>
{
}
