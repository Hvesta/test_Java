package fr.omnilog.maref.service;

import fr.omnilog.maref.model.Technology;
import fr.omnilog.maref.repository.TechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {

    private final TechnologyRepository technologyRepository;

    public TechnologyService(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public List<Technology> getExistingTechnologies(List<String> technologies) {
        if ( technologies != null) {
            return technologyRepository.findExistingTechnologies(technologies);
        } else {
            throw new RuntimeException("Pas de technologies renseignées !");
        }
    }
}
