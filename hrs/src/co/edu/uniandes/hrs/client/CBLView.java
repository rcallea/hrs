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

public class CBLView {

	/**
	 * Constants used in web page
	 */
	private RSConstants constants = GWT.create(RSConstants.class);

	/**
	 * Form elements: Label, textbox, dropdown, button
	 */
	private VerticalPanel vp=new VerticalPanel();
	private HTML htmlUiTitle=new HTML(constants.cblTitle());
	private HTML htmlMinTermFrequency=new HTML(constants.cblMinTermFrequency());
	private HTML htmlMinDocFrequency=new HTML(constants.cblMinDocFrequency());
	private HTML htmlMinWordLen=new HTML(constants.cblMinWordLen());
	private HTML htmlUser=new HTML(constants.cfUser());
	private HTML htmlPrecision=new HTML(constants.cblPrecision());
	private HTML htmlRecall=new HTML(constants.cblRecall());
	private HTML htmlResultList=new HTML(constants.cblResultList());
	private HTML htmlError=new HTML();
	private ListBox listboxMinTermFrequency=new ListBox();
	private ListBox listboxMinDocFrequency=new ListBox();
	private ListBox listBoxMinWordLen=new ListBox();
	private TextBox textboxUser=new TextBox();
	private HTML htmlPrecisionResult=new HTML("");
	private HTML htmlRecallResult=new HTML("");
	private HTML htmlResultListResult=new HTML("");
	private Button buttonSend = new Button(constants.cblSend());
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
		int row=0;
		int column=0;
		FlexTable ft=new FlexTable();
		
		this.setListboxMinTermFrequency(constants.cblMinTermFrequencyValues());
		this.setListboxMinDocFrequency(constants.cblMinDocFrequencyValues());;
		this.setListBoxMinWordLen(constants.cblMinWordLenValues());
		this.textboxUser.setText("2");;
		
		this.vp.add(this.htmlUiTitle);
		ft.setWidget(row++,column, this.htmlMinTermFrequency);
		ft.setWidget(row++,column, this.htmlMinDocFrequency);
		ft.setWidget(row++,column, this.htmlMinWordLen);
		ft.setWidget(row++,column, this.htmlUser);
		ft.setWidget(row++,column, this.htmlPrecision);
		ft.setWidget(row++,column, this.htmlRecall);
		ft.setWidget(row++,column, this.htmlResultList);
		
		column++;
		row=0;
		ft.setWidget(row++, column, this.listboxMinTermFrequency);
		ft.setWidget(row++, column, this.listboxMinDocFrequency);
		ft.setWidget(row++, column, this.listBoxMinWordLen);
		ft.setWidget(row++, column, this.textboxUser);
		ft.setWidget(row++, column, this.htmlPrecisionResult);
		ft.setWidget(row++, column, this.htmlRecallResult);
		ft.setWidget(row++, column, this.htmlResultListResult);
		ft.setStyleName("table table-striped");
		this.vp.add(ft);

		this.vp.add(this.htmlError);
		this.vp.add(this.buttonSend);
		this.buttonSend.addClickHandler(this.controller);
		RootPanel.get("contentL").add(this.vp);
		
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
		if(!this.textboxUser.getText().matches("[0-9]+")) {
			retorno=false;
			message += "<li>" + this.constants.uiFieldError() + "\" " + this.constants.cfUser() + "\": " + this.constants.uiFieldErrorMessage() + " [" + constants.cfNeighborsMinValue() + "," + constants.cfNeighborsMaxValue() + "]" + "</li>";
		}
		
		
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
	 * @return the htmlMinTermFrequency
	 */
	public HTML getHtmlMinTermFrequency() {
		return htmlMinTermFrequency;
	}

	/**
	 * @param htmlMinTermFrequency the htmlMinTermFrequency to set
	 */
	public void setHtmlMinTermFrequency(HTML htmlMinTermFrequency) {
		this.htmlMinTermFrequency = htmlMinTermFrequency;
	}

	/**
	 * @return the htmlMinDocFrequency
	 */
	public HTML getHtmlMinDocFrequency() {
		return htmlMinDocFrequency;
	}

	/**
	 * @param htmlMinDocFrequency the htmlMinDocFrequency to set
	 */
	public void setHtmlMinDocFrequency(HTML htmlMinDocFrequency) {
		this.htmlMinDocFrequency = htmlMinDocFrequency;
	}

	/**
	 * @return the htmlMinWordLen
	 */
	public HTML getHtmlMinWordLen() {
		return htmlMinWordLen;
	}

	/**
	 * @param htmlMinWordLen the htmlMinWordLen to set
	 */
	public void setHtmlMinWordLen(HTML htmlMinWordLen) {
		this.htmlMinWordLen = htmlMinWordLen;
	}

