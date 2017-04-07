package dip.config.jobs;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import dip.jobs.dataflow.DipDataFlowJob;
import dip.jobs.dataflow.DipSimpleElasticJob;
import dip.jobs.dataflow.MySimpleJob;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author JiaSonglin
 * @version V1.0,2017年2月9日 下午1:57:02
 */
@Configuration
public class DipJobConfig {

	@Resource
	private ZookeeperRegistryCenter regCenter;

	@Resource
	private JobEventConfiguration jobEventConfiguration;

//	@Bean
	public DipDataFlowJob getDipDataFlowJob() {
		return new DipDataFlowJob();
	}

//	@Bean(initMethod = "init")
	public JobScheduler dipDataFlowJobScheduler(DipDataFlowJob dipDataFlowJob,
			@Value("${dataflowJob.cron}") final String cron,
			@Value("${dataflowJob.shardingTotalCount}") final int shardingTotalCount,
			@Value("${dataflowJob.shardingItemParameters}") final String shardingItemParameters) {
		return new SpringJobScheduler(dipDataFlowJob, regCenter, 
				DefaultJobConfiguration.getDataFlowJobConfig(dipDataFlowJob.getClass(), cron, shardingTotalCount, shardingItemParameters,true), 
				jobEventConfiguration);
	}

	@Bean
	public MySimpleJob getDipSimpleJob() {
		return new MySimpleJob();
	}
	
	@Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final MySimpleJob dipSimpleJob,
    		@Value("${simpleJob.cron}") final String cron, 
    		@Value("${simpleJob.shardingTotalCount}") final int shardingTotalCount,
    		@Value("${simpleJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(dipSimpleJob, regCenter, 
        		DefaultJobConfiguration.getSimpleJobConfiguration(dipSimpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters, true), 
        		jobEventConfiguration);
    }
    
//    @Bean
    public DipSimpleElasticJob getDipSimpleElasticJob() {
    	return new DipSimpleElasticJob();
    }
    
//    @Bean(initMethod = "init")
    public JobScheduler dipSimpleElasticJob(final DipSimpleElasticJob mySimpleJob,
    		@Value("${dipSimpleElasticJob.cron}") final String cron, 
    		@Value("${dipSimpleElasticJob.shardingTotalCount}") final int shardingTotalCount,
    		@Value("${dipSimpleElasticJob.shardingItemParameters}") final String shardingItemParameters) {
    	return new SpringJobScheduler(mySimpleJob, regCenter, getDipSimpleElasticJobConfiguration(mySimpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration);
    }
    
    private LiteJobConfiguration getDipSimpleElasticJobConfiguration(final Class<? extends DipSimpleElasticJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
    	return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
    			jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
    }
}
