public class Komentotehdas {
    private HashMap<String, Komento> komennot;
    private Komento tuntematon;

    public Komentotehdas(IO io) {
        komennot = new HashMap<String, Komento>();
        komennot.put("summa", new Summa(io));
        komennot.put("tulo", new Tulo(io));
        komennot.put("nelio", new Nelio(io));
        tuntematon = new Tuntematon(io);
    }

    public Komento hae(String operaatio) {
        return komennot.getOrDefault(operaatio, tuntematon);
    }
}