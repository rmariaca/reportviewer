package org.vaadin.addon.reportviewer.client;

import org.vaadin.addon.reportviewer.ReportViewer;

import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.client.ui.layout.ElementResizeEvent;
import com.vaadin.client.ui.layout.ElementResizeListener;
import com.vaadin.shared.ui.Connect;

@Connect(ReportViewer.class)
public class ReportViewerConnector extends AbstractComponentConnector {
	private static final long serialVersionUID = 1L;

	@Override
	protected void init() {
		super.init();
		getWidget().setConnection(getConnection());
    getLayoutManager().addElementResizeListener(getWidget().getElement(),
        new ElementResizeListener() {
            @Override
            public void onElementResize(ElementResizeEvent event) {
            	getWidget().resize();
            }
        }
    );
	}

	@Override
	public ReportViewerWidget getWidget() {
		return (ReportViewerWidget) super.getWidget();
	}

	@Override
	public ReportViewerState getState() {
		return (ReportViewerState) super.getState();
	}

	protected void linkClicked(String parameters) {
		getRpcProxy(ReportViewerServerRpc.class).linkClicked(parameters);
	}

	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);

		if (stateChangeEvent.hasPropertyChanged("report")) {
			getWidget().setReport(getState().getReport());
		}
	}
}
