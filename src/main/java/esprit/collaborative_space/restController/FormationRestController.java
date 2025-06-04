package esprit.collaborative_space.restController;

import esprit.collaborative_space.entity.Formation;
import esprit.collaborative_space.service.IFormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formation")
public class FormationRestController {
    @Autowired
    private IFormationService formationService;

    @PostMapping
    public ResponseEntity<Formation> createFormation(@RequestBody Formation formation) {
        Formation createdFormation = formationService.createFormation(formation);
        return new ResponseEntity<>(createdFormation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Formation>> getAllFormations() {
        List<Formation> formations = formationService.getAllFormations();
        return new ResponseEntity<>(formations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id) {
        Formation formation = formationService.getFormationById(id);
        return new ResponseEntity<>(formation, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}