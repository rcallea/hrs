package co.edu.uniandes.hrs.client;

import com.google.gwt.i18n.client.Constants;

public interface RSConstants extends Constants{
	
	/**
	 * á \u00E1		é \u00E9		í \u00ED		ó \u00F3		ú \u00FA		ñ \u00F0		Ñ \u00F1		¿ \u00BF
	 * @return
	 */
	
	@DefaultStringValue("Recomendador por filtraje colaborativo")
	String cfTitle();

	@DefaultStringValue("Tama\u00F1o del dataset de pruebas")
	String cfDatasetSize();

	@DefaultStringValue("N\u00FAmero de vecinos")
	String cfNeighbors();

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
}

