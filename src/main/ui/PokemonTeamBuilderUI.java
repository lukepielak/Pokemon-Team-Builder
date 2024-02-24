package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;


import static model.PokemonType.*;

// CITATION: Modal dialogue boxes modelled with help from https://www.youtube.com/@CodingExamples
//           Video: https://www.youtube.com/watch?v=jbwoxGdIWIk&t=74s

// CITATION: JFrame layout and buttons modelled with help from https://www.youtube.com/@BroCodez
//           Video: https://www.youtube.com/watch?v=Kmgo00avvEw&t=2824s

// CITATION: Free Pokemon icons taken from https://www.flaticon.com/free-icons/pokemon

// Terminal UI.
public class PokemonTeamBuilderUI extends JFrame implements ActionListener {

    ListOfTeam userList = new ListOfTeam(); // initialize empty ListOfTeam.

    private JButton makeNewTeamButton;
    private JButton viewTeamsButton;
    private JButton removeButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton quitButton;

    private static final String JSON_STORE = "./data/pkmnteambuilder.json";
    private JTextArea ta;
    private JPanel mainPanel;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // all the Pokemon a user can choose from, each Pokemon is an object with a name and up to two types.
    Pokemon[] allPokemon =
            {new Pokemon("Bulbasaur", GRASS, POISON),
                    new Pokemon("Ivysaur", GRASS, POISON),
                    new Pokemon("Venusaur", GRASS, POISON),
                    new Pokemon("Charmander", FIRE, null),
                    new Pokemon("Charmeleon", FIRE, null),
                    new Pokemon("Charizard", FIRE, FLYING),
                    new Pokemon("Squirtle", WATER, null),
                    new Pokemon("Wartortle", WATER, null),
                    new Pokemon("Blastoise", WATER, null),
                    new Pokemon("Caterpie", BUG, null),
                    new Pokemon("Metapod", BUG, null),
                    new Pokemon("Butterfree", BUG, FLYING),
                    new Pokemon("Weedle", BUG, POISON),
                    new Pokemon("Kakuna", BUG, POISON),
                    new Pokemon("Beedrill", BUG, POISON),
                    new Pokemon("Pidgey", PokemonType.NORMAL, FLYING),
                    new Pokemon("Pidgeotto", PokemonType.NORMAL, FLYING),
                    new Pokemon("Pidgeot", PokemonType.NORMAL, FLYING),
                    new Pokemon("Rattata", PokemonType.NORMAL, null),
                    new Pokemon("Raticate", PokemonType.NORMAL, null),
                    new Pokemon("Spearow", PokemonType.NORMAL, FLYING),
                    new Pokemon("Fearow", PokemonType.NORMAL, FLYING),
                    new Pokemon("Ekans", POISON, null),
                    new Pokemon("Arbok", POISON, null),
                    new Pokemon("Pikachu", ELECTRIC, null),
                    new Pokemon("Raichu", ELECTRIC, null),
                    new Pokemon("Vaporeon", WATER, null)
            };

    // BorderLayout
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();

