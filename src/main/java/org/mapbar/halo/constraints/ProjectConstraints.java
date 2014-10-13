package org.mapbar.halo.constraints;

import org.mapbar.halo.utils.InjectUtils;

/**
 * 项目级约束，每个项目都必须要实现的
 *
 */

public interface ProjectConstraints extends InjectUtils{
	
	/**获得项目的名称*/
	String getProjectName();

}
