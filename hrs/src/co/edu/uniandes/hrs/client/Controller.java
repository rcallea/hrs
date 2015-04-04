package co.edu.uniandes.hrs.client;


import co.edu.uniandes.hrs.shared.CBParametersL;
import co.edu.uniandes.hrs.shared.CBResultL;
import co.edu.uniandes.hrs.shared.CFParameters;
import co.edu.uniandes.hrs.shared.CFResult;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;

public class Controller implements ClickHandler, EntryPoint {
	
	private CFView CFView;
	private CBLView cblView;
	private ContentView ContentView;
	private RSConstants constants = GWT.create(RSConstants.class);

	//private ArrayList<String> stocks = new ArrayList<String>();
	private HRSServiceAsync hrsSvc = GWT.create(HRSService.class);

	@Override
	public void onModuleLoad() {
		this.CFView=new CFView();
		this.CFView.setController(this);
		this.CFView.generateUI();
		
		this.cblView=new CBLView();
		this.cblView.setController(this);
		this.cblView.generateUI();

		this.ContentView=new ContentView();
		this.ContentView.setController(this);
		this.ContentView.generateUI();
	}
	
	@Override
	public void onClick(ClickEvent event) {
		String sender;
		if(event.getSource() instanceof Button) {
			sender = ((Button) event.getSource()).getText();
			
			if(sender.equals(this.constants.cfSend())) {
				if(this.CFView.validate()) {
					this.sendCFData();
				}
			}
			else if(sender.equals(this.constants.cblSend())) {
				if(this.cblView.validate()) {
					this.sendCBLData();
				}
			}else if(sender.equals(this.constants.cfSend())) {
				if(this.ContentView.validate()) {
					this.LoadRecommendationContent();
				}
			}
		}		
		
	}
	
	private void sendCFData() {
		if(hrsSvc==null) hrsSvc = GWT.create(HRSService.class);
		CFParameters data=new CFParameters();
		
		try {
			data=new CFParameters(this.CFView.getListboxDatasetSize(),
					this.CFView.getTextboxNeighbors().getText(),
					this.CFView.getTextboxGradeNumber().getText(),
					this.CFView.getListBoxMeasureType(),
					this.CFView.getListBoxRecommenderType(),
					this.CFView.getTextboxUser().getText());
			this.CFView.getHtmlPrecisionResult().setHTML("<strong>Calculando...</strong>");
			this.CFView.getHtmlRecallResult().setHTML("<strong>Calculando...</strong>");
			this.CFView.getHtmlResultListResult().setHTML("<strong>Sin resultados</strong>");
	} catch (NumberFormatException nfe) {}
		
		AsyncCallback<CFResult> callback = new AsyncCallback<CFResult>() {
			public void onFailure(Throwable caught) {
				//Aquí poner el llamado en caso de que salga mal
			}

			@Override
			public void onSuccess(CFResult result) {
				//Aquí poner el llamado en caso de que salga bien
				updateCFResult(result);
			}
		};

		// Make the call to the stock price service.
		hrsSvc.initCF(data, callback);
	}

	private void updateCFResult(CFResult result) {
		this.CFView.getHtmlPrecisionResult().setText("" + result.getPrecision());
		this.CFView.getHtmlRecallResult().setText("" + result.getRecall());
		String text="Mostrando " + result.getDataInfo().length + " recomendaciones de " + result.getData().length + " resultados.<br/>";
		String[] resultData=result.getDataInfo();
		for(int i=2;i<resultData.length;i++) {
			text+=resultData[i] + "<br/>";
		}
		this.CFView.getHtmlResultListResult().setHTML(text);
	}

	private void LoadRecommendationContent() {
		String nameCity = this.ContentView.getTextboxCity().getText(); 
		String category = this.ContentView.getTextboxCategory().getText();  
		String description = this.ContentView.getTextboxDescription().getText();
		
		/*AsyncCallback<List<String>> callbackJaccard = new AsyncCallback<List<String>>() {
			public void onFailure(Throwable caught) {
		        // TODO: Do something with errors.
			}

			public void onSuccess(List<String> result) {
				int i = 0;
				for (String stringsRecommender : result) {
					//userUserView.getJaccard().setText(i, 0, stringsRecommender);
					
					i++;
				}
			}
		};
		userUserSvc.getUserUserRecommended(userId, Integer.parseInt(numVecinos), Double.parseDouble(similarity), IndexType.JACCARD, callbackJaccard);*/

	}
	private void sendCBLData() {
		if(hrsSvc==null) hrsSvc = GWT.create(HRSService.class);
		CBParametersL data=new CBParametersL();
		
		try {
			data=new CBParametersL(this.cblView.getListboxDatasetSize(),
					this.cblView.getTextboxWaitTime(),
					this.cblView.getListboxMinTermFrequency(),
					this.cblView.getListboxMinDocFrequency(),
					this.cblView.getListBoxMinWordLen(),
					this.cblView.getTextboxUser());
			this.cblView.getHtmlPrecisionResult().setHTML("<strong>Calculando...</strong>");
			this.cblView.getHtmlRecallResult().setHTML("<strong>Calculando...</strong>");
			this.cblView.getHtmlResultListResult().setHTML("<strong>Sin resultados</strong>");
		} catch (NumberFormatException nfe) {}
		
		AsyncCallback<CBResultL> callback = new AsyncCallback<CBResultL>() {
			public void onFailure(Throwable caught) {
				//Aquí poner el llamado en caso de que salga mal
			}

			@Override
			public void onSuccess(CBResultL result) {
				//Aquí poner el llamado en caso de que salga bien
				updateCBLResult(result);
			}
		};

		// Make the call to the stock price service.
		hrsSvc.initCBL(data, callback);
	}

	private void updateCBLResult(CBResultL result) {
		this.cblView.getHtmlPrecisionResult().setText("" + result.getPrecision());
		this.cblView.getHtmlRecallResult().setText("" + result.getRecall());
		String text="";
		int tam=10;
		String[] resultData=result.getData();
		for(int i=2;i<resultData.length;i++) {
			text+=resultData[i] + " ";
		}
		
		String[] userText=result.getUserDocs();
		if(tam>userText.length) {
			tam=userText.length;
		}
		
		text="Mostrando " + tam + " reviews de " + userText.length + " para verificar<br/>";
		for(int i=0;i<tam;i++) {
			text=text+userText[i]+"<br/>";
		}
		
		tam=50;
		String[] resultText=result.getOtherDocs();
		if(tam>resultText.length) {
			tam=resultText.length;
		}
		
		text=text + "<hr/>Mostrando " + tam + " reviews de " + resultText.length + " recomendados<br/>";
		for(int i=0;i<tam;i++) {
			text=text+resultText[i]+"<br/>";
		}
		
		this.cblView.getHtmlResultListResult().setHTML(text);
	}
}
