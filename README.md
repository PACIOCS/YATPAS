# 🚀 YATPAS - Yet Another TPA System

![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/paciocs.xyz/YATPAS)
![Spigot version](https://img.shields.io/badge/Minecraft-1.20%2B-blueviolet)
![License](https://img.shields.io/github/license/paciocs.xyz/YATPAS)

YATPAS è un sistema di teletrasporto Player-to-Player (TPA) semplice, ma robusto, per server Bukkit/Spigot/Paper. Offre richieste cliccabili, cooldown configurabili, timeout e cancellazione automatica in caso di movimento o danno, garantendo un'esperienza di gioco equilibrata.

## ✨ Caratteristiche Principali

* **Richieste Cliccabili:** I giocatori possono accettare o rifiutare le richieste TPA con un semplice click.
* **Logica TPA/TPAHERE Corretta:** Supporta sia l'andare da un altro giocatore (`/tpa`) sia il richiamarlo a sé (`/tpahere`).
* **Warmup (Tempo di Attesa):** Tempo configurabile prima del teletrasporto effettivo, annullato se il giocatore si muove o subisce danni.
* **Cooldown & Timeout:** Limita lo spam di richieste e assicura che le richieste scadano automaticamente.
* **Messaggi Personalizzabili:** Tutti i messaggi sono configurabili tramite `config.yml`.

## 📦 Comandi

| Comando | Alias | Descrizione | Permesso |
| :--- | :--- | :--- | :--- |
| `/tpa <giocatore>` | | Richiede di teletrasportarsi al giocatore specificato. | `yatpas.use.tpa` |
| `/tpahere <giocatore>` | | Richiede al giocatore specificato di teletrasportarsi da te. | `yatpas.use.tpahere` |
| `/tpaccept` | | Accetta la richiesta TPA in sospeso. | `yatpas.use.tpaccept` |
| `/tpdeny` | | Nega la richiesta TPA in sospeso. | `yatpas.use.tpdeny` |
| `/tpacancel` | | Annulla la tua richiesta TPA in uscita. | `yatpas.use.tpacancel` |

## ⚙️ Configurazione

Il file `config.yml` ti permette di personalizzare ogni aspetto del sistema TPA.

| Chiave Config | Descrizione | Valore di Default |
| :--- | :--- | :--- |
| `teleport-warmup-seconds` | Tempo in secondi prima del teletrasporto. | `5` |
| `tpa-cooldown-seconds` | Tempo di attesa tra l'invio di due richieste. | `2` |
| `request-timeout-seconds` | Tempo prima che una richiesta TPA scada. | `60` |
| `cancel-on-move` | `true` per annullare il warmup se il giocatore si muove. | `true` |
| `cancel-on-damage` | `true` per annullare il warmup se il giocatore subisce danni. | `true` |
| `messages.prefix` | Prefisso visualizzato in tutti i messaggi. | `&3YATPAS&8» &f` |

---

## 🛠️ Installazione e Compilazione

### Requisiti

* Java Development Kit (JDK) 17 o superiore.
* Apache Maven.

### Download

Puoi scaricare l'ultima versione compilata [qui](LINK_AL_TUO_JAR).

### Compilazione da Sorgente

Se desideri compilare il plugin dai sorgenti:

1.  Clona il repository (sostituisci l'URL con il tuo):
    ```bash
    git clone [https://github.com/tuo_utente/YATPAS.git](https://github.com/tuo_utente/YATPAS.git)
    cd YATPAS
    ```

2.  Compila il progetto utilizzando Maven:
    ```bash
    mvn clean package
    ```

3.  Il file JAR compilato (`YATPAS-1.0.0.jar`) si troverà nella cartella `target/`.

## 📜 Licenza

Questo progetto è distribuito sotto licenza [MIT License](LICENSE).

## 🙋 Contatti e Supporto

Se trovi bug o desideri suggerire nuove funzionalità, apri una Issue su GitHub o invia una mail a info@paciocs.xyz!

---

*Questo plugin è stato sviluppato come progetto open-source.*
