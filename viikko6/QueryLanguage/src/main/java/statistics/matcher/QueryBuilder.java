package statistics.matcher;


import java.util.ArrayList;

public class QueryBuilder {
    ArrayList<Matcher> matchers = new ArrayList<>();

    public Matcher build() {
        if (matchers.isEmpty()) {
            return new All();
        } else {
            return new And(matchers.toArray(new Matcher[0]));
        }
    }

    public QueryBuilder playsIn(String string) {
        matchers.add(new PlaysIn(string));
        return this;
    }

    public QueryBuilder hasAtLeast(int n, String s) {
        matchers.add(new HasAtLeast(n, s));
        return this;
    }

    public QueryBuilder hasFewerThan(int n, String s) {
        matchers.add(new HasFewerThan(n, s));
        return this;
    }

    public QueryBuilder oneOf(Matcher... matchers) {
         this.matchers.add(new Or(matchers));
         return this;
    }

}
