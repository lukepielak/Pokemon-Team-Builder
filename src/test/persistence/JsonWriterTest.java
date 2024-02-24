package persistence;

import model.ListOfTeam;
import model.Pokemon;
import model.Team;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static model.PokemonType.ELECTRIC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        // CITATION: code taken from provided sample, JasonSerializationDemo, and appropriate adjustments were made.

        try {
            ListOfTeam lot = new ListOfTeam();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyListOfTeam() {
        try {
            ListOfTeam lot = new ListOfTeam();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyListOfTeam.json");
            writer.open();
            writer.write(lot);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyListOfTeam.json");
            lot = reader.read();
            assertEquals(0, lot.getTeams().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralListOfTeam() {
        try {
            ListOfTeam lot = new ListOfTeam();
            lot.addTeam(new Team("Electric Team"));

            lot.getTeams().get(0).addPokemon(new Pokemon("Pikachu", ELECTRIC, null));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralListOfTeam.json");
            writer.open();
            writer.write(lot);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralListOfTeam.json");
            lot = reader.read();

            ArrayList<Team> teams = lot.getTeams();
            assertEquals(1, teams.size());

            checkTeam("Electric Team", 1, lot.getTeams().get(0));


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
