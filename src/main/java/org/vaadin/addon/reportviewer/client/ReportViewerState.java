package org.vaadin.addon.reportviewer.client;

import com.vaadin.shared.AbstractComponentState;

public class ReportViewerState extends AbstractComponentState {
	private static final long serialVersionUID = 1L;

	private String report;

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

}