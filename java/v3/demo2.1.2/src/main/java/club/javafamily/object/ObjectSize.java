package club.javafamily.object;

import org.openjdk.jol.info.ClassLayout;

public class ObjectSize {
	public static void main(String[] args) {
		int[] arrayObject = new int[]{1};
		// 打印对象大小 -XX:-UseCompressedOops
		System.out.println(ClassLayout.parseInstance(arrayObject).toPrintable());
		
		
		EmptyObject emptyObject = new EmptyObject();
		System.out.println(ClassLayout.parseInstance(emptyObject).toPrintable());
		
		NormalObject normalObject = new NormalObject();
		System.out.println(ClassLayout.parseInstance(normalObject).toPrintable());
		
		ComplexObject complexObject = new ComplexObject();
		System.out.println(ClassLayout.parseInstance(complexObject).toPrintable());
	}
}

class ComplexObject {
	int[] a = {1, 2, 3};
	short b;
	byte c;
}

class NormalObject {
	int[] a = {1};
}

class EmptyObject {
}
