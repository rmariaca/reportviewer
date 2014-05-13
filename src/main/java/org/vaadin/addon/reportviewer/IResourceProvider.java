package org.vaadin.addon.reportviewer;

import java.io.IOException;
import java.io.Serializable;

public interface IResourceProvider extends Serializable {

	public String getMimeType(String name);

	public byte[] getBytes(String name) throws IOException;

	public boolean isEmpty();

	public void clear();
}
