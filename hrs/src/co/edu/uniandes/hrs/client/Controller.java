package co.edu.uniandes.hrs.client;


import java.util.List;

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
	private UserView userView;
	private RSConstants constants = GWT.create(RSConstants.class);

	//private ArrayList<String> stocks = new ArrayList<String>();
	private HRSServiceAsync hrsSvc = GWT.create(HRSService.class);

	@Override
	public void onModuleLoad() {
		this.userView=new UserView();
		this.userView.setController(this);
		this.userView.generateUI();
		this.CFView=new CFView();
		this.CFView.setController(this);
		this.CFView.generateUI();
	}
	
	@Override
	public void onClick(ClickEvent event) {
		String sender;
		if(event.getSource() instanceof Button) {
			sender = ((Button) event.getSource()).getText();
			
			if(sender.equals(this.constants.uiSend())) {
				if(this.userView.validate()) {
					this.sendUser();
				}
			}
		}
	}
	
	private void sendUser() {
		if(hrsSvc==null) hrsSvc = GWT.create(HRSService.class);
		String user=this.userView.getTextBoxUsername().getText() + "|"
				+ this.userView.getTextBoxName().getText() + "|"
				+ this.userView.getTextBoxTwitter().getText() + "|" 
				+ this.userView.getTextBoxEmail().getText();
		
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
		hrsSvc.sendUser(user, callback);
	}

	private void updateUsers(String[] result) {
		this.userView.getTextBoxEmail().setText(result[0]);
	}
}
