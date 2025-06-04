package esprit.collaborative_space.service;

import esprit.collaborative_space.entity.Formation;
import esprit.collaborative_space.repository.IFormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormationServiceImp implements IFormationService{
    @Autowired
    private IFormationRepository formationRepository;

    @Override
    public Formation createFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    @Override
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    @Override
    public Formation getFormationById(Long id) {
        return formationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation not found"));
    }


    @Override
    public void deleteFormation(Long id) {
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation not found"));

        formationRepository.delete(formation);
    }
}
