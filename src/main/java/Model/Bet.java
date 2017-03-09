package Model;

import Exeption.RouletteGameException;

import java.math.BigDecimal;

public class Bet {

    private Double value;
    private BetTypes type;
    private Integer[] squares;
    private RouletteTable rouletteTable;
    public enum BetTypes {
        STRAIGHT("Straight", 1), SPLIT("Split", 2), STREET("STREET",3), CORNER("CORNER",4), DOUBLE_STREET("DOUBLE_STREET",6),TRIO("TRIO",3),
        BASKET("BASKET",4),TOP_LINE("TOP_LINE",5), EVEN("EVEN", 16), ODD("ODD", 16);

        private final String text;
        private final int squares;

        BetTypes(final String text, final int squares){
            this.text = text;
            this.squares = squares;
        }

        public String toString(){
            return this.text;
        }
    }
    /* If the bet type has pre determined squares, e.g a bet on odds, or red/black(hypothetical) the squares are set here*/

    public Bet(BetTypes type, RouletteTable rouletteTable){
        this.type = type;
        this.rouletteTable = rouletteTable;
        switch (this.type){
            case EVEN: Integer[] esquares = new Integer[16];
            int j = 0;
            for(int i = 1; i <= 32; i ++){
                if(i % 2 == 0){
                    esquares[j] = i;
                    j++;
                }
            }
            this.squares = esquares;
                break;
            case ODD: Integer[] osquares = new Integer[16];
                j = 0;
                for(int i = 1; i <= 32; i ++){
                    if(i % 2 != 0){
                        osquares[j] = i;
                        j++;
                    }
                }
                this.squares = osquares;

                default: break;
        }
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) throws IllegalArgumentException {
        if(value <= 0){
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public BetTypes getType() {
        return type;
    }
    private boolean checkIfValidSquares(Integer[] squares){
        for(int i = 0; i < squares.length; i++){
            if(squares[i] < 0 || squares[i] > 36){
                return false;
            }
        }
        return true;
    }
    private boolean validSquareAmount(Integer[] squares) {
        if (this.getType().squares == squares.length) {
            return true;
        }
        return false;
    }
    /* If the bet type has pre determined squares, attempting to set the squares manually throws an illegal argument exception*/
    public void setSquares(Integer[] bets) throws IllegalArgumentException, RouletteGameException {
        if(this.getType().text.equals("EVEN") || this.getType().text.equals("ODD")){
            throw new IllegalArgumentException("No need to set squares for outer bets");
        }
        if( checkIfValidSquares(bets) && validSquareAmount(bets)){
            this.squares = bets;
        }
        else{
            throw new RouletteGameException("Invalid Squares");
        }
    }
    private Double getPayout(){
        int bets = squares.length;
        return ((36/bets)) * this.value;
    }
    private boolean checkResult(int result){
        for(Integer square: squares){
            if(square == result)
                return true;
        }
        return false;
    }

    public String makeBet() throws Exception {
        if(squares.length > 0 && value != null ){
            int result = rouletteTable.spin();
            if(checkResult(result)) {
                Double returns = getPayout();
                BigDecimal bdValue = new BigDecimal(value).setScale(2);
                BigDecimal bdReturns = new BigDecimal(returns).setScale(2);
                String output = "You bet £" + bdValue + " on: \n";
                for (int i = 0; i < squares.length; i++) {
                    output = output + " " + squares[i].toString() + ",";
                }
                    output = output.substring(0, output.length() -1);
                    output = output + " \n";
                    output = output + "Result was ... " + result + "\n";
                    output = output + "Returns: £" + bdReturns;
                    return output;

            }
            else {
                String output = "You bet £" + value + " on: \n";
                for (int i = 0; i < squares.length; i++) {
                    output = output + " " + squares[i].toString() + ",";
                }
                    output = output.substring(0, output.length() - 1);
                    output = output + " \n";
                    output = output + "Result was ... " + result + "\n";
                    output = output + "Returns: £0.00";
                    return output;
                }
            }

        else {
            throw  new Exception();
        }

    }

}
