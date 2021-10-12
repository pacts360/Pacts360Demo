package gov.ao.uscourts.demo.core;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSessionListener.super.sessionCreated(se);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		se.getSession().invalidate();
	}
}
