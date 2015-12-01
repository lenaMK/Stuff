 package compte;/** Representation des balances du compte du client. Un objet vide de cette classe et  * cree et envoye avec le message de transaction; la banque le remplie avec des valeurs. */public class Balances{    /** Constructor.  Cree un objet dont les valeurs vont etre remplies plus tard, quand on retourne une valeur au createur.     */    public Balances()    {    }        /** Mutator.  Remplit les valeurs     *     *  @param total balance totale du compte     *  @param available balance disponible     */    public void setBalances(Argent total, Argent available)    {        this.total = total;        this.available = available;    }        /** Accessor balance totale     *     *  @return balance totale du compte     */    public Argent getTotal()    {        return total;    }        /** Accessor balance disponible     *     *  @return balance disponible     */    public Argent getAvailable()    {        return available;    }        // Instance variables        /** Balance totale courante dans le compte     */    private Argent total;        /** Balance disponible courante dans le compte      */    private Argent available;}