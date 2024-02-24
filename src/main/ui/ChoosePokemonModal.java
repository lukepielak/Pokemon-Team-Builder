package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Pokemon;
import model.Team;
import model.exception.CancelledTeamCreationException;
import model.exception.PokemonNotFoundException;

import static java.util.Objects.isNull;

// modal dialog box for choosing pokemon to add to a team
public class ChoosePokemonModal extends JDialog {

    JComboBox cboNormal = new JComboBox();
    JButton btnAddNormal = new JButton("Add");

    JComboBox cboFire = new JComboBox();
    JButton btnAddFire = new JButton("Add");

    JComboBox cboWater = new JComboBox();
    JButton btnAddWater = new JButton("Add");

    JComboBox cboElectric = new JComboBox();
    JButton btnAddElectric = new JButton("Add");

    JComboBox cboGrass = new JComboBox();
    JButton btnAddGrass = new JButton("Add");

    JButton btnOK = new JButton("OK");
    JButton btnCancel = new JButton("Cancel");

    JPanel panel0 = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();
    JPanel panel5 = new JPanel();
    JPanel optionPanel = new JPanel();

    private String name;
    private PokemonTeamBuilderUI owner;

    // MODIFIES: this.
    // EFFECTS: creates a modal dialog box that allows the user to select and add pokemon to a team.
    public ChoosePokemonModal(PokemonTeamBuilderUI owner, String title, String name) {
        super(owner, title);
        this.name = name;
        this.owner = owner;

        makeJFrame();

        setPanelStyle();

        addPanels();

        sortPokemon(owner);

        JLabel nameLabel = new JLabel(name);

        addToPanels(nameLabel);

        addTypeActionListener(btnAddNormal, cboNormal, owner);
        addTypeActionListener(btnAddWater, cboWater, owner);
        addTypeActionListener(btnAddFire, cboFire, owner);
        addTypeActionListener(btnAddElectric, cboElectric, owner);
        addTypeActionListener(btnAddGrass, cboGrass, owner);

        // MODIFIES: this.
        // EFFECTS: handles actions for OK button.
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChoosePokemonModal.this.setVisible(false);
            }
        });

        cancelActionListener(owner, name);
    }

    // MODIFIES: this.
    // EFFECTS: action listener for the cancel button, deletes team if user decides to cancel during team creation.
    private void cancelActionListener(PokemonTeamBuilderUI owner, String name) {
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    owner.getUserList().removeTeam(name);
                    throw new CancelledTeamCreationException();
                } catch (CancelledTeamCreationException f) {
                    cancelledTeamCreationPopup("data/mankey.png", owner, "Creation of " + name + " was cancelled.");
                } catch (Exception g) {
                    System.out.println("Exception occurred.");
                    cancelledTeamCreationPopup("data/mankey.png", owner, "Creation of " + name + " was cancelled.");
                }
                ChoosePokemonModal.this.setVisible(false);
            }
        });
    }

    // EFFECTS: displays popup after cancelling team creation.
    private static void cancelledTeamCreationPopup(String filename, PokemonTeamBuilderUI owner, String name) {
        ImageIcon mankey = new ImageIcon(filename);
        Image mankey2 = mankey.getImage();
        Image mankey3 = mankey2.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        mankey = new ImageIcon(mankey3);
        JOptionPane.showMessageDialog(owner, name, "", JOptionPane.INFORMATION_MESSAGE, mankey);
    }

    // MODIFIES: this.
    // EFFECTS: creates action listener for addType button according to parameters passed.
    private void addTypeActionListener(JButton btn, JComboBox cbo, PokemonTeamBuilderUI owner) {
        btn.addActionListener(new ActionListener() {
            Pokemon pokemonToAdd;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pokemonToAdd = findPokemon((String) cbo.getSelectedItem());
                } catch (PokemonNotFoundException f) {
                    ChoosePokemonModal.this.setVisible(false);
                }
                if (getNewTeam().getPokemonList().size() != 6) {
                    String message = pokemonToAdd.getPokemonName() + " was added to your team.";
                    cancelledTeamCreationPopup("data/pikachu.png", owner, message);
                    getNewTeam().addPokemon(pokemonToAdd);
                    btnOK.setEnabled(true);
                } else {
                    cancelledTeamCreationPopup("data/snorlax.png", owner, "Your team is full!");
                }
            }
        });
    }

    // MODIFIES: this.
    // EFFECTS: adds elements to panels.
    private void addToPanels(JLabel nameLabel) {
        panel0.add(nameLabel);
        nameLabel.setFont(new Font("MV Boli", Font.BOLD, 15));

        panel1.add(new JLabel("NORMAL"));
        panel1.add(cboNormal);
        panel1.add(btnAddNormal);

        panel2.add(new JLabel("FIRE"));
        panel2.add(cboFire);
        panel2.add(btnAddFire);

        panel3.add(new JLabel("WATER"));
        panel3.add(cboWater);
        panel3.add(btnAddWater);

        panel4.add(new JLabel("ELECTRIC"));
        panel4.add(cboElectric);
        panel4.add(btnAddElectric);

        panel5.add(new JLabel("GRASS"));
        panel5.add(cboGrass);
        panel5.add(btnAddGrass);


        optionPanel.add(btnOK);
        btnOK.setEnabled(false);
        optionPanel.add(btnCancel);
    }

    // MODIFIES: this.
    // EFFECTS: searches through pokemon and adds to corresponding JCombo according to type.
    private void sortPokemon(PokemonTeamBuilderUI owner) {
        for (Pokemon p : owner.getAllPokemon()) {
            if (isNull(p.getType2())) {
                ifMonoType(p);
            } else {
                ifDualType(p);
            }
        }
    }

    // MODIFIES: this.
    // EFFECTS: instructions for adding mono type pokemon.
    private void ifMonoType(Pokemon p) {
        if (p.getType1().equals("NORMAL")) {
            cboNormal.addItem(p.getPokemonName());
        } else if (p.getType1().equals("FIRE")) {
            cboFire.addItem(p.getPokemonName());
        } else if (p.getType1().equals("WATER")) {
            cboWater.addItem(p.getPokemonName());
        } else if (p.getType1().equals("ELECTRIC")) {
            cboElectric.addItem(p.getPokemonName());
        } else if (p.getType1().equals("GRASS")) {
            cboGrass.addItem(p.getPokemonName());
        }
    }

    // MODIFIES: this.
    // EFFECTS: instructions for adding dual type pokemon.
    private void ifDualType(Pokemon p) {
        if (p.getType1().equals("NORMAL") || p.getType2().equals("NORMAL")) {
            cboNormal.addItem(p.getPokemonName());
        } else if (p.getType1().equals("FIRE") || p.getType2().equals("FIRE")) {
            cboFire.addItem(p.getPokemonName());
        } else if (p.getType1().equals("WATER") || p.getType2().equals("WATER")) {
            cboWater.addItem(p.getPokemonName());
        } else if (p.getType1().equals("ELECTRIC") || p.getType2().equals("ELECTRIC")) {
            cboElectric.addItem(p.getPokemonName());
        } else if (p.getType1().equals("GRASS") || p.getType2().equals("GRASS")) {
            cboGrass.addItem(p.getPokemonName());
        }
    }

    // MODIFIES: this.
    // EFFECTS: adds panels to frame;

    private void addPanels() {
        this.add(panel0);
        this.add(panel1);
        this.add(panel2);
        this.add(panel3);
        this.add(panel4);
        this.add(panel5);
        this.add(optionPanel);
    }

    // MODIFIES: this.
    // EFFECTS: sets style for panels.
    private void setPanelStyle() {
        panel1.setBackground(new Color(184, 189, 188));
        panel2.setBackground(new Color(255, 115, 115));
        panel3.setBackground(new Color(114, 169, 247));
        panel4.setBackground(new Color(242, 237, 75));
        panel5.setBackground(new Color(88, 224, 103));

        panel0.setPreferredSize(new Dimension(340, 35));
        panel1.setPreferredSize(new Dimension(340, 35));
        panel2.setPreferredSize(new Dimension(340, 35));
        panel3.setPreferredSize(new Dimension(340, 35));
        panel4.setPreferredSize(new Dimension(340, 35));
        panel5.setPreferredSize(new Dimension(340, 35));
        optionPanel.setPreferredSize(new Dimension(340, 35));
    }

    // MODIFIES: this.
    // EFFECTS: creates a JFrame.
    private void makeJFrame() {
        setBounds(550, 400, 340, 340);
        this.setLocationRelativeTo(null);
        Container dialogContent = getContentPane();
        dialogContent.setLayout(new FlowLayout());
        this.setResizable(false);
    }

    // EFFECTS: return the most recently created team.
    public Team getNewTeam() {
        int newTeamSize = owner.getUserList().getTeams().size();
        return owner.getUserList().getTeams().get(newTeamSize - 1);
    }

    // EFFECTS: find pokemon given pokemon name.
    public Pokemon findPokemon(String name) throws PokemonNotFoundException {
        for (Pokemon p : owner.getAllPokemon()) {
            if (name.equals(p.getPokemonName())) {
                return p;
            }
        }
        throw new PokemonNotFoundException("Pokemon not found.");
    }

}

