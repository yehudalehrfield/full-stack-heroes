package yehuda.heroes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeroRepository extends JpaRepository<Hero, Long> {
    List<Hero> findByLiveness(boolean published);

    List<Hero> findByTitleContaining(String title);
}
