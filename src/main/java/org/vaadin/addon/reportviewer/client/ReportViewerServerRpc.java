package org.vaadin.addon.reportviewer.client;

import com.vaadin.shared.communication.ServerRpc;

public interface ReportViewerServerRpc extends ServerRpc {

	public void linkClicked(String parameters);

}
