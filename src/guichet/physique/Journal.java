package guichet.physique;import compte.Message;import compte.Argent;import compte.Etat;import simulation.Simulation;/** Gestionnaire du journal interne du guichet. Dans un vrai guichet, ceci gere un composant physique;  * 	dans cette simulation, ca utilise les classes dans le package simulation. */ public class Journal{    /** Constructor     */    public Journal()    {    }        /** Ajoute l'envoi de message a la banque au journal     *     *  @param message le message a ajouter     */    public void logSend(Message message)    {        Simulation.getInstance().printLogLine("Message:   " + message.toString());     }        /** Ajoute la reception de message de la banque au journal     *     *  @param status l'etat retourne par la banque comme reponse     */    public void logResponse(Etat response)    {        Simulation.getInstance().printLogLine("Reponse:  " + response.toString());    }        /** Ajoute la distribution d'argent par le distributeur au journal     *     *  @param amount Le montant d'argent distribue     */    public void logCashDispensed(Argent amount)    {        Simulation.getInstance().printLogLine("Distribue: " + amount.toString());    }        /** Ajoute la reception d'une enveloppe du client au journal. Cette methode est appelee si      * une enveloppe a ete recu du client.     */    public void logEnvelopeAccepted()    {        Simulation.getInstance().printLogLine("Enveloppe:  recu");    }        public void logStartup()    {    	Simulation.getInstance().printLogLine("Etat: allum�");    }        public void logShutdown()    {    	Simulation.getInstance().printLogLine("Etat: �teint");    }        public void logCardRetained()    {    	Simulation.getInstance().printLogLine("Carte retenue");    }}