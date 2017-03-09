import Exeption.RouletteGameException;
import Model.Bet;
import Model.RouletteTable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class BetTests {

    @Test
    public void winningSingleReturnsCorrectAmount() throws Exception{
        RouletteTable rouletteTable = mock(RouletteTable.class);
        when(rouletteTable.spin()).thenReturn(30);
        Integer[] square = new Integer[1];
        square[0] = 30;
        Bet bet = new Bet(Bet.BetTypes.STRAIGHT, rouletteTable);
            bet.setSquares(square);
            bet.setValue(10);
            String output = bet.makeBet();
            System.out.print(output);
            Assert.assertTrue("Incorrect winnings..",output.contains("£360.00"));
    }
    @Test
    public void losingSingleReturnsCorrectAmount() throws Exception{
        RouletteTable rouletteTable = mock(RouletteTable.class);
        when(rouletteTable.spin()).thenReturn(22);
        Integer[] square = new Integer[1];
        square[0] = 30;
        Bet bet = new Bet(Bet.BetTypes.STRAIGHT, rouletteTable);
        bet.setSquares(square);
        bet.setValue(10);
        String output = bet.makeBet();
        System.out.print(output);
        Assert.assertTrue("Incorrect winnings ...",output.contains("£0.00"));
    }
    @Test(expected=IllegalArgumentException.class)
    public void invalidBetAmountThrowsException() throws Exception{
        RouletteTable rouletteTable = mock(RouletteTable.class);
        Integer[] square = new Integer[1];
        square[0] = 30;
        Bet bet = new Bet(Bet.BetTypes.STRAIGHT, rouletteTable);
        bet.setSquares(square);
        bet.setValue(0);
    }
    @Test(expected= RouletteGameException.class)
    public void invalidSquaresThrowsException() throws Exception {
        RouletteTable rouletteTable = mock(RouletteTable.class);
        Integer[] square = new Integer[1];
        square[0] = 40;
        Bet bet = new Bet(Bet.BetTypes.STRAIGHT, rouletteTable);
        bet.setSquares(square);
    }
    @Test
    public void oddBetFillsSquaresCorrectly(){
        RouletteTable rouletteTable = new RouletteTable();
        Bet bet = new Bet(Bet.BetTypes.EVEN,rouletteTable);

    }
    @Test
    public void zeroResultOddBetReturnsCorrectWinnings() throws Exception{
        RouletteTable rouletteTable = mock(RouletteTable.class);
        when(rouletteTable.spin()).thenReturn(0);
        Bet bet = new Bet(Bet.BetTypes.EVEN, rouletteTable);
        bet.setValue(10.00);
        String output = bet.makeBet();
        Assert.assertTrue("Wrong returns for 0 on even bet",output.contains("£0.00"));
    }
    @Test
    public void zeroResultEvenBetReturnsCorrectWinnings() throws Exception{
        RouletteTable rouletteTable = mock(RouletteTable.class);
        when(rouletteTable.spin()).thenReturn(0);
        Bet bet = new Bet(Bet.BetTypes.ODD, rouletteTable);
        bet.setValue(10.00);
        String output = bet.makeBet();
        Assert.assertTrue("Wrong returns for 0 on even bet",output.contains("£0.00"));
    }
    @Test
    public void winningEvenBetReturnsCorrectWinnings() throws Exception{
        RouletteTable rouletteTable = mock(RouletteTable.class);
        when(rouletteTable.spin()).thenReturn(2);
        Bet bet = new Bet(Bet.BetTypes.EVEN, rouletteTable);
        bet.setValue(10.00);
        String output = bet.makeBet();
        System.out.print(output);
        Assert.assertTrue("Wrong returns for 2 on even bet",output.contains("£20.00"));
    }
    @Test
    public void oddResultOnEvenBetReturnsCorrectWinnings() throws Exception {
        RouletteTable rouletteTable = mock(RouletteTable.class);
        when(rouletteTable.spin()).thenReturn(3);
        Bet bet = new Bet(Bet.BetTypes.EVEN, rouletteTable);
        bet.setValue(10.00);
        String output = bet.makeBet();
        System.out.print(output);
        Assert.assertTrue("Wrong returns for 3 on even bet", output.contains("£0.00"));
    }
    @Test
    public void evenResultOnOddBetReturnsCorrectWinnings() throws Exception{
        RouletteTable rouletteTable = mock(RouletteTable.class);
        when(rouletteTable.spin()).thenReturn(2);
        Bet bet = new Bet(Bet.BetTypes.ODD, rouletteTable);
        bet.setValue(10.00);
        String output = bet.makeBet();
        System.out.print(output);
        Assert.assertTrue("Wrong returns for 3 on even bet", output.contains("£0.00"));
    }
    @Test(expected = IllegalArgumentException.class)
    public void settingSquaresOnEvenBetThrowsException(){
        RouletteTable rouletteTable = new RouletteTable();
        Bet bet = new Bet(Bet.BetTypes.EVEN, rouletteTable);
        Integer[] squares = new Integer[1];
        squares[0] = 6;
        bet.setSquares(squares);
    }
    @Test(expected = IllegalArgumentException.class)
    public void settingSquaresOnOddBetThrowsException(){
        RouletteTable rouletteTable = new RouletteTable();
        Bet bet = new Bet(Bet.BetTypes.ODD, rouletteTable);
        Integer[] squares = new Integer[1];
        squares[0] = 6;
        bet.setSquares(squares);
    }
}
