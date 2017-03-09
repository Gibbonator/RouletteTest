package Model;

import java.util.Random;
public class RouletteTable {

    private final int MAXVAL = 36;

    public int spin(){
        Random random = new Random();
        return random.nextInt(MAXVAL);
    }




}
