package dip.jobs.dataflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleElasticJob;

import dip.model.City;
import dip.services.CityService;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author JiaSonglin
 * @version V1.0,2017年2月21日 下午3:30:41
 */
public class DipSimpleElasticJob implements SimpleElasticJob<City> {

	@Autowired
	private CityService cityService;

	@Override
	public List<City> loadeData() {
		System.out.println("------Thread get data----" + Thread.currentThread().getId());
		return cityService.findWaitDealCitys();
	}

	@Override
	public void execute(ShardingContext shardingContext, List<City> subData) {
		for (City city : subData) {
			System.out.println("------Thread-" + Thread.currentThread().getId() + " "
					+ shardingContext.getShardingItem() + ": " + city.getName() + ": " + city.getId());
			cityService.dealSuccess(city.getId().toString());
		}
	}

}
