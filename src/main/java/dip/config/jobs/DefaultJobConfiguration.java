package dip.config.jobs;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author JiaSonglin
 * @version V1.0,2017年2月27日 下午3:42:04
 */
public class DefaultJobConfiguration {

	/**
	 * 获取简单作业配置
	 * @param jobClass 作业类
	 * @param cron corn表达式
	 * @param shardingTotalCount 总分片项
	 * @param shardingItemParameters 分片参数
	 * @param overWriteProperty 本地配置是否可覆盖注册中心配置
	 * @return
	 */
	public static LiteJobConfiguration getSimpleJobConfiguration(final Class<? extends SimpleJob> jobClass,
			final String cron, final int shardingTotalCount, final String shardingItemParameters ,boolean overWriteProperty) {
		JobCoreConfiguration coreConfig = getJobCoreConfiguration(jobClass, cron, shardingTotalCount,shardingItemParameters);
		SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(coreConfig , jobClass.getCanonicalName());
		return LiteJobConfiguration
				.newBuilder(simpleJobConfiguration)
				.overwrite(overWriteProperty).build();
	}

	private static JobCoreConfiguration getJobCoreConfiguration(final Class<? extends ElasticJob> jobClass, final String cron,
			final int shardingTotalCount, final String shardingItemParameters) {
		JobCoreConfiguration coreConfig = JobCoreConfiguration
				.newBuilder(jobClass.getName(), cron, shardingTotalCount)
				.shardingItemParameters(shardingItemParameters)
				.build();
		return coreConfig;
	}
	
	/**
	 * 获取流式作业配置
	 * @param jobClass 作业类
	 * @param cron corn表达式
	 * @param shardingTotalCount 总分片项
	 * @param shardingItemParameters 分片参数
	 * @param overWriteProperty 本地配置是否可覆盖注册中心配置
	 * @return
	 */
	public static LiteJobConfiguration getDataFlowJobConfig(@SuppressWarnings("rawtypes") final Class<? extends DataflowJob> jobClass,
			final String cron, final int shardingTotalCount, final String shardingItemParameters, boolean overWriteProperty) {
		JobCoreConfiguration coreConfig = getJobCoreConfiguration(jobClass, cron, shardingTotalCount,shardingItemParameters);
		DataflowJobConfiguration dataflowJobConfiguration = new DataflowJobConfiguration(coreConfig, jobClass.getCanonicalName(), true);
		LiteJobConfiguration jobConfig = LiteJobConfiguration.newBuilder(dataflowJobConfiguration).overwrite(overWriteProperty).build();
		return jobConfig;
	}
}
