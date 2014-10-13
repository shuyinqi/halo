package org.mapbar.halo.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mapbar.halo.route.RouteContext;
import org.mapbar.halo.route.RouteTable;

/** 
 * @Description
 * @author <a href='huhj@mapbar.com'>胡洪佳</a>
 * @date 2013-5-2
 */
public class TestHaloFilter {
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	@Before
	public void setUp() throws Exception {
		request = EasyMock.createMock(HttpServletRequest.class);
		response = EasyMock.createMock(HttpServletResponse.class);
		
		EasyMock.expect(request.getServletPath()).andReturn("/hello/aa");
	}

	@Test
	public void testInit() {
//		RouteContext rouetContext = new RouteContext(request,response);
//		RouteTable.selectRoute(rouetContext);
	}
	
}
