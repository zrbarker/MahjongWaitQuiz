import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Zane Barker
 */
public class Hand {

    private final ArrayList<String> hand;
    private final List<String> TILESET;

    public Hand(List<String> tileset){
        hand = new ArrayList<>();
        TILESET = tileset;
    }

    public void add(String tile){
        hand.add(tile);
    }

    public void remove(String tile){ hand.remove(tile); }

    public int size(){
        return hand.size();
    }


    /**
       Uses Collections.sort() to sort the hand in ascending order
     */
    public void sort(){
        Collections.sort(hand);
    }

    public String toString(){
        return hand.toString();
    }

    /**
        Checks if the given hand can be interpreted as winning hand consisting of a pair and
        4 groups(three-of-kind or sequence of three consecutive numbers) or a winning hand
        consisting of seven distinct pairs

        @return True if the hand is a valid winning hand, false if not or does not have the correct number of tiles to win.
     */
    public boolean isWinning(){
        // Because of melds, size won't necessarily be 14 in a real mahjong game,
        // but it will be for the purpose of this program
        if(hand.size() != 14)
            return false;

        int[] manzuCount = new int[9];

        for(String tile : hand){
            if(tile.charAt(1) == 'm') {
                int tileNum = Character.getNumericValue(tile.charAt(0));
                manzuCount[tileNum - 1]++;
            }
        }

        //check for standard hand of 4 groups and one pair
        if(isWinning(manzuCount, 0, 0, 0))
            return true;

        //check for seven pairs hand
        int pairs = 0;
        for (int j : manzuCount) {
            if (j == 2)
                pairs++;
        }

        return pairs == 7;
    }

    /**
     * Recursive algorithm to check if a hand of 14 tiles is a valid winning hand of four groups and a pair
     *
     * @param manzuCount - an array containing a tally of each manzu tile in a hand
     * @param i          - the index of the current manzu tile being checked for belonging in a group or pair
     * @param groups     - the current number of valid groups in the current interpretation of the hand
     * @param pairs      - the current number of pairs in the current interpretation of the hand. Should never be more than 1
     * @return           - true if an interpretation of the hand results in the needed four groups and one pair, false if
     *                     such and interpretation proves impossible
     */
    private boolean isWinning(int[] manzuCount, int i, int groups, int pairs){
        if(groups==4 && pairs==1)
            return true;

        while(manzuCount[i]==0) {
            i++;
            if (i >= manzuCount.length)
                return false;
        }

        int[] tempManzuCount;

        if(pairs==0 && manzuCount[i]>=2){
            //System.out.println("i: "+i+" Trying pair");
            tempManzuCount = Arrays.copyOf(manzuCount, manzuCount.length);
            tempManzuCount[i] = tempManzuCount[i]-2;
            if(isWinning(tempManzuCount, i, groups, pairs+1))
                return true;
        }
        if(manzuCount[i]>=3){
            //System.out.println("i: "+i+" Trying pon");
            tempManzuCount = Arrays.copyOf(manzuCount, manzuCount.length);
            tempManzuCount[i] = tempManzuCount[i]-3;
            if(isWinning(tempManzuCount, i, groups+1, pairs))
                return true;
        }
        if(i+2<manzuCount.length && manzuCount[i]>=1 && manzuCount[i+1]>=1 && manzuCount[i+2]>=1){
            //System.out.println("i: "+i+" Trying chii");
            tempManzuCount = Arrays.copyOf(manzuCount, manzuCount.length);
            tempManzuCount[i]   = tempManzuCount[i]-1;
            tempManzuCount[i+1] = tempManzuCount[i+1]-1;
            tempManzuCount[i+2] = tempManzuCount[i+2]-1;
            return isWinning(tempManzuCount, i, groups + 1, pairs);
        }
        //System.out.println("i:"+i+" returning False");
        return false;
    }

    /**
     *  Checks the hand against every tile in the tileset to determine which tiles will change the current hand
     *  into a valid winning hand.
     *
     * @return Null if the hand does not have enough tiles to be one away from winning
     *         otherwise, returns an ArrayList containing the tiles needed (if any) to turn a hand into a winning hand
     */
    public ArrayList<String> getTenpaiWaits(){
        // Because of melds, size won't necessarily be 13 in a real mahjong game,
        // but it will be for the purpose of this program
        if(hand.size() != 13)
            return null;

        ArrayList<String> waits = new ArrayList<>();

        for(String t : TILESET){
            hand.add(t);
            if(isWinning())
                waits.add(t);
            hand.remove(t);
        }

        return waits;
    }
}
