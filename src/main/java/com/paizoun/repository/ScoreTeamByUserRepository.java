package com.paizoun.repository;

import com.paizoun.domain.ScoreTeamByUser;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data Cassandra repository for the ScoreTeamByUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScoreTeamByUserRepository extends CassandraRepository<ScoreTeamByUser, UUID> {
}
