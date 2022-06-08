package uefs.ecomp.redes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uefs.ecomp.redes.services.Service;

/**
 * Classe responsável por comportar-se como controlador de lixeiras.
 * 
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
@RestController
@RequestMapping("/recyclebin/id")
public class RecycleBinId {

    /**
     * Método responsável por retornar os dados de determinada lixeira.
     * 
     * @param id Refere-se ao id da lixeira.
     * @return Retorna os dados da lixeira cujo ID foi fornecido.
     */
    @GetMapping
    public String getRecycleBinCritical(final @RequestBody String id) {
        return Service.getRecycleBinById(id);
    }
}
