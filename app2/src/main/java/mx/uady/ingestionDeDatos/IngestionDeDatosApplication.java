package mx.uady.ingestionDeDatos;

import java.lang.*;
import java.io.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import org.renjin.script.RenjinScriptEngine;
import org.renjin.sexp.*;
import org.renjin.primitives.matrix.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.*;
import java.util.Optional;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import javax.script.ScriptException;
import java.lang.InterruptedException;

import mx.uady.ingestionDeDatos.model.Propiedad;
import mx.uady.ingestionDeDatos.model.Regresion;
import mx.uady.ingestionDeDatos.repository.RegresionRepository;
import mx.uady.ingestionDeDatos.repository.PropiedadRepository;

@SpringBootApplication
@EnableScheduling
public class IngestionDeDatosApplication {

	@Autowired 
	private RegresionRepository regresionRepository;
    
    @Autowired
    private PropiedadRepository propiedadRepository;

	public static void main(String[] args) {
		SpringApplication.run(IngestionDeDatosApplication.class, args);
	}

	//@Scheduled(cron="@hourly")
	@Scheduled(initialDelay = 29000, fixedRate = 30000)
	public void saveRegresionLineal() throws InterruptedException, IOException, URISyntaxException, ScriptException {
		List<Propiedad> propiedades = propiedadRepository.findAll();
		List<Float> precios = new ArrayList<Float>();
		List<Integer> banos = new ArrayList<Integer>();
		List<Integer> habitaciones = new ArrayList<Integer>();
		List<Float> metros = new ArrayList<Float>();

		for (Propiedad propiedad : propiedades) {
			precios.add( propiedad.getPrecio() );
			banos.add( propiedad.getBanos() );
			habitaciones.add( propiedad.getNumHabitaciones() );
			metros.add( propiedad.getMetrosCuadrados() );
		}

		RenjinScriptEngine engine = new RenjinScriptEngine();
		/*System.out.println(IngestionDeDatosApplication.class.getClassLoader().getResource("regresion-lineal.R"));
		URI rScriptUri = IngestionDeDatosApplication.class.getClassLoader().getResource("regresion-lineal.R").toURI();
		Path inputScript = Paths.get(rScriptUri);
		String meanScriptContent = Files.lines(inputScript).collect(Collectors.joining());*/
		InputStream i = IngestionDeDatosApplication.class.getClassLoader().getResourceAsStream("regresion-lineal.R");
		BufferedReader r = new BufferedReader(new InputStreamReader(i));
		String val = "";
        String l;
        while((l = r.readLine()) != null) {
        	val = val + l + "\n";
        } 
		i.close();
		String meanScriptContent = val;
		/*Stream<String> lines = new BufferedReader(new InputStreamReader(i)).lines();
		String meanScriptContent = lines.collect(Collectors.joining());*/
		Float[] precios_arr = new Float[precios.size()];
		Integer[] banos_arr = new Integer[banos.size()];
		Integer[] habitaciones_arr = new Integer[habitaciones.size()];
		Float[] metros_arr = new Float[metros.size()];
		precios_arr = precios.toArray(precios_arr);
		banos_arr = banos.toArray(banos_arr);
		habitaciones_arr = habitaciones.toArray(habitaciones_arr);
		metros_arr = metros.toArray(metros_arr);
		Double[] precios_ar = IngestionDeDatosApplication.convertFloatsToDoubles(precios_arr);
		Double[] banos_ar = IngestionDeDatosApplication.convertIntsToDoubles(banos_arr);
		Double[] habitaciones_ar = IngestionDeDatosApplication.convertIntsToDoubles(habitaciones_arr);
		Double[] metros_ar = IngestionDeDatosApplication.convertFloatsToDoubles(metros_arr);
		System.out.println(meanScriptContent);
		engine.put("array_precios", precios_ar);
		engine.put("array_banos", banos_ar);
		engine.put("array_habitaciones", habitaciones_ar);
		engine.put("array_metros", metros_ar);
		engine.eval(meanScriptContent);
		Vector result = (Vector)engine.eval("regresion_lineal(array_precios,array_banos,array_habitaciones,array_metros)");
		System.out.println("Regresion Ejecutada Con Exito");
		
		Optional<Regresion> opt = regresionRepository.findById(1);

        if(!opt.isPresent()) {
			Regresion regresionCreada = new Regresion();
			regresionCreada.setIntercepto(result.getElementAsDouble(0));
			regresionCreada.setQ1(result.getElementAsDouble(1));
			regresionCreada.setQ2(result.getElementAsDouble(2));
			regresionCreada.setQ3(result.getElementAsDouble(3));
			regresionCreada.setFechaRegresion(new Date());
            regresionRepository.save(regresionCreada);
        } else {
			Regresion regresion = opt.get();
			regresion.setIntercepto(result.getElementAsDouble(0));
			regresion.setQ1(result.getElementAsDouble(1));
			regresion.setQ2(result.getElementAsDouble(2));
			regresion.setQ3(result.getElementAsDouble(3));
			regresion.setFechaRegresion(new Date());
			regresionRepository.save(regresion);
        }
        
	}

	public static Double[] convertFloatsToDoubles(Float[] input)
	{
		if (input == null)
		{
			return null; // Or throw an exception - your choice
		}
		Double[] output = new Double[input.length];
		for (int i = 0; i < input.length; i++)
		{
			output[i] = Double.valueOf(input[i]);
		}
		return output;
	}

	public static Double[] convertIntsToDoubles(Integer[] input)
	{
		if (input == null)
		{
			return null; // Or throw an exception - your choice
		}
		Double[] output = new Double[input.length];
		for (int i = 0; i < input.length; i++)
		{
			output[i] = Double.valueOf(input[i]);
		}
		return output;
	}

}
