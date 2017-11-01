package pucrs.myflight.modelojojo;

import org.jxmapviewer.viewer.GeoPosition;

public class Geo extends GeoPosition{
	
	public Geo(double latitude, double longitude) {
		super(latitude, longitude);		
	}

	@Override
	public String toString() {
		return "Geo [latitude=" + getLatitude() + ", longitude=" + getLongitude() + "]";
	}
	
	public static double distancia(Geo geo1, Geo geo2){
		double dist =0;
		int r = 6371;
		
		double lat1 = Math.toRadians(geo1.getLatitude());
		double lat2 =Math.toRadians(geo2.getLatitude());
		double difLat = (Math.toRadians(geo1.getLatitude()) - Math.toRadians(geo2.getLatitude()))/2;
		double difLong = (Math.toRadians(geo1.getLongitude()) - Math.toRadians(geo2.getLongitude()))/2;
		
		double a = Math.asin(Math.pow(difLat,2) + Math.pow(difLong,2) * Math.cos(lat1)*Math.cos(lat2));
		
		dist = 2*r *Math.asin(Math.sqrt(a));
		
		return dist;
	}
	
	public double distancia(Geo geo2){
		return distancia(this,geo2);
	}
}
