package org.farid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.farid.model.entity.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
}