    // MODIFIES: this.
    // EFFECTS: creates the primary JFrame for the program and all the menu buttons.
    public PokemonTeamBuilderUI() {

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        mainPanel = new JPanel();
        
        // create buttons for GUI.
        makeButtonsMVR();
        makeButtonsLSQ();

        // create JFrame.
        makeFrame();

        // create border layout for JFrame.
        makeBorderLayout();

        // add JLabels;
        addLabels();

        // text area
        makeTextArea();

        // window listener for closing window
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                printEventLog();
            }
        });

        // enable visibility of the frame.
        this.setVisible(true);
    }

    public Pokemon[] getAllPokemon() {
        return allPokemon;
    }

    public ListOfTeam getUserList() {
        return this.userList;
    }

    // MODIFIES: this.
    // EFFECTS: handles menu buttons: makeNewTeamButton, viewTeamsButton, removeButton, saveButton, and loadButton.

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == makeNewTeamButton) {
            makeNewTeamAction();
        }

        if (e.getSource() == viewTeamsButton) {
            viewTeamsAction();
        }

        if (e.getSource() == removeButton) {
            removeTeamAction();
        }

        if (e.getSource() == saveButton) {
            try {
                saveListOfTeam();
                savePopup("data/jigglypuff.png", "Data saved.");
            } catch (Exception f) {
                savePopup("data/jigglypuff.png", "Data saved.");
            }
        }

        if (e.getSource() == loadButton) {
            loadAction();
        }

        if (e.getSource() == quitButton) {
            quitButtonBehaviour();
        }
    }

    // MODIFIES: this.
    // EFFECTS: controls behaviour for quitButton
    public void quitButtonBehaviour() {
        printEventLog();
        this.dispose();
    }

    @Override
    public synchronized void addWindowListener(WindowListener l) {
        super.addWindowListener(l);
    }

    // EFFECTS: displays popup for the save button.
    private void savePopup(String filename, String message) {
        ImageIcon jigglypuff = new ImageIcon(filename);
        Image jigglypuff2 = jigglypuff.getImage();
        Image jigglypuff3 = jigglypuff2.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        jigglypuff = new ImageIcon(jigglypuff3);
        JOptionPane.showMessageDialog(this, message, "", JOptionPane.INFORMATION_MESSAGE, jigglypuff);
    }

    // MODIFIES: this.
    // EFFECTS: generates text to display saved teams on menu.
    public String displayTeams() {
        String teamInfo = "";
        for (Team t : this.getUserList().getTeams()) {
            teamInfo = teamInfo + t.getTeamName() + " (" + t.getPokemonList().size() + " Pokemon)\n";
        }
        return teamInfo;
    }

    // MODIFIES: this.
    // EFFECTS: writes data to save file.
    private void saveListOfTeam() {
        try {
            jsonWriter.open();
            jsonWriter.write(userList);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this.
    // EFFECTS: loads workroom from file
    private void loadListOfTeam() {
        try {
            userList = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this.
    // EFFECTS: creates Make team, View team, and Remove team buttons.
    private void makeButtonsMVR() {
        // make new team button
        makeNewTeamButton = new JButton();
        makeNewTeamButton.setBounds(10, 10, 4, 4);
        makeNewTeamButton.addActionListener(this);
        makeNewTeamButton.setText("MAKE NEW TEAM");
        makeNewTeamButton.setFocusable(false);
        makeNewTeamButton.setForeground(new Color(48, 77, 77));

        // view teams button
        viewTeamsButton = new JButton();
        viewTeamsButton.setBounds(10, 10, 4, 4);
        viewTeamsButton.setText("VIEW TEAMS");
        viewTeamsButton.setFocusable(false);
        viewTeamsButton.addActionListener(this);
        viewTeamsButton.setForeground(new Color(48, 77, 77));
        viewTeamsButton.setEnabled(false);

        // remove team button
        removeButton = new JButton();
        removeButton.setBounds(10, 10, 4, 4);
        removeButton.setText("REMOVE TEAM");
        removeButton.setFocusable(false);
        removeButton.addActionListener(this);
        removeButton.setForeground(new Color(48, 77, 77));
        removeButton.setEnabled(false);
    }

    // MODIFIES: this.
    // EFFECTS: creates Load, Save and Quit buttons.
    private void makeButtonsLSQ() {
        // save button
        saveButton = new JButton();
        saveButton.setBounds(10, 10, 4, 4);
        saveButton.setText("SAVE");
        saveButton.setFocusable(false);
        saveButton.addActionListener(this);
        saveButton.setForeground(new Color(48, 77, 77));

        // load button
        loadButton = new JButton();
        loadButton.setBounds(10, 10, 4, 4);
        loadButton.setText("LOAD");
        loadButton.setFocusable(false);
        loadButton.addActionListener(this);
        loadButton.setForeground(new Color(48, 77, 77));

        // quit button
        quitButton = new JButton();
        quitButton.setBounds(10, 10, 4, 4);
        quitButton.setText("QUIT");
        quitButton.setFocusable(false);
        quitButton.addActionListener(this);
        quitButton.setForeground(new Color(48, 77, 77));

    }

    // MODIFIES: this.
    // EFFECTS: creates JFrame.
    private void makeFrame() {
        // JFrame
        this.setTitle("POKEMON TEAM BUILDER");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(715, 530);
        this.setLayout(new BorderLayout(5, 20));
        this.getContentPane().setBackground(new Color(57, 99, 99));
        this.setLocationRelativeTo(null);
    }

    // MODIFIES: this.
    // EFFECTS: creates border layout.
    private void makeBorderLayout() {

        panel1.setLayout(new BorderLayout());

        setPanels();

        JPanel panel6 = new JPanel();
        panel6.setBackground(new Color(48, 77, 77));
        panel1.setLayout(new BorderLayout());
        panel6.setPreferredSize(new Dimension(50, 40));
        panel1.add(panel6, BorderLayout.SOUTH);

        panel6.add(makeNewTeamButton);
        panel6.add(viewTeamsButton);
        panel6.add(removeButton);
        panel6.add(saveButton);
        panel6.add(loadButton);
        panel6.add(quitButton);
    }

    // MODIFIES: this.
    // EFFECTS: sets panel positions and style.
    private void setPanels() {
        panel1.setBackground(new Color(48, 77, 77));
        panel2.setBackground(new Color(57, 99, 99));
        panel3.setBackground(new Color(57, 99, 99));
        panel4.setBackground(new Color(57, 99, 99));
        mainPanel.setBackground(new Color(179, 179, 179));

        panel1.setPreferredSize(new Dimension(700, 80));
        panel2.setPreferredSize(new Dimension(30, 100));
        panel3.setPreferredSize(new Dimension(30, 100));
        panel4.setPreferredSize(new Dimension(700, 25));
        mainPanel.setPreferredSize(new Dimension(700, 100));

        this.add(panel1, BorderLayout.NORTH);
        this.add(panel2, BorderLayout.WEST);
        this.add(panel3, BorderLayout.EAST);
        this.add(panel4, BorderLayout.SOUTH);
        this.add(mainPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this.
    // EFFECTS: adds JLables.
    private void addLabels() {
        // title JLabel
        JLabel title = new JLabel();
        title.setText("POKEMON TEAM BUILDER");
        title.setForeground(new Color(179, 179, 179));
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("MV Boli", Font.BOLD, 20));
        panel1.add(title);

        // signature JLabel
        JLabel signature = new JLabel();
        signature.setText("Luke Pielak | CPSC 210 Project | 2023");
        signature.setForeground(new Color(53, 77, 77));
        signature.setHorizontalTextPosition(JLabel.CENTER);
        signature.setVerticalTextPosition(JLabel.CENTER);
        signature.setHorizontalAlignment(JLabel.LEFT);
        signature.setFont(new Font("MV Boli", Font.BOLD, 10));
        panel4.add(signature);
    }
    
    // MODFIES: this.
    // EFFECTS: creates text area.
    private void makeTextArea() {
        JTextArea ta = new JTextArea(21, 46);
        ta.setEditable(false);
        this.ta = ta;
        mainPanel.add(ta);
        ta.setBackground(new Color(179, 179, 179));
    }
    
    // MODIFIES: this.
    // EFFECTS: handles action for make new team button.
    private void makeNewTeamAction() {
        String strData = "";
        NameTeamModal ntm = new NameTeamModal(PokemonTeamBuilderUI.this, "Please choose a name for your new team");
        ntm.setModal(true);
        ntm.setVisible(true);
        if (getUserList().getTeams().size() > 0) {
            viewTeamsButton.setEnabled(true);
            removeButton.setEnabled(true);
        }

        strData = displayTeams();
        ta.setText(strData);
    }
    
    // MODIFIES: this.
    // EFFECTS: handles action for view teams button.
    private void viewTeamsAction() {
        ViewTeamsModal vtm = new ViewTeamsModal(PokemonTeamBuilderUI.this, "Select a team to view");
        vtm.setModal(true);
        vtm.setVisible(true);
    }

    // MODIFIES: this.
    // EFFECTS: handles action for remove teams button.
    private void removeTeamAction() {
        String strData = "";
        RemoveTeamModal rtm = new RemoveTeamModal(PokemonTeamBuilderUI.this, "Select a team to remove");
        rtm.setModal(true);
        rtm.setVisible(true);
        if (getUserList().getTeams().size() == 0) {
            viewTeamsButton.setEnabled(false);
            removeButton.setEnabled(false);
        }
        strData = displayTeams();
        ta.setText(strData);
    }

    // MODIFIES: this.
    // EFFECTS: handles action for load button.
    private void loadAction() {
        String strData = "";
        savePopup("data/eevee.png", "Data loaded.");
        loadListOfTeam();
        if (getUserList().getTeams().size() > 0) {
            viewTeamsButton.setEnabled(true);
            removeButton.setEnabled(true);
        }
        strData = displayTeams();
        ta.setText(strData);
    }

    // EFFECTS: prints all events in the event log to console.
    private void printEventLog() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.toString());
        }
        EventLog.getInstance().clear();
    }
}
