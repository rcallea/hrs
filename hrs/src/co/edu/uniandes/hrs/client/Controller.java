package co.edu.uniandes.hrs.client;


//HEAD
import java.util.HashMap;
import java.util.List;



import co.edu.uniandes.hrs.shared.CBParametersL;
import co.edu.uniandes.hrs.shared.CBResultL;
import co.edu.uniandes.hrs.shared.CFParameters;
import co.edu.uniandes.hrs.shared.ContentParameters;
import co.edu.uniandes.hrs.shared.CFResult;
import co.edu.uniandes.hrs.shared.ContentResult;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTML;
// branch 'master' of https://github.com/rcallea/hrs.git

public class Controller implements ClickHandler, EntryPoint {
	
	private CFView CFView;
	private CBLView cblView;
	private ContentView ContentView;
	private HybridView HybridView;
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

		this.HybridView=new HybridView();
		this.HybridView.setController(this);
		this.HybridView.generateUI();
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
			}else if(sender.equals(this.constants.contentSend())) {
				if(this.ContentView.validate()) {
					ContentView.getTableResultsBusiness().clear();
					this.LoadRecommendationContent();
				}
			}
			else if(sender.equals(this.constants.hybridSend())) {
				if(this.HybridView.validate()) {
					HybridView.getTableResultsBusiness().clear();
					this.LoadRecommendationHybrid();
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
				//Aqu� poner el llamado en caso de que salga mal
			}

			@Override
			public void onSuccess(CFResult result) {
				//Aqu� poner el llamado en caso de que salga bien
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
		ContentParameters data=new ContentParameters();
		
		String nameCity = this.ContentView.getTextboxCity().getText(); 
		String category = this.ContentView.getTextboxCategory().getText();  
		String description = this.ContentView.getTextboxDescription().getText();
		data=new ContentParameters(nameCity, category, description);
		
		AsyncCallback<List<ContentResult>> callback = new AsyncCallback<List<ContentResult>>() {
			public void onFailure(Throwable caught) {
		        // TODO: Do something with errors.
			}

			public void onSuccess(List<ContentResult> result) {
				int i = 0;
				for (final ContentResult contentResult : result) {
					Hyperlink link = new Hyperlink();  
					link.setText(contentResult.getName());
					link.addClickListener(new ClickListener() {
						public void onClick(Widget sender) {
					        PopupDeatils popup = new PopupDeatils(contentResult);
							popup.setStyleName("demo-popup");
						    popup.show();
					      }
						
					});
					
					ContentView.getTableResultsBusiness().setWidget(i, 0, link);
					i++;
				}
			}
		};
		hrsSvc.getContentBusiness(data, null, callback);

	}
	
	private void LoadRecommendationHybrid() {
		ContentParameters contentData=new ContentParameters();
		
		String userId = this.HybridView.getTextboxUser().getText();
		String nameCity = this.HybridView.getTextboxCity().getText(); 
		String category = this.HybridView.getTextboxCategory().getText();  
		String description = this.HybridView.getTextboxDescription().getText();
		contentData=new ContentParameters(nameCity, category, description);
		
		///**
		
		CFParameters cfData=new CFParameters();
		cfData=new CFParameters(this.CFView.getListboxDatasetSize(),
		this.CFView.getTextboxNeighbors().getText(),
		this.CFView.getTextboxGradeNumber().getText(),
		this.CFView.getListBoxMeasureType(),
		this.CFView.getListBoxRecommenderType(),
		userId);
			
		///****
		AsyncCallback<List<ContentResult>> callback = new AsyncCallback<List<ContentResult>>() {
			public void onFailure(Throwable caught) {
		        // TODO: Do something with errors.
			}

			public void onSuccess(List<ContentResult> result) {
				int i = 0;
				for (final ContentResult contentResult : result) {
					Hyperlink link = new Hyperlink();  
					link.setText(contentResult.getName());
					link.addClickListener(new ClickListener() {
						public void onClick(Widget sender) {
					        PopupDeatils popup = new PopupDeatils(contentResult);
							popup.setStyleName("demo-popup");
						    popup.show();
					      }
						
					});
					
					HybridView.getTableResultsBusiness().setWidget(i, 0, link);
					i++;
				}
			}
		};
		hrsSvc.getHybridBusiness(cfData,contentData, callback);

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
				//Aqu� poner el llamado en caso de que salga mal
			}

			@Override
			public void onSuccess(CBResultL result) {
				//Aqu� poner el llamado en caso de que salga bien
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
