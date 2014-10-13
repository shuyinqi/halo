package org.mapbar.halo.route;

import org.junit.Before;
import org.junit.Test;
import org.mapbar.halo.HaloLoaderFactory;
import org.mapbar.halo.constraints.IHaloConstraints;

/**
 * @Description
 * @author <a href='huhj@mapbar.com'>胡洪佳</a>
 * @date 2013-5-3
 */
public class TestRouteTable {

	private IHaloConstraints contraints;

	@Before
	public void setUp() throws Exception {
		contraints = HaloLoaderFactory.getHaloContraints();
	}

	@Test
	public void testInitRoute() {
		// 初始化RouteTable对象
		RouteTable.getInsatnce().initRoute(contraints);
		RouteTable.getInsatnce().selectRoute("/hello/user//password");
		RouteTable.getSimpleKey("/hello/user/{name}/password");
		
	}
}
