package org.vaadin.addon.reportviewer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.addon.reportviewer.client.ReportViewerServerRpc;
import org.vaadin.addon.reportviewer.client.ReportViewerState;

import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.ui.AbstractComponent;

public class ReportViewer extends AbstractComponent {
	private static final long serialVersionUID = 1L;

	public static final String PREVIEW_URL_PATTERN = "preview";
	public static final String ID_PARAMETER = "id";
	public static final String NAME_PARAMETER = "name";

	private Map<String, String> reports = new LinkedHashMap<String, String>();
	private String selectedReport;

	public ReportViewer() {
		registerRpc(new ReportViewerServerRpcImpl());
	}

	public void addReport(String id) {
		String url = getReportUrl(id);
		reports.put(id, url);
		setSelectedReport(id);
		markAsDirty();
	}

	public void removeReport(String id) {
		reports.remove(id);
		if (id.equals(selectedReport)) {
			if (!reports.isEmpty()) {
				List<String> keys = new ArrayList<String>(reports.keySet());
				setSelectedReport(keys.get(keys.size() - 1));
			}
			else {
				setSelectedReport(null);
			}
		}
		markAsDirty();
	}

	public void setSelectedReport(String id) {
		selectedReport = id;
		getState().setReport(reports.get(id));
		markAsDirty();
	}

	public String getSelectedReport() {
		return selectedReport;
	}

	public int getReportSize() {
		return reports.size();
	}

  public void addLinkClickListener(LinkClickListener listener) {
  	addListener(LinkClickEvent.class, listener, LinkClickListener.LINK_CLICKED_METHOD);
  }

  public void removeLinkClickListener(LinkClickListener listener) {
  	removeListener(LinkClickEvent.class, listener, LinkClickListener.LINK_CLICKED_METHOD);
  }

	@Override
	public ReportViewerState getState() {
		return (ReportViewerState) super.getState();
	}

	public static String getReportUrl(String id) {
		return getServletPath() + "/" + PREVIEW_URL_PATTERN + "?" +
				ID_PARAMETER + "=" + id + "&" +
				NAME_PARAMETER + "=report";
	}

	public static String getImageUrl(String id) {
		return getServletPath() + "/" + PREVIEW_URL_PATTERN + "?" +
				ID_PARAMETER + "=" + id + "&" +
				NAME_PARAMETER + "=";
	}

	private static String getServletPath() {
		String servletPath = VaadinServlet.getCurrent().getServletContext().getContextPath()
				+ VaadinServletService.getCurrentServletRequest().getServletPath();
		return servletPath;
	}

	private class ReportViewerServerRpcImpl implements ReportViewerServerRpc {
		private static final long serialVersionUID = 1L;

		@Override
		public void linkClicked(String parameters) {
			fireEvent(new LinkClickEvent(ReportViewer.this, parameters));
		}

	}
}
