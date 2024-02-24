package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// modal dialog box for confirm choice
public class ConfirmChoiceModal extends JDialog {

    JButton btnDelete = new JButton("Delete");
    JButton btnCancel = new JButton("Cancel");

    PokemonTeamBuilderUI owner;
    JDialog prevModal;
    String name;

    // MODIFIES: this.
    // EFFECTS: creates modal dialog box that prompts the user to confirm their decision to delete a pokemon team.
    public ConfirmChoiceModal(PokemonTeamBuilderUI owner, String title, String name, JDialog prevModal) {
        super(owner, title);

        this.owner = owner;
        this.prevModal = prevModal;
        this.name = name;

        makeJFrame(name);

        // MODIFIES: this.
        // EFFECTS: handles actions for delete button.
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmChoiceModal.this.setVisible(false);
                try {
                    confirmChoicePopup(owner, name,  " was successfully deleted.");
                } catch (Exception f) {
                    confirmChoicePopup(owner, name,  " was successfully deleted.");
                }
            }
        });

        // MODIFIES: this.
        // EFFECTS: handles actions for cancel button.
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmChoiceModal.this.setVisible(false);
            }
        });
    }

    // EFFECTS: displays popup after confirming to delete a team.
    private static void confirmChoicePopup(PokemonTeamBuilderUI owner, String name, String message) {
        owner.getUserList().removeTeam(name);
        ImageIcon meowth = new ImageIcon("data/meowth.png");
        Image meowth2 = meowth.getImage();
        Image meowth3 = meowth2.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        meowth = new ImageIcon(meowth3);
        JOptionPane.showMessageDialog(owner, name + message, "", JOptionPane.INFORMATION_MESSAGE, meowth);
    }

    // MODIFIES: this.
    // EFFECTS: creates a JFrame.
    private void makeJFrame(String name) {
        setBounds(550, 400, 385, 115);
        this.setLocationRelativeTo(null);
        Container dialogContent = getContentPane();
        dialogContent.setLayout(new FlowLayout());
        this.setResizable(false);

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        panel1.setPreferredSize(new Dimension(360, 30));
        panel2.setPreferredSize(new Dimension(360, 30));

        this.add(panel1);
        this.add(panel2);

        JLabel text = new JLabel(name + " will be deleted, are you sure?");
        panel1.add(text);

        panel2.add(btnDelete);
        panel2.add(btnCancel);
    }
}