package club.javafamily;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class MyDBConnPool {
	
	private AtomicInteger connCount;
	private int min;
	private int initCount;
	private int max;
	private int setup;
	private Vector<Connection> connections;
	
	private Connection getRealConnection() {
		// Class.forName.....
		
		return null;
	}

	public MyDBConnPool(
			int defaultConnCound, 
			int setupCount, 
			int minCounnCount, 
			int maxConnCount
			/*int scheduleTime*/)
	{
		connCount = new AtomicInteger(0);
		initCount = defaultConnCound;
		setup = setupCount;
		min = minCounnCount;
		max = maxConnCount;
		
		// init create init count
		connections = new Vector<Connection>();
		
		for(int i = 0; i < initCount; i++) {
			addConn();
		}
	}
	
	public void setupConn() {
		for(int i = 0; i < setup; i++) {
			int count = addConn();
			
			if(count >= connCount.get()) {
				return;
			}
		}
	}
	
	public Connection getConnection() {
		if(connections.isEmpty()) {
			if(connCount.get() >= max) {
				// wait, schedule timer
			}
			else {
				setupConn();
			}
		}
		
		Connection conn = connections.remove(0);
		changeAndGetModulo(false);

		return conn;
	}
	
	private synchronized int addConn() {
		if(connCount.get() >= max) {
			// should custom exception
			throw new RuntimeException("limit create!");
		}
		
		Connection conn = getRealConnection();
		
		Connection proxy = (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(),
				conn.getClass().getInterfaces(), new ConnInvocationHandler(conn));
		
		connections.add(proxy);
		
		return changeAndGetModulo(true);
	}

	private int changeAndGetModulo(boolean add) {
        for (;;) {
            int current = connCount.get();
            int next = add ? current + 1 : (current - 1);
            if(connCount.compareAndSet(current, next))
                return next;
        }
    }
	
	// @Scheduler(scheduleTime)
	private synchronized void clean() throws Exception {
		if(connCount.get() < min + setup /* && check 冗余连接未达到配置的空闲时间 */) {
			return;
		}
		
		Iterator<Connection> it = connections.iterator();
		int removeCount =  connCount.get() - min - setup;
		
		while(it.hasNext()) {
			Connection conn = it.next();
			boolean timeout = false;
			// check time

			if(timeout && removeCount > 0) {
				// 关闭原始对象
				Connection originConn = getOriginConnection(conn);
				originConn.close();
				it.remove();
				removeCount--;
			}
		}
	}
	
	public Connection getOriginConnection(Connection proxy) throws Exception {
        Field field = proxy.getClass().getSuperclass().getDeclaredField("h");
        field.setAccessible(true);
        ConnInvocationHandler invocation = (ConnInvocationHandler) field.get(proxy); 
        Field oringinConn = invocation.getClass().getDeclaredField("oringinConn");
        oringinConn.setAccessible(true);
        return (Connection) oringinConn.get(invocation);
    }
	
	private class ConnInvocationHandler implements InvocationHandler {
		
		private Connection oringinConn;
		
		private ConnInvocationHandler(Connection conn) {
			oringinConn = conn;
		}
		
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if("close".equals(method.getName())) {
				connections.add(0, (Connection) proxy);
				return null;
			}
			else {
				return method.invoke(oringinConn, args);
			}
		}
	}
	
}
