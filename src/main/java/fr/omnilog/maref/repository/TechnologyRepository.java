package fr.omnilog.maref.repository;

import fr.omnilog.maref.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Integer> {
    @Query("SELECT t FROM Technology t WHERE t.name IN (:technologies)")
    List<Technology> findMatchingTechnologies(List<String> technologies);
}
