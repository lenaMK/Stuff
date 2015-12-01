package guichet;import guichet.physique.*;import java.net.InetAddress;import compte.Carte;import compte.Argent;/** Representation du guichet lui-meme.  Un objet de cette class "possede" les objets *  representants les composants du guichet, et des communications reseau, et est responsable  *  de creer les sessions client qui l'utilisent pour acceder aux composants. *  Cet une classe active - quand une instance de cette calsse est creee, *  ue thread est executee pour lancer le systeme. */ public class Guichet implements Runnable{    /** Constructor     *     *  @param id Identifiant unique du guichet     *  @param place l'adresse physique du guichet     *  @param bankName le nom de la banque proprietaire du guichet     *  @param bankAddress L'adress internet de la banque     */    public Guichet(int id, String place, String bankName, InetAddress bankAddress)    {        this.id = id;        this.place = place;        this.bankName = bankName;        this.bankAddress = bankAddress;                // Cree les objets correspondants aux composants du guichets.        log = new Journal();        cardReader = new LecteurCarte(this);        cashDispenser = new DistributeurArgent(log);        customerConsole = new ConsoleClient();        envelopeAcceptor = new FenteEnveloppe(log);        networkToBank = new ReseauBanque(log, bankAddress);        operatorPanel = new OperatorPanel(this);        receiptPrinter = new ImprimeurRecu();              // Mise en place des conditions initiales apres la creation du guichet                 state = OFF_STATE;        switchOn = false;        cardInserted = false;           }        // Methods correspondants aux responsabilites majeurs du guichet        /** Le programme va cree une Thread qui execute ce code.     */    public void run()    {        Session currentSession = null;                while (true)        {            switch(state)            {                case OFF_STATE:                                    customerConsole.display("Non disponible");                    synchronized(this)                    {                        try                        {                             wait();                        }                        catch(InterruptedException e)                        { }                    }                                        if (switchOn)                    {                        performStartup();                        state = IDLE_STATE;                    }                                                                break;                                    case IDLE_STATE:                                    customerConsole.display("Veuillez inserer votre carte");                    cardInserted = false;                                                            synchronized(this)                    {                        try                        {                             wait();                        }                        catch(InterruptedException e)                        { }                    }                                               if (cardInserted)                    {                        currentSession = new Session(this);                        state = SERVING_CUSTOMER_STATE;                    }                    else if (! switchOn)                    {                        performShutdown();                        state = OFF_STATE;                    }                                        break;                            case SERVING_CUSTOMER_STATE:                                                                            currentSession.performSession();                                        state = IDLE_STATE;                                        break;                            }        }    }                    /** Informe le guichet que le button ON de la console de l'operateur a ete pousse.     */    public synchronized void switchOn()    {        switchOn = true;        notify();    }        /** Informe le guichet que le button OFF de la console de l'operateur a ete pousse.     */    public synchronized void switchOff()    {        switchOn = false;        notify();    }        /** Informe le guichet qu'une carte a ete insere dans le lecteur de carte.     */    public synchronized void cardInserted()    {        cardInserted = true;        notify();    }        // Ces methodes permettent aux autres objets d'acceder au composants du guichets         /** Accessor pour id     *     *  @return id unique du guichet     */    public int getID()    {        return id;    }        /** Accessor pour adresse physique     *     *  @return adresse physique du guichet     */    public String getPlace()    {        return place;    }        /** Accessor pour nom de la banque     *     *  @return nom de la banque proprietaire du guichet     */    public String getBankName()    {        return bankName;    }        /** Accessor pour lecteur de carte     *     *  @return composant pour lecteur de carte du guichet     */    public LecteurCarte getCardReader()    {        return cardReader;    }        /** Accessor pour distributeur d'argent     *     *  @return composant distributeur d'argent du guichet     */    public DistributeurArgent getCashDispenser()    {        return cashDispenser;    }        /** Accessor pour console client     *     *  @return composant console client du guichet     */    public ConsoleClient getCustomerConsole()    {        return customerConsole;    }        /** Accessor pour fente enveloppe     *     *  @return  composant fente enveloppe du guichet     */    public FenteEnveloppe getEnvelopeAcceptor()    {        return envelopeAcceptor;    }        /** Accessor pour journal     *     *  @return composant journal du guichet     */    public Journal getLog()    {        return log;    }        /** Accessor pour reseau vers la banque     *     *  @return connection reseau vers la banque de ce guichet     */    public ReseauBanque getNetworkToBank()    {        return networkToBank;    }        /** Accessor pour panneau operateur     *     *  @return composant panneau operateur du guichet      */    public OperatorPanel getOperatorPanel()    {        return operatorPanel;    }        /** Accessor pour imprimeur de recu     *     *  @return composant imprimeur de recu du guichet     */    public ImprimeurRecu getReceiptPrinter()    {        return receiptPrinter;    }    // Methodes privees    /** Realise le cas d'utilisation StartUp      */    private void performStartup()    {        Argent initialCash = operatorPanel.getInitialCash();        cashDispenser.setInitialCash(initialCash);        networkToBank.openConnection();        log.logStartup();    }        /** Realise le cas d'utilisation ShutDown     */    private void performShutdown()    {        networkToBank.closeConnection();        log.logShutdown();    }            // Variable d'instances du guichet            /** ID unique du guichet     */    private int id;        /** adresse physique     */    private String place;        /** Nom de la banque     */    private String bankName;        /** Adress internet de la banque     */    private InetAddress bankAddress;            // Variables d'instances des composants du guichet        /** Lecteur carte     */    private LecteurCarte cardReader;        /** Distributeur d'argent     */    private DistributeurArgent cashDispenser;        /** Console client     */    private ConsoleClient customerConsole;        /** Fente enveloppe     */    private FenteEnveloppe envelopeAcceptor;        /** Journal du guichet     */    private Journal log;        /** Connection reseau a la banque     */    private ReseauBanque networkToBank;        /** Panneau operateur     */    private OperatorPanel operatorPanel;        /** Imprimeur Recu     */    private ImprimeurRecu receiptPrinter;        // Information sur l'etat        /** L'etat du guichet - une des valeurs ci dessous     */    private int state;        /** Vrai quand le button ON est clique. Faux quand le button OFF est clique.     */    private boolean switchOn;        /** Vrai si la carte est inserer - le guichet le remet a Faux lorsqu'il essaye de lire la carte     */    private boolean cardInserted;     // Valeurs possibles d'etats du guichet            /** Guichet OFF. Guichet doit etre mise en marcher (ON) pour fonctionner.     */    private static final int OFF_STATE = 0;        /** Guichet ON mais en attente. Peut servir un client ou etre eteint     */    private static final int IDLE_STATE = 1;        /** Guichet sert un client.     */    private static final int SERVING_CUSTOMER_STATE = 2;}