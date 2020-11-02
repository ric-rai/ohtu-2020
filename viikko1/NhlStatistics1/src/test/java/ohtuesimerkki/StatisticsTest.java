package ohtuesimerkki;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StatisticsTest {
    Reader readerStub = new Reader() {

        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<>();

            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri",   "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));

            return players;
        }
    };

    Statistics stats;

    @Before
    public void setUp(){
        // luodaan Statistics-olio joka käyttää "stubia"
        stats = new Statistics(readerStub);
    }

    @Test
    public void searchWorksCorrectly() {
        Player player = stats.search("Semenko");
        assertEquals(player.toString(), new Player("Semenko", "EDM", 4, 12).toString());
    }

    @Test
    public void teamWorksCorrectly() {
        ArrayList<Player> team = new ArrayList<>();
        team.add(new Player("Lemieux", "PIT", 45, 54));
        assertEquals(team.get(0).toString(), stats.team("PIT").get(0).toString());
    }

    @Test
    public void topScorersWorksCorrectly() {
        List<Player> players = stats.topScorers(1);
        Player top = new Player("Gretzky", "EDM", 35, 89);
        assertEquals(top.toString(), players.get(0).toString());
    }
}
