package converters.generic;

public class DataConverters {
	
	public static float GBToMB(float GB){
		return GB*1024;
	}
	
	public static float MBToGB(float MB){
		return MB/1024;
	}
	
	public static boolean StringToBoolean(String booleanString){
		if(booleanString.toLowerCase().equals("true"))
			return true;
		else
			return false;
	}
	
	public static int StringToInteger(String integerString){
		return Integer.parseInt(integerString);
	}
	
	public static float StringToFloat(String floatString){
		return Float.parseFloat(floatString);
	}

}
