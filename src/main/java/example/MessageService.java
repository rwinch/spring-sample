package example;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

	@HasReadPermission("#name")
	String sayHello(String name) {
		return "Hello " + name;
	}
}
