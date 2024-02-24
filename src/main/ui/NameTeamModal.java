package ui;

import model.Team;
import model.exception.EmptyNameException;
import model.exception.NameAlreadyExistsException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// MODIFIES: this.
// EFFECTS: creates a modal dialog box that allows the user to input the name of a new team.
public class NameTeamModal extends JDialog {

    private String name = "";
    JTextField txtName = new JTextField(20);
    JButton btnOK = new JButton("OK");
    JButton btnCancel = new JButton("Cancel");

    PokemonTeamBuilderUI owner;

    // constructor
    public NameTeamModal(PokemonTeamBuilderUI owner, String title) {
        super(owner, title);
        this.owner = owner;

        makeFrame();

        // MODIFIES: this.
        // EFFECTS: handles actions for OK button.
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    choosePokemonAction(owner);

                } catch (EmptyNameException f) {
                    namePopup("data/psyduck.png", owner, "Name must not begin with whitespace!");
                } catch (NameAlreadyExistsException g) {
                    namePopup("data/ditto.png", owner, "A team with that name already exists!");
                }
                NameTeamModal.this.setVisible(false);
            }
        });

        // MODIFIES: this.
        // EFFECTS: handles actions for cancel button.
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setName("");
                NameTeamModal.this.setVisible(false);

            }
        });
    }

    // MODIFIES: this.
    // EFFECTS: displays popups for exceptions that may occur while using the name text field.
    private static void namePopup(String filename, PokemonTeamBuilderUI owner, String message) {
        ImageIcon psyduck = new ImageIcon(filename);
        Image psyduck2 = psyduck.getImage();
        Image psyduck3 = psyduck2.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        psyduck = new ImageIcon(psyduck3);
        JOptionPane.showMessageDialog(owner, message, "", JOptionPane.INFORMATION_MESSAGE, psyduck);
    }

    // MODIFIES: this.
    // EFFECTS: creates JFrame.
    private void makeFrame() {
        setBounds(550, 400, 340, 100);
        this.setLocationRelativeTo(null);
        Container dialogContent = getContentPane();
        dialogContent.setLayout(new FlowLayout());
        this.setResizable(false);

        add(new JLabel("Name"));
        add(txtName);
        add(btnOK);
        add(btnCancel);
    }

    // MODIFIES: this.
    // EFFECTS: action for choose pokemon.
    private void choosePokemonAction(PokemonTeamBuilderUI owner) throws EmptyNameException, NameAlreadyExistsException {
        setName(txtName.getText());
        if (!isNameValid()) {
            throw new EmptyNameException();
        } else if (nameExists(name)) {
            throw new NameAlreadyExistsException();
        }
        // add new empty team with chosen team name
        owner.getUserList().addTeam(new Team(getName()));

        NameTeamModal.this.setVisible(false);
        ChoosePokemonModal cpm = new ChoosePokemonModal(owner, "Add Pokemon to your team", getName());
        cpm.setModal(true);
        cpm.setVisible(true);
    }

    // EFFECTS: checks if a name is empty, or begins with whitespace.
    private boolean isNameValid() throws EmptyNameException {
        if (getName().equals("")) {
            return false;
        } else if (getName().charAt(0) == ' ') {
            return false;
        } else {
            return true;
        }
    }

    // EFFECTS: checks if a chosen name has already been used to make a team.
    public boolean nameExists(String name) {
        for (Team t : owner.getUserList().getTeams()) {
            if (name.equals(t.getTeamName())) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
