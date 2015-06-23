package com.asiaworld.tmuhj.core.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 儲存檢索log
 * 
 * @author Roderick
 * @version 2015/1/20
 */
public class PageInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5010620168664114783L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		if (session.get("importList") != null) {
			session.remove("cellNames");
			session.remove("importList");
			session.remove("total");
			session.remove("normal");
			session.remove("checkItemSet");
		}

		return invocation.invoke();
	}
}