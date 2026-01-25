package cz.qery.toolkit.loader;

import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.layer.SimpleLayer;
import net.pl3x.map.core.markers.marker.Circle;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collection;

public class Pl3xMapWaypoints extends SimpleLayer {

    public Pl3xMapWaypoints(String key) {
        super(key, () -> "Waypoints");
    }

    @Override
    public @NonNull Collection<Marker<?>> getMarkers() {

        Collection<Marker<?>> list = new ArrayList<>();

        for (Waypoints wp : Waypoints.list) {
            if (!getKey().replace("toolkit_", "").equals(wp.world())) {
                continue;
            }

            Circle icon = Marker.circle(wp.name(), Point.of(wp.x(), wp.z()), 3);
            icon.setOptions(Options.builder()
                    .tooltipContent("<div>" + wp.name() + "</div>")
                    .strokeColor(java.awt.Color.decode(wp.color()).getRGB())
                    .strokeWeight(3)
                    .fillColor(0x00000000)
                    .build());

            list.add(icon);
        }

        return list;
    }
}