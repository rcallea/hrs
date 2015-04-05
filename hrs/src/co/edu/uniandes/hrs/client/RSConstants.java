package co.edu.uniandes.hrs.client;

import com.google.gwt.i18n.client.Constants;

public interface RSConstants extends Constants{
	
	/**
	 * á \u00E1		é \u00E9		í \u00ED		ó \u00F3		ú \u00FA		Ñ \u00F1		¿ \u00BF
	 * @return
	 */
	
	@DefaultStringValue("<h3>Recomendador Basado en contenido con More Like This de Lucene</h3>")
	String cblTitle();

	@DefaultStringValue("Tama\u00F1o del dataset de pruebas")
	String cblDatasetSize();

	@DefaultStringValue("Tiempo de espera m\u00E1ximo en minutos")
	String cblWaitTime();

	@DefaultStringValue("M\u00EDnimo de frecuencia de t\u00E9rminos")
	String cblMinTermFrequency();

	@DefaultStringValue("M\u00EDnimo de frecuencia de documentos")
	String cblMinDocFrequency();

	@DefaultStringValue("Tama\u00F1o m\u00EDnimo de las palabras")
	String cblMinWordLen();

	@DefaultStringValue("Usuario")
	String cblUser();

	@DefaultStringValue("Precision")
	String cblPrecision();

	@DefaultStringValue("Recall")
	String cblRecall();

	@DefaultStringValue("Lista de items recomendados")
	String cblResultList();

	@DefaultStringValue("Calcular CBL")
	String cblSend();

	@DefaultStringArrayValue({"0;Cualquiera","1;1","2;2","3;3","4;4","5;5","6;6","7;7","8;8","9;9","10;10","15;15","20;20","25;25","30;30","35;35","40;40","45;45","50;50","55;55","60;60","65;65","70;70","75;75","80;80","85;85","90;90","95;95","100;100"})
	String[] cblMinTermFrequencyValues();

	@DefaultStringArrayValue({"0;Cualquiera","1;1","2;2","3;3","4;4","5;5","6;6","7;7","8;8","9;9","10;10","15;15","20;20","25;25","30;30","35;35","40;40","45;45","50;50","55;55","60;60","65;65","70;70","75;75","80;80","85;85","90;90","95;95","100;100"})
	String[] cblMinDocFrequencyValues();

	@DefaultStringArrayValue({"0;Cualquiera","1;1","2;2","3;3","4;4","5;5","6;6","7;7","8;8","9;9","10;10","11;11","12;12","13;13","14;14","15;15","16;16","17;17","18;18","19;19","20;20"})
	String[] cblMinWordLenValues();

	//Generales
	@DefaultStringValue("Ingrese una ciudad: ")
	String labelCity();
	
	@DefaultStringValue("Ingrese una categoria: ")
	String labelCategory();
	
	
	//View Filtrado colaborativo
	@DefaultStringValue("<h3>Recomendador por filtraje colaborativo</h3>")
	String cfTitle();

	@DefaultStringValue("Tama\u00F1o del dataset de pruebas")
	String cfDatasetSize();

	@DefaultStringValue("N\u00FAmero de vecinos")
	String cfNeighbors();

	@DefaultStringValue("N\u00FAmero de calificaciones a utilizar")
	String cfGradeNumber();

	@DefaultStringValue("Tipo de medida")
	String cfMeasureType();

	@DefaultStringValue("Tipo de recomendador")
	String cfRecommenderType();

	@DefaultStringValue("Usuario")
	String cfUser();

	@DefaultStringValue("Precision")
	String cfPrecision();

	@DefaultStringValue("Recall")
	String cfRecall();

	@DefaultStringValue("Lista de items recomendados")
	String cfResultList();

	@DefaultStringValue("Calcular CF")
	String cfSend();

	@DefaultStringArrayValue({"60 %", "70 %", "80 %"})
	String[] cfDatasetSizeValues();

	@DefaultIntValue(1)
	int cfNeighborsMinValue();

	@DefaultIntValue(250)
	int cfNeighborsMaxValue();

	@DefaultStringArrayValue({"false;Correlaci\u00F3n de Pearson", "true;Distancia coseno"})
	String[] cfMeasureTypeValues();

	@DefaultStringArrayValue({"false;Usuario-Usuario", "true;Item-Item"})
	String[] cfRecommenderTypeValues();

	@DefaultStringValue("Error en el campo")
	String uiFieldError();

	@DefaultStringValue("El campo se encuentra vac\u00EDo o tiene caracteres inv\u00E1lidos")
	String uiFieldErrorMessage();

	@DefaultStringValue("El campo se encuentra fuera del rango de valores v\u00E1lidos")
	String uiFieldOutOfBoundsMessage();
	
	// View Recomendador basado en contenido
	@DefaultStringValue("Recomendador basado en Contenido")
	String contTitle();

	@DefaultStringValue("Ingrese una descripci\u00F3n del sitio que busca y lo que espera encontrar en este: ")
	String labelDescriptionBusiness();
	
	@DefaultStringValue("Recomendar")
	String contentSend();
	
	// View Recomendador hibrido
	@DefaultStringValue("Recomendar Hibrido")
	String hybridSend();
}

