package com.kyq.env.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * [功能描述]：动态选择数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDbType();
	}

}
