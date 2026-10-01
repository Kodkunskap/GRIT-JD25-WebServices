import java.util.random.RandomGenerator;

/*
    DiceRoll that is using Dependency Injection
 */
public class DiceRoll2 {
    private final int NUMBER_OF_SIDES = 6;
    private final RandomNumbers rnd;

    public DiceRoll2(RandomNumbers rnd) {
        this.rnd = rnd;
    }

    public String asText() {
        int rolled = rnd.nextInt(NUMBER_OF_SIDES) + 1;
        return String.format("You rolled a %d", rolled);
    }

}
