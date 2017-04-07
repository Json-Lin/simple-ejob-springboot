package dip.services;

import java.util.List;

import dip.model.City;

/**
 * <p>Description: </p>
 * @author JiaSonglin
 * @version V1.0,2017年2月9日 下午2:30:03
 */
public interface CityService {
	List<City> findCitys(int modSize,int modItem);

	/**
	 * @return
	 */
	List<City> findWaitDealCitys();
	
	void dealSuccess(String cityId);
}
