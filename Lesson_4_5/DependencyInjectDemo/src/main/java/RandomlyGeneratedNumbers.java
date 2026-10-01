import java.util.random.RandomGenerator;

public class RandomlyGeneratedNumbers implements RandomNumbers {

    private final RandomGenerator rnd = RandomGenerator.getDefault();

    @Override
    public int nextInt(int upperBoundExclusive) {
        return rnd.nextInt(upperBoundExclusive);
    }
}
