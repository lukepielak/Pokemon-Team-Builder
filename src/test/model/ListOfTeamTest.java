package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// tests for methods in the listofteam class.
class ListOfTeamTest {
    private ListOfTeam list1;

    @BeforeEach
    void runBefore() {
        list1 = new ListOfTeam();
    }

    @Test
    void testConstructor() {
        assertEquals(0, list1.getTeams().size());
    }

    @Test
    void testAddTeam1() {
        // add to an empty listofteam.
        Team testTeam = new Team("test");
        list1.addTeam(testTeam);
        assertEquals(1, list1.getTeams().size());
    }

    @Test
    void testAddTeam2() {
        // add to a listofteam with one element.
        Team testTeam1 = new Team("test1");
        Team testTeam2 = new Team("test2");
        list1.addTeam(testTeam1);
        list1.addTeam(testTeam2);
        assertEquals(2, list1.getTeams().size());
    }

    @Test
    void testRemoveTeam1() {
        // try to remove from an empty listofteam.
        assertEquals(0, list1.getTeams().size());
        list1.removeTeam("test1");
        assertEquals(0, list1.getTeams().size());

    }

    @Test
    void testRemoveTeam2() {
        // remove first index from listofteam with two elements.
        Team testTeam1 = new Team("test1");
        Team testTeam2 = new Team("test2");
        list1.addTeam(testTeam1);
        list1.addTeam(testTeam2);
        list1.removeTeam("test1");
        assertEquals(1, list1.getTeams().size());
    }

    @Test
    void testRemoveTeam3() {
        // remove second index from listofteam with two elements.
        Team testTeam1 = new Team("test1");
        Team testTeam2 = new Team("test2");
        list1.addTeam(testTeam1);
        list1.addTeam(testTeam2);
        try {
            list1.removeTeam("test2");
            fail("Exception expected.");
        } catch (Exception e) {
            // pass
            assertEquals(1, list1.getTeams().size());
        }

    }


}