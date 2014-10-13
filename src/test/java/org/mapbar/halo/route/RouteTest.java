package org.mapbar.halo.route;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.mapbar.halo.HaloLoaderFactory;
import org.mapbar.halo.client.ClientContext;
import org.mapbar.halo.client.IClientContext;
import org.mapbar.halo.constraints.IHaloConstraints;
import org.mapbar.halo.servlet.HaloRequest;

public class RouteTest {
	private static IHaloConstraints contraints;
	@Before
	public void setUp() throws Exception {
		contraints = HaloLoaderFactory.getHaloContraints();
	}

	@Test
	public void testMatchAndInvoke() {
		RouteTable.getInsatnce().initRoute(contraints);
		HttpServletRequest httpServletRequest = EasyMock.createMock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = EasyMock.createMock(HttpServletResponse.class);
		HaloRequest  haloRequset = new HaloRequest(httpServletRequest);
		IClientContext clientContext = new ClientContext(haloRequset,httpServletResponse);
//		Route route = new Route();
//		route.matchAndInvoke(clientContext);
	
	}

}
