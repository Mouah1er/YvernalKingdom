package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.utils.TagUtils;
import org.bukkit.event.EventHandler;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

public class AsyncPlayerReceiveNameTagListener extends YvernalListener<AsyncPlayerReceiveNameTagEvent> {

    @Override
    @EventHandler
    public void onEvent(AsyncPlayerReceiveNameTagEvent event) {
        if (TagUtils.tagMap.containsKey(event.getUUID())) {
            event.setTag(TagUtils.tagMap.get(event.getUUID()).getValue());
        }
    }
}
