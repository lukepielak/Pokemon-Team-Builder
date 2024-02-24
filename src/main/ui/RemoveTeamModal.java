package ui;

import model.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// modal dialog box for remove teams
public class RemoveTeamModal extends JDialog {

    JButton btnOK = new JButton("OK");
    JButton btnCancel = new JButton("Cancel");

    PokemonTeamBuilderUI owner;

    // MODIFIES: this.
    // EFFECTS: creates a modal dialog box that prompts the user to choose a team to remove.
    public RemoveTeamModal(PokemonTeamBuilderUI owner, String title) {
        super(owner, title);

        this.owner = owner;

        JComboBox cboTeams = makeTeamSelector(owner);

        add(cboTeams);
        add(btnOK);
        add(btnCancel);

        // MODIFIES: this.
        // EFFECTS: handles actions after using the OK button.
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmChoiceAction(owner, cboTeams);
            }
        });

        // MODIFIES: this.
        // EFFECTS: handles actions after using the cancel button.
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveTeamModal.this.setVisible(false);
            }
        });
    }

    // MODIFIES: this.
    // EFFECTS: action for confirm choice.
    private void confirmChoiceAction(PokemonTeamBuilderUI owner, JComboBox cboTeams) {
        RemoveTeamModal.this.setVisible(false);
        // short form initialization, see method below.
        s1(cboTeams);
    }

    // MODIFIES: this
    // EFFECTS: short form initialization.
    private void s1(JComboBox cboTeams) {
        ConfirmChoiceModal ccm;
        ccm = new ConfirmChoiceModal(owner, "", (String) cboTeams.getSelectedItem(), RemoveTeamModal.this);
        ccm.setModal(true);
        ccm.setVisible(true);
    }


    // MODIFIES: this.
    // EFFECTS: creates a team selector.
    private JComboBox makeTeamSelector(PokemonTeamBuilderUI owner) {
        JComboBox cboTeams = new JComboBox();
        setBounds(550, 400, 340, 65);
        this.setLocationRelativeTo(null);
        Container dialogContent = getContentPane();
        dialogContent.setLayout(new FlowLayout());
        this.setResizable(false);

        for (Team t : owner.getUserList().getTeams()) {
            cboTeams.addItem(t.getTeamName());
        }
        return cboTeams;
    }
}