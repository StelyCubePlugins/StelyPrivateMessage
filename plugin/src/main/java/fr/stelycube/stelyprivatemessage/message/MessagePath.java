package fr.stelycube.stelyprivatemessage.message;

import org.jetbrains.annotations.NotNull;

public record MessagePath(@NotNull String from, @NotNull String to) {
}
