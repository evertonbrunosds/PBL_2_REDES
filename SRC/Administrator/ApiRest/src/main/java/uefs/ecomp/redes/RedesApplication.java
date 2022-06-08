package uefs.ecomp.redes;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe responsável por startar a aplicação springboot.
 * @author Everton Bruno Silva dos Santos.
 * @version v1.0
 */
@SpringBootApplication
public class RedesApplication {

	/**
	 * Método responsável por startar a aplicação springboot.
	 * @param args refere-se aos argumentos de entrada.
	 */
	public static void main(String[] args) {
		File file = new File("datasectors.json");
		if (file.exists()) {
			file.delete();
		}
		SpringApplication.run(RedesApplication.class, args);
	}

}
