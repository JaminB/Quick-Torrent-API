package converters.generic;

public class DataConverters {
	
	public static float GBToMB(float GB){
		return GB*1024;
	}
	
	public static float MBToGB(float MB){
		return MB/1024;
	}

}
