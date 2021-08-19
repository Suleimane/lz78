package nanque.suleimane.lz78;

import java.text.DecimalFormat;

public class Util {
	
	public static String doubleFormat(double numero) {
		DecimalFormat formato = new DecimalFormat("#.###"); 
		return formato.format(numero);
	}

}
