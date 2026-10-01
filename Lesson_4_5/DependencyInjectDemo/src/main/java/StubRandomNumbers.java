public class StubRandomNumbers implements RandomNumbers {

    private int randomNumber;

    public StubRandomNumbers(int randomNumber) {
        this.randomNumber = randomNumber;
    }

    @Override
    public int nextInt(int upperBoundExclusive) {
        return randomNumber;
    }
}
