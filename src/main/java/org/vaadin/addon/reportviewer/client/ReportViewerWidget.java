package org.vaadin.addon.reportviewer.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ConnectorMap;

public class ReportViewerWidget extends HorizontalPanel {
	private static final int FRAME_OFFSET_WIDTH = 5;
	private static final int FRAME_OFFSET_HEIGHT = 5;

	private HTMLPanel reportPanel;
	private Frame frame;
	private ApplicationConnection connection;

	public ReportViewerWidget() {
		Window.addResizeHandler(new FrameResizeHandler());
		getElement().getStyle().setBorderWidth(0, Unit.PX);
		getElement().getStyle().setOverflow(Overflow.HIDDEN);

  	frame = new Frame();
  	frame.getElement().getStyle().setBorderWidth(0, Unit.PX);

  	reportPanel = new HTMLPanel("");
  	reportPanel.getElement().getStyle().setBorderWidth(0, Unit.PX);
  	reportPanel.getElement().getStyle().setOverflow(Overflow.HIDDEN);
		reportPanel.add(frame);

		frame.addLoadHandler(new FrameLoadHandler());

		add(reportPanel);
	}

	protected void setConnection(ApplicationConnection connection) {
		this.connection = connection;
	}

	protected void setReport(String url) {
		frame.setUrl(url);
	}

	protected void resize() {
		frame.setWidth((Window.getClientWidth() - getAbsoluteLeft() - FRAME_OFFSET_WIDTH) + "px");
		frame.setHeight((Window.getClientHeight() - getAbsoluteTop() - FRAME_OFFSET_HEIGHT) + "px");
	}

  private class FrameLoadHandler implements LoadHandler {

		@Override
		public void onLoad(LoadEvent event) {
			IFrameElement iframe = IFrameElement.as(event.getRelativeElement());
			NodeList<Element> anchors = iframe.getContentDocument().getElementsByTagName("a");
	    for ( int i = 0 ; i < anchors.getLength() ; i++ ) {
	        Element a = anchors.getItem(i);
	        final String href = a.getAttribute("href");
	        if (href == null || href.trim().equals("")) {
	        	continue;
	        }
	        Anchor link = new Anchor(a.getInnerHTML(), true);
	        link.addClickHandler(new LinkClickHandler(href));
	        reportPanel.addAndReplaceElement(link, a);
	    }

			anchors = iframe.getContentDocument().getElementsByTagName("area");
	    for ( int i = 0 ; i < anchors.getLength() ; i++ ) {
	        Element a = anchors.getItem(i);
	        final String href = a.getAttribute("href");
	        if (href == null || href.trim().equals("")) {
	        	continue;
	        }
	        String shape = a.getAttribute("shape");
	        String coords = a.getAttribute("coords");
	        AreaWidget link = new AreaWidget();
	        link.setShape(shape);
	        link.setCoords(coords);
	        link.addClickHandler(new LinkClickHandler(href));
	        reportPanel.addAndReplaceElement(link, a);
	    }
		}
  }

  private class AreaWidget extends FocusWidget {

  	private AreaWidget() {
  		setElement(DOM.createElement("area"));
  		DOM.setElementAttribute(getElement(), "href", "#");
  	}

  	private void setCoords(String coords) {
  		DOM.setElementAttribute(getElement(), "coords", (coords == null) ? "" : coords);
  	}

  	private void setShape(String shape) {
  		DOM.setElementAttribute(getElement(), "shape", (shape == null) ? "" : shape);
  	}

  }

	private class LinkClickHandler implements ClickHandler {
  	private String parameters;

		private LinkClickHandler(String parameters) {
			this.parameters = parameters;
  	}

		@Override
		public void onClick(ClickEvent event) {
			ReportViewerConnector connector = (ReportViewerConnector) ConnectorMap.get(connection).getConnector(ReportViewerWidget.this);
			connector.linkClicked(URL.decodeQueryString(parameters));
		}
  }

	private class FrameResizeHandler implements ResizeHandler {

		@Override
		public void onResize(ResizeEvent event) {
			resize();
		}
	}
}