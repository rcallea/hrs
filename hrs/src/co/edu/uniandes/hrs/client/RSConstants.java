package co.edu.uniandes.hrs.client;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.Constants.DefaultStringValue;

public interface RSConstants extends Constants{
	
	/**
	 * � \u00E1		� \u00E9		� \u00ED		� \u00F3		� \u00FA		� \u00F0		� \u00F1		� \u00BF
	 * @return
	 */
	
	@DefaultStringValue("Recomendador por filtraje colaborativo")
	String cfTitle();

	@DefaultStringValue("<h4>Tama\u00F1o del dataset de pruebas</h4>")
	String cfDatasetSize();

	@DefaultStringValue("<h4>N\u00FAmero de vecinos</h4>")
	String cfNeighbors();

	@DefaultStringValue("<h4>Tipo de medida</h4>")
	String cfMeasureType();

	@DefaultStringValue("<h4>Tipo de recomendador</h4>")
	String cfRecommenderType();

	@DefaultStringValue("N\u00FAmero de vecinos")
	String iiLevel();

	@DefaultStringValue("N\u00FAmero de resultados a mostrar")
	String iiResults();

	@DefaultStringValue("\u00CDndice de Jaccard")
	String iiJaccard();

	@DefaultStringValue("Distancia Coseno")
	String iiCoseno();

	@DefaultStringValue("Correlaci\u00F3n de Pearson")
	String iiPearson();

	@DefaultStringValue("Calcular \u00EDtem-\u00EDtem")
	String iiSend();

	@DefaultStringValue("El campo se encuentra vac\u00EDo, tiene mas de dos cifras decimales o tiene caracteres no numericos")
	String iiFieldErrorMessage();
	
	@DefaultStringValue("Id de usuario")
	String iiUsername();

	@DefaultStringValue("Registros fuera del dataset")
	String iiOutOfDataset();


	@DefaultStringValue("Guardar usuario")
	String uiSend();

	@DefaultStringValue("Informaci\u00F3n del usuario")
	String uiTitle();

	@DefaultStringValue("Id de usuario (N\u00FAmero mayor a 35000)")
	String uiUserName();

	@DefaultStringValue("Nombres  y apellidos")
	String uiName();

	@DefaultStringValue("Correo electr\u00F3nico")
	String uiEmail();

	@DefaultStringValue("Twitter")
	String uiTwitter();

	@DefaultStringValue("Error en el campo")
	String uiFieldError();

	@DefaultStringValue("El campo se encuentra vac\u00EDo o tiene caracteres inv\u00E1lidos")
	String uiFieldErrorMessage();

	@DefaultStringValue("El campo filtro es muy corto o se encuentra vac\u00EDo. Ingese por lo menos tres caracteres.")
	String uiFilterErrorMessage();

	@DefaultStringValue("Guardar calificaci\u00F3n")
	String miSend();

	@DefaultStringValue("Calificaci\u00F3n de una pel\u00EDcula")
	String miTitle();

	@DefaultStringValue("Id de usuario")
	String miUserName();

	@DefaultStringValue("Filtrar Pel\u00EDculas")
	String miFilterTitle();

	@DefaultStringValue("Aplicar filtro")
	String miFilterButton();

	@DefaultStringValue("Pel\u00EDcula a calificar")
	String miMovie();

	@DefaultStringValue("Calificaci\u00F3n")
	String miGrade();

	@DefaultStringArrayValue({"10", "9", "8", "7", "6", "5", "4", "3", "2", "1"})
	String[] miGradeList();

	@DefaultDoubleValue(180.0)
	double default02();
	//------Pagina Home-------//
	@DefaultStringValue("Informaci\u00F3n de interes")
	String uiTittleHome();
	
	@DefaultStringValue("Top 10 peliculas mejor calificadas")
	String uiSubTittleTop();
	
	@DefaultStringValue("Estadisticas datos")
	String uiSubTittleStatistics();

	@DefaultStringValue("Generar datos")
	String hiSend();

//------------------------//
	
	//------Pagina Recomendaciones User-User-------//
	@DefaultStringValue("Informaci\u00F3n Recomendaciones")
	String uiTittleUserUser();
	
	@DefaultStringValue("Tus votaciones")
	String uiSubTittleUserUser();
	
	@DefaultStringValue("Nuestras recomendaciones")
	String uiSubTittleUserUserRec();

	@DefaultStringValue("Tus valoraciones")
	String ruiValoraciones();
	
	@DefaultStringValue("Consultar")
	String ruiSearchUser();
	
	@DefaultStringValue("Id de usuario")
	String uiUserId();
	
	@DefaultStringValue("Calcular usuario-usuario")
	String ruiRecUsuario();
	
	@DefaultStringValue("Similaridad")
	String uiSimilaridad();

	//------------------------//
}

