package org.vaadin.addon.reportviewer;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.ui.Component;

public class LinkClickEvent extends Component.Event {
	private static final long serialVersionUID = 1L;

	private String parameters;

	public LinkClickEvent(Component source, String parameters) {
		super(source);
		this.parameters = parameters;
	}

	public String getParameters() {
		return parameters;
	}

	public Map<String, String> getParameterMap() {
    Map<String, String> parameterMap = new HashMap<String, String>();
    if (parameters != null && parameters.length() > 0) {
    	for (String pair : parameters.split("&")) {
        String[] values = pair.split("=");
        if (values[0].length() == 0) {
          continue;
        }
        parameterMap.put(values[0], values.length > 1 ? values[1] : "");
      }
    }
		return parameterMap;
	}
}
