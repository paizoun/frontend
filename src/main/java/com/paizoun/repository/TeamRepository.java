package com.paizoun.repository;

import com.paizoun.domain.Team;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data Cassandra repository for the Team entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamRepository extends CassandraRepository<Team, UUID> {
}