	/**
	 * @return the htmlUser
	 */
	public HTML getHtmlUser() {
		return htmlUser;
	}

	/**
	 * @param htmlUser the htmlUser to set
	 */
	public void setHtmlUser(HTML htmlUser) {
		this.htmlUser = htmlUser;
	}

	/**
	 * @return the htmlPrecision
	 */
	public HTML getHtmlPrecision() {
		return htmlPrecision;
	}

	/**
	 * @param htmlPrecision the htmlPrecision to set
	 */
	public void setHtmlPrecision(HTML htmlPrecision) {
		this.htmlPrecision = htmlPrecision;
	}

	/**
	 * @return the htmlRecall
	 */
	public HTML getHtmlRecall() {
		return htmlRecall;
	}

	/**
	 * @param htmlRecall the htmlRecall to set
	 */
	public void setHtmlRecall(HTML htmlRecall) {
		this.htmlRecall = htmlRecall;
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
	 * @return the listboxMinTermFrequency
	 */
	public int getListboxMinTermFrequency() {
		int ret=0;
		try {
			String value=this.listboxMinTermFrequency.getValue(this.listboxMinTermFrequency.getSelectedIndex());
			ret=Integer.parseInt(value);
		} catch (NumberFormatException nfe) {}
		return ret;
	}

	/**
	 * @param listboxMinTermFrequency the listboxMinTermFrequency to set
	 */
	public void setListboxMinTermFrequency(String[] minTermFrequencyValues) {
		for(int i=0;i<minTermFrequencyValues.length;i++) {
			String[] cols=minTermFrequencyValues[i].split(";");
			this.listboxMinTermFrequency.addItem(cols[1], cols[0]);
		}
	}

	/**
	 * @return the listboxMinDocFrequency
	 */
	public int getListboxMinDocFrequency() {
		int ret=0;
		try {
			String value=this.listboxMinDocFrequency.getValue(this.listboxMinDocFrequency.getSelectedIndex());
			ret=Integer.parseInt(value);
		} catch (NumberFormatException nfe) {}
		return ret;
	}

	/**
	 * @param listboxMinDocFrequency the listboxMinDocFrequency to set
	 */
	public void setListboxMinDocFrequency(String[] minDocFrequencyValues) {
		for(int i=0;i<minDocFrequencyValues.length;i++) {
			String[] cols=minDocFrequencyValues[i].split(";");
			this.listboxMinDocFrequency.addItem(cols[1], cols[0]);
		}
	}

	/**
	 * @return the listBoxMinWordLen
	 */
	public int getListBoxMinWordLen() {
		int ret=0;
		try {
			String value=this.listBoxMinWordLen.getValue(this.listBoxMinWordLen.getSelectedIndex());
			ret=Integer.parseInt(value);
		} catch (NumberFormatException nfe) {}
		return ret;
	}

	/**
	 * @param listBoxMinWordLen the listBoxMinWordLen to set
	 */
	public void setListBoxMinWordLen(String[] minWordLenValues) {
		for(int i=0;i<minWordLenValues.length;i++) {
			String[] cols=minWordLenValues[i].split(";");
			this.listBoxMinWordLen.addItem(cols[1], cols[0]);
		}
	}

	/**
	 * @return the textboxUser
	 */
	public int getTextboxUser() {
		int ret=0;
		try {
			ret=Integer.parseInt(this.textboxUser.getText());
		} catch (NumberFormatException nfe) {}
		return ret;
	}

	/**
	 * @param textboxUser the textboxUser to set
	 */
	public void setTextboxUser(TextBox textboxUser) {
		this.textboxUser = textboxUser;
	}

	/**
	 * @return the htmlPrecisionResult
	 */
	public HTML getHtmlPrecisionResult() {
		return htmlPrecisionResult;
	}

	/**
	 * @param htmlPrecisionResult the htmlPrecisionResult to set
	 */
	public void setHtmlPrecisionResult(HTML htmlPrecisionResult) {
		this.htmlPrecisionResult = htmlPrecisionResult;
	}

	/**
	 * @return the htmlRecallResult
	 */
	public HTML getHtmlRecallResult() {
		return htmlRecallResult;
	}

	/**
	 * @param htmlRecallResult the htmlRecallResult to set
	 */
	public void setHtmlRecallResult(HTML htmlRecallResult) {
		this.htmlRecallResult = htmlRecallResult;
	}

	/**
	 * @return the htmlResultListResult
	 */
	public HTML getHtmlResultListResult() {
		return htmlResultListResult;
	}

	/**
	 * @param htmlResultListResult the htmlResultListResult to set
	 */
	public void setHtmlResultListResult(HTML htmlResultListResult) {
		this.htmlResultListResult = htmlResultListResult;
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

}
