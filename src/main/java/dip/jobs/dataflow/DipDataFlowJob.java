package dip.jobs.dataflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import dip.model.City;
import dip.services.CityService;

/**
 * <p>Description: </p>
 * @author JiaSonglin
 * @version V1.0,2017年2月9日 下午1:47:38
 */
@Component
public class DipDataFlowJob implements DataflowJob<City>{

	@Autowired
	private CityService cityService;
	
	@Override
	public List<City> fetchData(ShardingContext shardingContext) {
		return cityService.findCitys(shardingContext.getShardingTotalCount(), shardingContext.getShardingItem());
	}

	@Override
	public void processData(ShardingContext shardingContext, List<City> data) {
		for(City city : data){
			System.out.println("------Thread-"+Thread.currentThread().getId()
					+" shardItem:"+shardingContext.getShardingItem()
					+" name:"+city.getName()
					+" state:"+city.getState());
		}
	}

}
