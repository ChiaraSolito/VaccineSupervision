# VaccineSupervision
Progetto di Ingegneria del Software a cura di Chiara Solito e Virginia Filippi.\
III Anno Bioinformatica - A.A. 2021/22
> Si vuole progettare un sistema software per gestire le segnalazioni di reazioni avverse (ad esempio, asma, dermatiti, insufficienza renale, …) da farmaci.\
\
Ogni segnalazione è caratterizzata da un codice univoco, dall’indicazione del paziente a cui fa riferimento,dall’indicazione della reazione avversa, dalla data della reazione avversa, dalla data di segnalazione, e dalle
terapie farmacologiche in atto al momento della reazione avversa.\
Per ogni paziente sono memorizzati: un codice univoco, l’anno di nascita, la provincia di residenza e la professione. Per ogni paziente è possibile memorizzare gli eventuali fattori di rischio presenti (paziente fumatore,
iperteso, sovrappeso, …), anche più d’uno.\ Ogni fattore di rischio è caratterizzato da un nome univoco, una descrizione e il livello di rischio associato.\
Ogni terapia farmacologica è caratterizzata da: paziente a cui si riferisce, segnalazioni a cui è legata, farmaco somministrato, dose, frequenza giornaliera, data di inizio e data di fine della terapia stessa.\
Per ogni reazione avversa sono memorizzati un nome univoco, un livello di gravità (da 1 a 5) e una descrizione generale, espressa in
linguaggio naturale. Una reazione avversa può essere legata a molte segnalazioni. Per ogni paziente sono memorizzati per ogni anno il numero di reazioni avverse segnalate ed il numero di terapie farmacologiche relative.\
\
Il sistema deve supportare i medici che effettuano la segnalazione. Dopo opportuna autenticazione, il medico viene introdotto ad una interfaccia che permette l’inserimento dei dati delle reazioni avverse e dei pazienti. Il codice
univoco dei pazienti è gestito dal sistema, che tiene traccia dei pazienti indicati da ogni medico. Ogni medico vede solo i codici identificativi dei pazienti, dei quali ha già segnalato qualche reazione avversa.\
\
Ad ogni fine settimana o quando il numero di segnalazioni raggiunge la soglia di 50, il sistema manda un avviso ad uno dei farmacologi responsabili della gestione delle segnalazioni di reazioni avverse. Il farmacologo, dopo
autenticazione, accede alle segnalazioni e può effettuare alcune analisi di base (quante segnalazioni per farmaco,
quante segnalazioni gravi in settimana, ...)\
Il sistema, inoltre, avvisa il farmacologo quando un farmaco ha accumulato nell’anno oltre 10 segnalazioni di gravità superiore a 3.
In base alle segnalazioni e agli avvisi del sistema, il farmacologo può proporre di ritirare il farmaco dal commercio immediatamente, di attivare una fase di controllo del farmaco, di mettere il farmaco fra quelli che richiedono un
monitoraggio più attento. Tali proposte vengono registrate dal sistema, che tiene traccia di tutte le proposte relative ai farmaci segnalati.
