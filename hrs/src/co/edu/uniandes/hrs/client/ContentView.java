package co.edu.uniandes.hrs.client;

import java.util.Iterator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContentView {

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
	private HTML htmlUiTitle=new HTML("<h2>" + constants.contTitle() + "</h2>");
	private HTML htmlUiSubTitle=new HTML("<br/><h3> Nuestras recomendaciones </h3>");
	private HTML htmlLabelCity=new HTML(constants.labelCity());
	private HTML htmlLabelCategory=new HTML(constants.labelCategory());
	private HTML htmlLabelDescription=new HTML(constants.labelDescriptionBusiness());
	private HTML htmlResultList=new HTML(constants.cfResultList());
	private HTML htmlError=new HTML();
	private TextBox textboxCity=new TextBox();
	private TextBox textboxCategory=new TextBox();
	private TextArea textboxDescription=new TextArea();
	private Button buttonSend = new Button(constants.contentSend());
	private Controller controller;
	private FlexTable tableResultsBusiness =new FlexTable();
	

	HorizontalPanel hp0=new HorizontalPanel();
	HorizontalPanel hp1=new HorizontalPanel();
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
		int row=0;
		int column=0;
		FlexTable ft=new FlexTable();
		this.textboxDescription.setWidth("100%");
		this.textboxDescription.setHeight("200px");
		//this.tableResultsBusiness.setStyleName("table table-striped");
		
		hp0.add(new HTML("<div style= 'width:150px'>" + this.htmlLabelCity + "</div>"));
		hp0.add(this.textboxCity);
		
		hp1.add(new HTML("<div style= 'width:150px'>" + this.htmlLabelCategory + "</div>"));
		hp1.add(this.textboxCategory);
		
		this.textboxCity.setText("Phoenix");
		this.textboxCategory.setText("health");
		this.textboxDescription.setText("I need a clinical with a expert doctor in neurology");
		
		this.vp.add(this.htmlUiTitle);
		ft.setWidget(row++,column, hp0);
		ft.setWidget(row++,column, hp1);
		ft.setWidget(row++,column, this.htmlLabelDescription);
		ft.setWidget(row++, column, this.textboxDescription);
		ft.setStyleName("table table-striped");
		this.vp.add(ft);

		this.vp.add(this.htmlError);
		this.vp.add(this.buttonSend);
		this.vp.add(this.htmlUiSubTitle);
		this.vp.add(this.tableResultsBusiness);
		this.buttonSend.addClickHandler(this.controller);
		
		RootPanel.get("content").add(this.vp);
		
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
		String message="<ul>";

		if(retorno==false) {
			this.showErrorMessage(message + "</ul>");
		}
		else {
			this.hidErrorMessage();
		}
		return retorno;
	}
	

	/**
	 * @return the htmlUiTitle
	 */
	public HTML getHtmlUiTitle() {
		return htmlUiTitle;
	}

	/**
	 * @param htmlUiTitle the htmlUiTitle to set
	 */
	public void setHtmlUiTitle(HTML htmlUiTitle) {
		this.htmlUiTitle = htmlUiTitle;
	}

	/**
	 * @return the htmlLabelCity
	 */
	public HTML getHtmlLabelCity() {
		return htmlLabelCity;
	}

	/**
	 * @param htmlLabelCity the htmlLabelCity to set
	 */
	public void setHtmlLabelCity(HTML htmlLabelCity) {
		this.htmlLabelCity = htmlLabelCity;
	}

	public HTML getHtmlLabelCategory() {
		return htmlLabelCategory;
	}

	public void setHtmlLabelCategory(HTML htmlLabelCategory) {
		this.htmlLabelCategory = htmlLabelCategory;
	}
	
	public HTML getHtmlLabelDescription() {
		return htmlLabelDescription;
	}

	public void setHtmlLabelDescription(HTML htmlLabelDescription) {
		this.htmlLabelDescription = htmlLabelDescription;
	}
	
	/**
	 * @return the htmlResultList
	 */
	public HTML getHtmlResultList() {
		return htmlResultList;
	}

	/**
	 * @param htmlResultList the htmlResultList to set
	 */
	public void setHtmlResultList(HTML htmlResultList) {
		this.htmlResultList = htmlResultList;
	}

	/**
	 * @return the htmlError
	 */
	public HTML getHtmlError() {
		return htmlError;
	}

	/**
	 * @param htmlError the htmlError to set
	 */
	public void setHtmlError(HTML htmlError) {
		this.htmlError = htmlError;
	}

	/**
	 * @return the buttonSend
	 */
	public Button getButtonSend() {
		return buttonSend;
	}

	/**
	 * @param buttonSend the buttonSend to set
	 */
	public void setButtonSend(Button buttonSend) {
		this.buttonSend = buttonSend;
	}

	public TextBox getTextboxCity() {
		return textboxCity;
	}

	public void setTextboxCity(TextBox textboxCity) {
		this.textboxCity = textboxCity;
	}

	public TextBox getTextboxCategory() {
		return textboxCategory;
	}

	public void setTextboxCategory(TextBox textboxCategory) {
		this.textboxCategory = textboxCategory;
	}

	public TextArea getTextboxDescription() {
		return textboxDescription;
	}

	public void setTextboxDescription(TextArea textboxDescription) {
		this.textboxDescription = textboxDescription;
	}
	
	public FlexTable getTableResultsBusiness() {
		return tableResultsBusiness;
	}

	public void setTableResultsBusiness(FlexTable tableResultsBusiness) {
		this.tableResultsBusiness = tableResultsBusiness;
	}
	public HTML getHtmlUiSubTitle() {
		return htmlUiSubTitle;
	}

	public void setHtmlUiSubTitle(HTML htmlUiSubTitle) {
		this.htmlUiSubTitle = htmlUiSubTitle;
	}
}
