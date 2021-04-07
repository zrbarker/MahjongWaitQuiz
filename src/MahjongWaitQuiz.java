import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Zane Barker
 */
public class MahjongWaitQuiz extends JFrame {


    private final List<String> TILESET = Arrays.asList("1m", "2m", "3m", "4m", "5m", "6m", "7m", "8m", "9m");
    private final int NUM_TILES = TILESET.size();
    private final ArrayList<String> answerList = new ArrayList<>();
    private final JCheckBox[] answers = new JCheckBox[NUM_TILES];

    private Hand hand;

    public MahjongWaitQuiz(){

        //Create JFrame and main panel
        setTitle("Mahjong Wait Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel outerP = new JPanel();
        outerP.setLayout(new BoxLayout(outerP,BoxLayout.PAGE_AXIS));

        //Generate new hand to be as well as question text label
        hand = getNewHand();
        JLabel questionL = new JLabel("What tiles (if any) does the following hand need to win?\n");
        JPanel problemP = new JPanel();
        JLabel[] handTiles = new JLabel[hand.size()];
        for(int i=0; i< handTiles.length; i++){
            handTiles[i] = new JLabel(new ImageIcon(MahjongWaitQuiz.class.getResource(hand.toString().substring(i*4+1,i*4+3)+".png")));
            problemP.add(handTiles[i]);
        }
        JLabel handL = new JLabel(hand.toString());

        //create sub-panel with answer checkboxes along with the item listener to update the answer list
        JPanel answerP = new JPanel();
        ItemListener cbListener = this::updateAnswers;
        for(int i=0; i<NUM_TILES; i++){
            answers[i] = new JCheckBox(TILESET.get(i));
            answers[i].setIcon(new ImageIcon(MahjongWaitQuiz.class.getResource(TILESET.get(i)+".png")));
            answers[i].setBackground(Color.GREEN);
            answers[i].addItemListener(cbListener);
            answerP.add(answers[i]);
        }

        //Create buttons and their associated action listeners
        JButton checkAnswerB = new JButton("Check Answer");
        JButton exitB = new JButton("Exit");
        checkAnswerB.addActionListener(e -> onCheckAnswer(handTiles,handL));
        exitB.addActionListener(e -> dispose());

        //Format components
        outerP.setBackground(Color.GREEN);
        problemP.setBackground(Color.GREEN);
        answerP.setBackground(Color.GREEN);
        questionL.setAlignmentX(Component.CENTER_ALIGNMENT);
        handL.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkAnswerB.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitB.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Add components to outer panel
        outerP.add(questionL);
        outerP.add(problemP);
        outerP.add(handL);
        outerP.add(answerP);
        outerP.add(checkAnswerB);
        outerP.add(exitB);

        //add components to frame
        add(outerP);

    }

    private Hand getNewHand(){
        Wall wall = new Wall(TILESET);
        Hand hand = new Hand(TILESET);
        for(int i=0;i<13;i++)
            hand.add(wall.drawTile());
        hand.sort();
        return hand;
    }

    /**
     * Called when a checkbox is selected or deselected
     *
     * @param e itemEvent
     */
    private void updateAnswers(ItemEvent e){
        for(JCheckBox cb: answers){
            if(e.getSource()==cb){
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    answerList.add(cb.getText());
                    cb.setBackground(Color.YELLOW);
                }
                else {
                    answerList.remove(cb.getText());
                    cb.setBackground(Color.GREEN);
                }

            }
        }
    }

    /**
     * Called when the check Answer button is used
     *
     * Creates a dialog box that displays whether the user's answer was correct or not.
     * it then creates a new problem for the user to solve and resets the checkboxes
     */
    private void onCheckAnswer(JLabel[] handTiles,  JLabel handL){
        Collections.sort(answerList);
        AnswerDialog ad = new AnswerDialog(answerList.toArray(new String[0]),
                hand.getTenpaiWaits().toArray(new String[0]));
        ad.pack();
        ad.setVisible(true);
        hand = getNewHand();
        for(int i=0; i< handTiles.length; i++){
            handTiles[i].setIcon(new ImageIcon(MahjongWaitQuiz.class.getResource(hand.toString().substring(i*4+1,i*4+3)+".png")));
        }
        handL.setText(hand.toString());
        for(JCheckBox cb: answers){
            cb.setBackground(Color.GREEN);
            cb.setSelected(false);
            answerList.remove(cb.getText());
        }

    }

    public static void main(String[] args){
        MahjongWaitQuiz mwq = new MahjongWaitQuiz();
        mwq.pack();
        mwq.setVisible(true);
    }

}
