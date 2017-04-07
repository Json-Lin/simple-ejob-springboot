
package dip.jobs.dataflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import dip.model.City;
import dip.services.CityService;

public class MySimpleJob implements SimpleJob {
    
	@Autowired
	private CityService cityService;
	
    @Override
    public void execute(final ShardingContext shardingContext) {
        List<City> data = cityService.findCitys(shardingContext.getShardingTotalCount(), shardingContext.getShardingItem());
        for(City city : data){
			System.out.println("------Thread-"+Thread.currentThread().getId()
					+" shardItem:"+shardingContext.getShardingItem()
					+" name:"+city.getName()
					+" state:"+city.getState());
		}
    }
}
