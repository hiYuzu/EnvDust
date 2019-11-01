package com.kyq.env.model;

import java.util.List;

/**
 * 
 * <p>[功能描述]：天气信息主类</p>
 *
 */
public class WeatherModel {
	
	//和天气
	private List<HeWeatherModel> HeWeather6;

	/**
	 * @return the heWeather6
	 */
	public List<HeWeatherModel> getHeWeather6() {
		return HeWeather6;
	}

	/**
	 * @param heWeather6 the heWeather6 to set
	 */
	public void setHeWeather6(List<HeWeatherModel> heWeather6) {
		HeWeather6 = heWeather6;
	}	

}
