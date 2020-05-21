package com.paizoun.repository;

import com.paizoun.domain.TeamUser;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data Cassandra repository for the TeamUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamUserRepository extends CassandraRepository<TeamUser, UUID> {
}
