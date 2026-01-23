package cz.qery.toolkit.loader;

import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.player.PlayerRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class Pl3xMapPlayers {

    @Nullable
    private static PlayerRegistry list;

    public static void Load() {
        list = Pl3xMap.api().getPlayerRegistry();
    }

    public static void Show(UUID uuid) {
        if (list == null) {
            return;
        }
        
        Objects.requireNonNull(list.get(uuid)).setHidden(false, false);
    }

    public static void Hide(UUID uuid) {
        if (list == null) {
            return;
        }

        Objects.requireNonNull(list.get(uuid)).setHidden(true, false);
    }
}
