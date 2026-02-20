package club.javafamily;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test01 {
	public static void main(String[] args) {
		
		Test01 t1 = new Test01();
		
		Object proxy = Proxy.newProxyInstance(t1.getClass().getClassLoader(), 
				t1.getClass().getInterfaces(), new MyInvo());
		System.out.println(proxy);
	}
	
	static class MyInvo implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			return null;
		}
		
	}
}
