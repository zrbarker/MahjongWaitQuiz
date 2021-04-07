import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * @author Zane Barker
 */
public class AnswerDialog extends JDialog {

    public AnswerDialog(String[] userAnswer,String[] correctAnswer) {
        //set up content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.PAGE_AXIS));
        setContentPane(contentPane);
        setModalityType(ModalityType.APPLICATION_MODAL);


        //Create and add labels based on answer result
        JLabel answerResult = new JLabel();
        answerResult.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(answerResult);
        if(Arrays.deepEquals(userAnswer,correctAnswer)){
            answerResult.setText("Correct!");
        } else{
            answerResult.setText("Incorrect!");
            JLabel userAnswerL = new JLabel("Your answer: " + Arrays.toString(userAnswer));
            JLabel correctAnswerL = new JLabel("Correct answer: " + Arrays.toString(correctAnswer));
            userAnswerL.setAlignmentX(Component.CENTER_ALIGNMENT);
            correctAnswerL.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPane.add(userAnswerL);
            contentPane.add(correctAnswerL);
        }

        //Okay button closes dialog box
        JButton buttonOK = new JButton("OK");
        buttonOK.addActionListener(e -> onOK());
        buttonOK.setAlignmentX(Component.CENTER_ALIGNMENT);
        getRootPane().setDefaultButton(buttonOK);
        contentPane.add(buttonOK);

        // call onOK() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        // call onOK() on ESCAPE
        contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void onOK() {
        dispose();
    }

}
