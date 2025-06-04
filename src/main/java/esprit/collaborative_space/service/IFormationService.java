package esprit.collaborative_space.service;

import esprit.collaborative_space.entity.Formation;

import java.util.List;

public interface IFormationService {
    Formation createFormation(Formation formation);
    List<Formation> getAllFormations();
    Formation getFormationById(Long id);

    void deleteFormation(Long id);
}
