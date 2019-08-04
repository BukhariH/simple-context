package io.idesofmarch;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Application {

	private static Map<Class<?>, Object> instances = new HashMap<>();

	public static void main(String[] args) {
		try {
			Reflections ref = new Reflections("io.idesofmarch", new FieldAnnotationsScanner(), new TypeAnnotationsScanner(), new SubTypesScanner());

			// Create a single instance of every component
			Set<Class<?>> components = ref.getTypesAnnotatedWith(Component.class);
			for (Class<?> component : components) {
				instances.put(component, component.getConstructor().newInstance());
			}

			// Wire fields
			Set<Field> fields = ref.getFieldsAnnotatedWith(Autowired.class);
			for (Field field : fields) {
				//	This allows us to modify private fields
				field.setAccessible(true);
				field.set(instances.get(field.getDeclaringClass()), instances.get(field.getType()));
			}

			start();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println(Arrays.toString(e.getStackTrace()));
		}
	}

	private static void start() {
		HelloWorldService o = (HelloWorldService) instances.get(HelloWorldService.class);
		o.printHelloWorld();
	}

}
