package fr.stelycube.stelyprivatemessage.message;

import org.jetbrains.annotations.NotNull;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SendMessageProcess {

    private final BaseComponent sendMessage;
    private final SpyManager spyManager;

    public SendMessageProcess(@NotNull BaseComponent prefixComponent) {
        this.prefixComponent = prefixComponent;
    }

    public void sendMessage(@NotNull ProxiedPlayer sender, @NotNull ProxiedPlayer receiver, @NotNull String message) {
        final var formattedMessage = prefixComponent.duplicate();
        formattedMessage.addExtra(message);
        receiver.sendMessage(formattedMessage);
        spyManager.collectSpyPlayers(message);
    }

}
