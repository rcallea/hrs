package co.edu.uniandes.hrs.client;


import java.util.List;

import co.edu.uniandes.hrs.shared.CFParameters;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

public class Controller implements ClickHandler, EntryPoint {
	
	private CFView CFView;
	private RSConstants constants = GWT.create(RSConstants.class);

	//private ArrayList<String> stocks = new ArrayList<String>();
	private HRSServiceAsync hrsSvc = GWT.create(HRSService.class);

	@Override
	public void onModuleLoad() {
		this.CFView=new CFView();
		this.CFView.setController(this);
		this.CFView.generateUI();
	}
	
	@Override
	public void onClick(ClickEvent event) {
		String sender;
		if(event.getSource() instanceof Button) {
			sender = ((Button) event.getSource()).getText();
			
			if(sender.equals(this.constants.cfSend())) {
				if(this.CFView.validate()) {
					this.sendUser();
				}
			}
		}
	}
	
	private void sendUser() {
		if(hrsSvc==null) hrsSvc = GWT.create(HRSService.class);
		CFParameters data=new CFParameters();
		
		try {
			data=new CFParameters(this.CFView.getListboxDatasetSize(),
					this.CFView.getTextboxNeighbors().getText(),
					this.CFView.getListBoxMeasureType(),
					this.CFView.getListBoxRecommenderType(),
					Integer.parseInt(this.CFView.getTextboxUser().getText()));
		} catch (NumberFormatException nfe) {}
		
		AsyncCallback<String[]> callback = new AsyncCallback<String[]>() {
			public void onFailure(Throwable caught) {
				//Aquí poner el llamado en caso de que salga mal
			}

			public void onSuccess(String[] result) {
				//Aquí poner el llamado en caso de que salga bien
				updateUsers(result);
			}
		};

		// Make the call to the stock price service.
		hrsSvc.sendUser(data, callback);
	}

	private void updateUsers(String[] result) {
		this.CFView.getHtmlPrecisionResult().setText(result[0]);
		this.CFView.getHtmlRecallResult().setText(result[1]);
		String text="";
		for(int i=2;i<result.length;i++) {
			text+=result[i] + " ";
		}
		this.CFView.getHtmlResultListResult().setText(text);
		
	}
}
