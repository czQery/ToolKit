package cz.qery.toolkit.loader;

import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.layer.SimpleLayer;
import net.pl3x.map.core.markers.marker.Circle;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collection;

import static cz.qery.toolkit.loader.LunarWaypoint.waypoints;

public class Pl3xWaypoint extends SimpleLayer {

    public Pl3xWaypoint(String key) {
        super(key, () -> "Waypoints");
    }

    @Override
    public @NonNull Collection<Marker<?>> getMarkers() {

        Collection<Marker<?>> list = new ArrayList<>();

        for (LunarWaypoint wp : waypoints) {
            if (!getKey().replace("toolkit_", "").equals(wp.world())) {
                continue;
            }

            Circle icon = Marker.circle(wp.name(), Point.of(wp.x(), wp.z()), 1);
            icon.setOptions(Options.builder()
                    .tooltipContent("<div>" + wp.name() + "</div>")
                    .strokeColor(java.awt.Color.decode(wp.color()).getRGB())
                    .fillColor(java.awt.Color.decode(wp.color()).getRGB())
                    .build());

            list.add(icon);
        }

        return list;
    }
}