package com.paizoun.repository;

import com.paizoun.domain.TeamDetailByUser;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data Cassandra repository for the TeamDetailByUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamDetailByUserRepository extends CassandraRepository<TeamDetailByUser, UUID> {
}
