package org.vaadin.addon.reportviewer;

import java.io.IOException;
import java.io.OutputStream;

import com.vaadin.server.RequestHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinSession;

public class ReportViewerHandler implements RequestHandler {
	private static final long serialVersionUID = 1L;

	private ResourceManager resourceManager;

	public ReportViewerHandler(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	@Override
	public boolean handleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) throws IOException {
    if (("/" + ReportViewer.PREVIEW_URL_PATTERN).equals(request.getPathInfo())) {
    	String id = request.getParameter(ReportViewer.ID_PARAMETER);
  		if (id == null) {
  			return true;
  		}
  		IResourceProvider resourceProvider = resourceManager.get(id);
  		if (resourceProvider == null) {
  			return true;
  		}
  		String name = request.getParameter(ReportViewer.NAME_PARAMETER);
  		response.setContentType(resourceProvider.getMimeType(name));
  		byte[] data = resourceProvider.getBytes(name);
  		if (data != null) {
  			OutputStream ouputStream = response.getOutputStream();
  			ouputStream.write(data, 0, data.length);
  			ouputStream.flush();
  			ouputStream.close();
  		}
  		return true;
    }
		else {
			return false;
		}
	}

}
