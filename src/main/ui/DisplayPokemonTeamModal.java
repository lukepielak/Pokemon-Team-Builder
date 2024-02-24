package ui;

import model.Pokemon;
import model.Team;
import model.exception.TeamNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

// modal dialog box for display pokemon team
public class DisplayPokemonTeamModal extends JDialog {

    JButton btnDone = new JButton("Done");

    PokemonTeamBuilderUI owner;
    String name;
    Team teamToDisplay;
    JDialog prevModal;
    HashMap<String, Color> typeColor;

    JPanel panel0 = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();
    JPanel panel5 = new JPanel();
    JPanel panel6 = new JPanel();
    JPanel panel7 = new JPanel();

    // MODIFIES: this.
    // EFFECTS: creates a modal dialog box that displays a team that was chosen by the user.
    public DisplayPokemonTeamModal(PokemonTeamBuilderUI owner, String title, String name, JDialog prevModal) {
        super(owner, title);

        this.owner = owner;
        this.name = name;
        this.prevModal = prevModal;

        HashMap<String, Color> typeColor = getTypeColor();

        this.typeColor = typeColor;

        ArrayList<JPanel> panelList = getjPanels();

        // try to find team that should be displayed, catches exception if no team is found.
        try {
            teamToDisplay = this.findTeam();
        } catch (TeamNotFoundException e) {
            DisplayPokemonTeamModal.this.setVisible(false);
        } catch (Exception f) {
            DisplayPokemonTeamModal.this.setVisible(false);
        }

        // make jframe
        makeJFrame();

        // set dimensions for panels
        setPanelDimensions();

        // add panels to frame
        addPanels();

        // add elements to panels
        addToPanels(name, panel0, panel7, panelList);

        // action listener for done button, disables modal and re-enables previous modal to continue looking at teams.
        doneActionListener(prevModal);

    }

    // MODIFIES: this.
    // EFFECTS: action listener for done button, closes window after user is finished viewing a team.
    private void doneActionListener(JDialog prevModal) {
        btnDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayPokemonTeamModal.this.setVisible(false);
                prevModal.setVisible(true);
            }
        });
    }

    // MODIFIES: this.
    // EFFECTS: adds panels to frame.
    private void addPanels() {
        this.add(panel0);
        this.add(panel1);
        this.add(panel2);
        this.add(panel3);
        this.add(panel4);
        this.add(panel5);
        this.add(panel6);
        this.add(panel7);
    }

    // MODIFIES: this.
    // EFFECTS: sets panel dimensions.
    private void setPanelDimensions() {
        panel0.setPreferredSize(new Dimension(340, 30));
        panel1.setPreferredSize(new Dimension(340, 30));
        panel2.setPreferredSize(new Dimension(340, 30));
        panel3.setPreferredSize(new Dimension(340, 30));
        panel4.setPreferredSize(new Dimension(340, 30));
        panel5.setPreferredSize(new Dimension(340, 30));
        panel6.setPreferredSize(new Dimension(340, 30));
        panel7.setPreferredSize(new Dimension(340, 30));
    }

    // EFFECTS: adds panels to a list.
    private ArrayList<JPanel> getjPanels() {
        ArrayList<JPanel> panelList = new ArrayList<JPanel>();
        panelList.add(panel1);
        panelList.add(panel2);
        panelList.add(panel3);
        panelList.add(panel4);
        panelList.add(panel5);
        panelList.add(panel6);
        return panelList;
    }

    // MODIFIES: this.
    // EFFECTS: adds elements to panels.
    private void addToPanels(String name, JPanel panel0, JPanel panel7, ArrayList<JPanel> panelList) {
        JLabel nameLabel = new JLabel(name);
        panel0.add(nameLabel);
        nameLabel.setFont(new Font("MV Boli", Font.BOLD, 15));

        insertPokemon(panelList);

        panel7.add(btnDone);
    }

    // MODIFIES: this.
    // EFFECTS: creates a JFrame.
    private void makeJFrame() {
        setBounds(550, 400, 340, 325);
        this.setLocationRelativeTo(null);
        Container dialogContent = getContentPane();
        dialogContent.setLayout(new FlowLayout());
        this.setResizable(false);
    }

    // EFFECTS: HashMap of Pokemon types to corresponding Colors, used to choose panel colors during team display.
    private static HashMap<String, Color> getTypeColor() {
        HashMap<String, Color> typeColor = new HashMap<String, Color>();
        typeColor.put("NORMAL", new Color(184, 189, 188));
        typeColor.put("FIRE", new Color(255, 115, 115));
        typeColor.put("WATER", new Color(114, 169, 247));
        typeColor.put("ELECTRIC", new Color(242, 237, 75));
        typeColor.put("GRASS", new Color(88, 224, 103));
        typeColor.put("ICE", new Color(127, 250, 248));
        typeColor.put("FIGHTING", new Color(222, 119, 60));
        typeColor.put("POISON", new Color(184, 111, 247));
        typeColor.put("GROUND", new Color(163, 115, 67));
        typeColor.put("FLYING", new Color(171, 245, 225));
        typeColor.put("PSYCHIC", new Color(237, 114, 221));
        typeColor.put("BUG", new Color(172, 245, 108));
        typeColor.put("ROCK", new Color(112, 88, 75));
        typeColor.put("GHOST", new Color(93, 82, 117));
        typeColor.put("DRAGON", new Color(71, 64, 247));
        typeColor.put("DARK", new Color(79, 79, 79));
        typeColor.put("STEEL", new Color(156, 154, 154));
        typeColor.put("FAIRY", new Color(252, 194, 251));
        return typeColor;
    }

    // EFFECTS: searches list of team and returns the team chosen by the user for display.
    public Team findTeam() throws TeamNotFoundException {
        for (Team t : owner.getUserList().getTeams()) {
            if (name.equals(t.getTeamName())) {
                return t;
            }
        }
        throw new TeamNotFoundException("Pokemon not found.");
    }

    // MODIFIES: this.
    // EFFECTS: adds pokemon in teamToDisplay to each panel and changes panel color according to pokemonType.
    public void insertPokemon(ArrayList<JPanel> panels) {
        int i = 0;
        for (Pokemon p : teamToDisplay.getPokemonList()) {
            if (p.getType2() == null) {
                panels.get(i).add(new JLabel(p.getPokemonName() + " | Type: " + p.getType1()));
                panels.get(i).setBackground(typeColor.get(p.getType1()));
            } else {
                panels.get(i).add(new JLabel(p.getPokemonName() + " | Type: " + p.getType1() + " " + p.getType2()));
                panels.get(i).setBackground(typeColor.get(p.getType1()));
            }
            i++;
        }
    }
}