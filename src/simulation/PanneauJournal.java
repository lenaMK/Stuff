package simulation;import java.awt.*;import java.awt.event.*;/** Le panneau qui affiche le journal interne */class PanneauJournal extends Panel{    /** Constructor     *     *  @param gui L'inteface du guichet     */         PanneauJournal(final GUI gui)    {        GridBagLayout logLayout = new GridBagLayout();        setLayout(logLayout);        setFont(new Font("Monospaced", Font.PLAIN, 14));                Label logPanelLabel = new Label("Journal", Label.CENTER);        add(logPanelLabel);        GridBagConstraints constraints =             GUI.makeConstraints(0, 0, 1, 1, GridBagConstraints.NONE);        constraints.weighty = 0;        logLayout.setConstraints(logPanelLabel, constraints);                     logPrintArea = new TextArea();        logPrintArea.setBackground(Color.white);        logPrintArea.setForeground(Color.black);        logPrintArea.setFont(new Font("Monospaced", Font.PLAIN, 12));        logPrintArea.setEditable(false);                add(logPrintArea);        constraints = GUI.makeConstraints(1, 0, 1, 1, GridBagConstraints.BOTH);        constraints.weighty = 1;        logLayout.setConstraints(logPrintArea, constraints);                Panel logButtonPanel = new Panel();        logButtonPanel.setLayout(new FlowLayout());                Button clearLogButton = new Button("Effacer journal");        clearLogButton.addActionListener(new ActionListener() {            public void actionPerformed(ActionEvent e)            {                logPrintArea.setText("");            }        });        logButtonPanel.add(clearLogButton);                Button dismissLogButton = new Button("Cacher Journal");        dismissLogButton.addActionListener(new ActionListener() {            public void actionPerformed(ActionEvent e)            {                gui.showCard("GUICHET");            }        });                logButtonPanel.add(dismissLogButton);        add(logButtonPanel);        constraints = GUI.makeConstraints(2, 0, 1, 1, GridBagConstraints.NONE);        constraints.weighty = 0;        logLayout.setConstraints(logButtonPanel, constraints);    }        /** Ajoute du texte au journal     *     *  @param text Le text a etre imprimer     */    void println(String text)    {        logPrintArea.append(text + "\n");    }        /** La region ou le journal doit etre imprimer     */    private TextArea logPrintArea;}                    