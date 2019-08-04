package io.idesofmarch;

@Component
public class HelloWorldService {

	@Autowired
	private RandomNameService randomNameService;

	public void printHelloWorld() {
		String name = randomNameService.getRandomName();
		System.out.println("Hello " + name + "!");
	}

}
