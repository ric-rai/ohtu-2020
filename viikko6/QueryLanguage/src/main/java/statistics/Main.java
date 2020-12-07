package statistics;

import statistics.matcher.*;

public class Main {

    public static void main(String[] args) {
        String url = "https://nhlstatisticsforohtu.herokuapp.com/players.txt";
        Statistics stats = new Statistics(new PlayerReaderImpl(url));
          
        Matcher m = new And( new HasAtLeast(5, "goals"),
                             new HasAtLeast(5, "assists"),
                             new PlaysIn("PHI")
        );
        stats.matches(m).forEach(System.out::println);
        System.out.println();

        m = new And(
                new Not( new HasAtLeast(1, "goals") ),
                new PlaysIn("NYR")
        );
        stats.matches(m).forEach(System.out::println);
        System.out.println();

        m = new Or( new HasAtLeast(40, "goals"),
                new HasAtLeast(60, "assists")
        );
        stats.matches(m).forEach(System.out::println);
        System.out.println();

        System.out.println(stats.matches(new All()).size());
        System.out.println();

        m = new And(
                new HasAtLeast(50, "points"),
                new Or(
                        new PlaysIn("NYR"),
                        new PlaysIn("NYI"),
                        new PlaysIn("BOS")
                )
        );
        stats.matches(m).forEach(System.out::println);
        System.out.println();

        QueryBuilder query = new QueryBuilder();
        m = query.playsIn("NYR").build();
        stats.matches(m).forEach(System.out::println);
        System.out.println();

        m = query.playsIn("NYR")
                .hasAtLeast(5, "goals")
                .hasFewerThan(10, "goals").build();
        stats.matches(m).forEach(System.out::println);
        System.out.println();

        m = query.oneOf(
                query.playsIn("PHI")
                        .hasAtLeast(10, "assists")
                        .hasFewerThan(5, "goals").build(),

                query.playsIn("EDM")
                        .hasAtLeast(40, "points").build()
        ).build();
        stats.matches(m).forEach(System.out::println);
        System.out.println();
        System.out.println("THE END");
    }

}
