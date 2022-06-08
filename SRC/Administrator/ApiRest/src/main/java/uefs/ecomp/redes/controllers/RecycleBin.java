package uefs.ecomp.redes.controllers;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
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
@RequestMapping("/recyclebin")
public class RecycleBin {

    /**
     * Método responsável por retornar "N" lixeiras mais críticas.
     * 
     * @param number Refere-se ao número de lixeiras desejado a ser visto.
     * @return Retorna lista de lixeiras mais críticas
     */
    @GetMapping
    public List<String> getRecycleBinCritical(final @RequestBody int number) {
        final List<String> stringList = new LinkedList<>();
        final List<JSONObject> jsonList = Service.getAllDataSectors();
        jsonList.sort(new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                return Integer.compare(o1.getInt("USAGE"), o2.getInt("USAGE"));
            }
        });
        for (int i = 0; i < number && i < jsonList.size(); i++) {
            stringList.add(jsonList.get(i).toString().replace("\"", ""));
        }
        return stringList;
    }
}
