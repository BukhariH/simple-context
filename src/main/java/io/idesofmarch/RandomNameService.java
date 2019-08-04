package io.idesofmarch;

import java.util.Random;

@Component
public class RandomNameService {

	private static final String[] NAMES = new String[]{"Hasnain", "Frane", "Yoni", "Clara"};

	private final Random random = new Random();

	public String getRandomName() {
		return NAMES[Math.abs(random.nextInt()) % NAMES.length];
	}

}
