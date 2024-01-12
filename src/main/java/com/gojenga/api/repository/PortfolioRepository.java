package com.gojenga.api.repository;

import com.gojenga.api.models.Portfolio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.sound.sampled.Port;
import java.util.List;

@Repository("portfolioRepository")
public interface PortfolioRepository extends CrudRepository<Portfolio, Integer> {
    @Query("SELECT p FROM Portfolio p WHERE p.username = :username")
    List<Portfolio> findByUsername(String username);

    Portfolio findByUsernameAndAsset(String username, String asset);
}
