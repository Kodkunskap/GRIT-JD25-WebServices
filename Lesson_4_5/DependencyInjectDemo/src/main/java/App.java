public class App {

    public static void main(String[] args) {
        App.run();
    }

    public static void run() {
        DiceRoll1 diceRoll1 = new DiceRoll1();
        //System.out.println(diceRoll1.asText());

        DiceRoll2 diceRoll2 = new DiceRoll2(
                new RandomlyGeneratedNumbers()
        );
        //System.out.println(diceRoll2.asText());

        diceRoll2 = new DiceRoll2(
                new StubRandomNumbers(5)
        );
        System.out.println(diceRoll2.asText());

    }


}
