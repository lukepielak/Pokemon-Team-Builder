package ui;

import model.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// modal dialog box for view teams
public class ViewTeamsModal extends JDialog {

    JButton btnOK = new JButton("OK");
    JButton btnCancel = new JButton("Cancel");

    PokemonTeamBuilderUI owner;

    // MODIFIES: this.
    // EFFECTS: creates a modal dialog box that prompts the user to select a created team to view it in more detail.
    public ViewTeamsModal(PokemonTeamBuilderUI owner, String title) {
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
                displayPokemonTeamAction(owner, cboTeams);
            }
        });

        // MODIFIES: this.
        // EFFECTS: handles actions after using the cancel button.
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewTeamsModal.this.setVisible(false);
            }
        });
    }

    // MODIFIES: this.
    // EFFECTS: action for display pokemon team.
    private void displayPokemonTeamAction(PokemonTeamBuilderUI owner, JComboBox cboTeams) {
        ViewTeamsModal.this.setVisible(false);
        // short form initialization, see method below.
        s2(cboTeams);
    }

    // MODIFIES: this.
    // EFFECTS: short form for initializing display pokemon.
    private void s2(JComboBox cboTeams) {
        DisplayPokemonTeamModal dptm;
        dptm = new DisplayPokemonTeamModal(owner, "", (String) cboTeams.getSelectedItem(), ViewTeamsModal.this);
        dptm.setModal(true);
        dptm.setVisible(true);
    }

    // MODIFIES: this.
    // EFFECTS: creates team selector.
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