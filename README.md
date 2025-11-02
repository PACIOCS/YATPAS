# 🚀 YATPAS - Yet Another TPA System

![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/paciocs/YATPAS)
![Spigot version](https://img.shields.io/badge/Minecraft-1.20%2B-blueviolet)
![License](https://img.shields.io/github/license/paciocs/YATPAS)

YATPAS is a simple but robust Player-to-Player Teleport (TPA) system for Bukkit/Spigot/Paper servers. It features clickable requests, configurable cooldowns, timeouts, and automatic cancellation upon movement or damage, ensuring a balanced gameplay experience.

***

## 🇮🇹 IMPORTANT NOTE ON LANGUAGE

The default language for the messages inside the plugin's `config.yml` is **Italian**. You can easily translate all messages to English or any other language by editing the `messages:` section of the configuration file.

***

## ✨ Key Features

* **Clickable Requests:** Players can accept or deny TPA requests with a single click.
* **Correct TPA/TPAHERE Logic:** Supports both teleporting to another player (`/tpa`) and calling a player to your location (`/tpahere`).
* **Warmup:** Configurable time before the actual teleport, which is cancelled if the player moves or takes damage.
* **Cooldown & Timeout:** Limits request spam and ensures requests expire automatically.
* **Customizable Messages:** All messages are fully configurable via `config.yml`.

## 📦 Commands

| Command | Alias | Description | Permission |
| :--- | :--- | :--- | :--- |
| `/tpa <player>` | | Requests to teleport to the specified player. | `yatpas.use.tpa` |
| `/tpahere <player>` | | Requests the specified player to teleport to you. | `yatpas.use.tpahere` |
| `/tpaccept` | | Accepts the pending TPA request. | `yatpas.use.tpaccept` |
| `/tpdeny` | | Denies the pending TPA request. | `yatpas.use.tpdeny` |
| `/tpacancel` | | Cancels your outgoing TPA request. | `yatpas.use.tpacancel` |

## ⚙️ Configuration

The `config.yml` file allows you to customize every aspect of the TPA system.

| Config Key | Description | Default Value |
| :--- | :--- | :--- |
| `teleport-warmup-seconds` | Time in seconds before the teleport occurs. | `5` |
| `tpa-cooldown-seconds` | Cooldown between sending two TPA requests. | `2` |
| `request-timeout-seconds` | Time before a TPA request expires. | `60` |
| `cancel-on-move` | `true` to cancel warmup if the player moves. | `true` |
| `cancel-on-damage` | `true` to cancel warmup if the player takes damage. | `true` |
| `messages.prefix` | Prefix displayed in all messages. | `&3YATPAS&8» &f` |

---

## 🛠️ Installation and Compilation

### Requirements

* Java Development Kit (JDK) 17 or higher.
* Apache Maven.

### Download

You can download the latest compiled version [here](LINK_TO_YOUR_JAR).

### Compiling from Source

If you wish to compile the plugin from source:

1.  Clone the repository (replace the URL with yours):
    ```bash
    git clone [https://github.com/paciocs/YATPAS.git](https://github.com/paciocs/YATPAS.git)
    cd YATPAS
    ```

2.  Compile the project using Maven:
    ```bash
    mvn clean package
    ```

3.  The compiled JAR file (`YATPAS-1.0.0.jar`) will be found in the `target/` folder.

## 📜 License

This project is licensed under the [MIT License](LICENSE).

## 🙋 Contact and Support

If you find any bugs or want to suggest new features, please open an Issue on GitHub!

---
