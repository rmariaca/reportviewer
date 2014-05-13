package org.vaadin.addon.reportviewer;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.vaadin.util.ReflectTools;

public interface LinkClickListener extends Serializable {
	public static final Method LINK_CLICKED_METHOD = ReflectTools.findMethod(LinkClickListener.class, "linkClicked", LinkClickEvent.class);

	public void linkClicked(LinkClickEvent event);

}
