package yehuda.heroes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// mess around with cross origins
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class HeroController {

    @Autowired
    HeroRepository heroRepository;

    @GetMapping("/heroes")
    public ResponseEntity<List<Hero>> getAllHeroes(@RequestParam(required = false) String title) {
        try {
            List<Hero> heroes = new ArrayList<Hero>();

            // get all heroes if no title string is entered as query param
            if (title == null)
                heroRepository.findAll().forEach(heroes::add);
            else
                // search by title containing the query param
                heroRepository.findByTitleContaining(title).forEach(heroes::add);

            if (heroes.isEmpty()) {
                // NO_CONTENT Vs. NOT_FOUND
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(heroes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/heroes/{id}")
//    public ResponseEntity<Hero> getHeroById(@PathVariable("id") long id) {
//        Optional<Hero> heroData = heroRepository.findById(id);
//
//        if (heroData.isPresent()) {
//            return new ResponseEntity<>(heroData.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//}
    @GetMapping("/heroes/{id}")
    public ResponseEntity<Hero> getHeroById(@PathVariable("id") long id) {
        Hero hero = heroRepository.findById(id).orElseThrow(() -> new HeroNotFoundException(id));
        return new ResponseEntity<>(hero, HttpStatus.OK);
    }

    @PostMapping("/heroes")
    public ResponseEntity<Hero> createHero(@RequestBody Hero hero) {
        try {
            Hero _hero = heroRepository
                    .save(new Hero(hero.getTitle(), hero.getDescription(), false));
            return new ResponseEntity<>(_hero, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/heroes/{id}")
    public ResponseEntity<Hero> updateHero(@PathVariable("id") long id, @RequestBody Hero hero) {
        Optional<Hero> heroData = heroRepository.findById(id);

        if (heroData.isPresent()) {
            Hero _hero = heroData.get();
            _hero.setTitle(hero.getTitle());
            _hero.setDescription(hero.getDescription());
            _hero.setLiveness(hero.isAlive());
            return new ResponseEntity<>(heroRepository.save(_hero), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/heroes/{id}")
    public ResponseEntity<HttpStatus> deleteHero(@PathVariable("id") long id) {
        try {
            heroRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/heroes")
    public ResponseEntity<HttpStatus> deleteAllHeroes() {
        try {
            heroRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/heroes/alive")
    public ResponseEntity<List<Hero>> findByLiveness() {
        try {
            List<Hero> heroes = heroRepository.findByLiveness(true);

            if (heroes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(heroes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}