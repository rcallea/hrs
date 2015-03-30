package co.edu.uniandes.hrs.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CFView {

	/**
	 * Constants used in web page
	 */
	private RSConstants constants = GWT.create(RSConstants.class);

	/**
	 * Form elements: Label, textbox, dropdown, button
	 */
	//private FlexTable ft=new FlexTable();
	//private ListBox listBoxDispositivo=new ListBox();
	private VerticalPanel vp=new VerticalPanel();
	private HTML htmlUiTitle=new HTML("<h2>" + constants.cfTitle() + "</h2>");
	private HTML htmlDatasetSize=new HTML(constants.cfDatasetSize());
	private HTML htmlNeighbors=new HTML(constants.cfNeighbors());
	private HTML htmlMeasureType=new HTML(constants.cfMeasureType());
	private HTML htmlRecommenderType=new HTML(constants.cfRecommenderType());
	private HTML htmlError=new HTML();
	private TextBox textBoxUsername=new TextBox();
	private TextBox textBoxName=new TextBox();
	private TextBox textBoxTwitter=new TextBox();
	private TextBox textBoxEmail=new TextBox();
	private Button buttonSend = new Button(constants.uiSend());
	private Controller controller;
	
	/**
	 * @param controller the controller to set
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Adds the elements to the root page
	 */
	public void generateUI() {
		this.textBoxUsername.setText("3500");
		this.textBoxName.setText("Nombre");
		this.textBoxTwitter.setText("@twitter");
		this.textBoxEmail.setText("example@example.com");
		
		this.vp.add(this.htmlUiTitle);
		this.vp.add(this.htmlDatasetSize);
		this.vp.add(this.textBoxUsername);
		this.vp.add(this.htmlNeighbors);
		this.vp.add(this.textBoxName);
		this.vp.add(this.htmlMeasureType);
		this.vp.add(this.textBoxTwitter);
		this.vp.add(this.htmlRecommenderType);
		this.vp.add(this.textBoxEmail);
		this.vp.add(new HTML("<hr/>"));
		this.vp.add(this.buttonSend);
		this.vp.add(new HTML("<hr/>"));
		this.vp.add(this.htmlError);
		this.buttonSend.addClickHandler(this.controller);
		RootPanel.get("cf").add(this.vp);
		
		this.hidErrorMessage();
	}
	
	/**
	 * Shows an error message on htmlError 
	 * @param message
	 */
	public void showErrorMessage(String message) {
		this.htmlError.setHTML(message);
		this.htmlError.setStyleName("alert alert-danger");
	}
	
	public void hidErrorMessage() {
		this.htmlError.setHTML("");
		this.htmlError.setStyleName("none");
	}

	public boolean validate() {
		boolean retorno=true;
//		String message="<ul>";
//		if(!this.getTextBoxUsername().getText().matches("[0-9]+")) {
//			retorno=false;
//			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.uiUserName() + "\": " + this.constants.uiFieldErrorMessage() + "</li>";
//		}
//
//		if(this.getTextBoxName().getText().trim().equals("")) {
//			retorno=false;
//			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.uiName() + "\": " + this.constants.uiFieldErrorMessage() + "</li>";
//		}
//		
//		if(this.getTextBoxEmail().getText().trim().equals("")) {
//			retorno=false;
//			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.uiEmail() + "\": " + this.constants.uiFieldErrorMessage() + "</li>";
//		}
//		
//		if(this.getTextBoxTwitter().getText().trim().equals("")) {
//			retorno=false;
//			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.uiTwitter() + "\": " + this.constants.uiFieldErrorMessage() + "</li>";
//		}
//		
//		if(retorno==false) {
//			this.showErrorMessage(message + "</ul>");
//		}
//		else {
//			this.hidErrorMessage();
//		}
		return retorno;
	}
}
