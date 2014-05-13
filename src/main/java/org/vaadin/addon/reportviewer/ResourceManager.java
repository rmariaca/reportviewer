package org.vaadin.addon.reportviewer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ResourceManager implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Map<String, IResourceProvider> resources;

	public ResourceManager() {
		this.resources = new HashMap<String, IResourceProvider>();
	}

	public String put(IResourceProvider resourceProvider) {
		String id = generateId();
		resources.put(id, resourceProvider);
		return id;
	}

	public void put(String id, IResourceProvider resourceProvider) {
		resources.put(id, resourceProvider);
	}

	public IResourceProvider get(String id) {
		return resources.get(id);
	}

	public IResourceProvider remove(String id) {
		IResourceProvider resource = resources.remove(id);
		if (resource != null) {
			resource.clear();
		}
		return resource;
	}

	public void clear() {
		for (IResourceProvider resource : resources.values()) {
			resource.clear();
		}
		resources.clear();
	}

	public String generateId() {
		UUID uniqueKey = UUID.randomUUID();
		return uniqueKey.toString();
	}

}
