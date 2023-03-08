package yehuda.heroes;

public class HeroesNotFoundException extends RuntimeException{
    HeroesNotFoundException() {super("Could not find any heroes in the database.");}

}
