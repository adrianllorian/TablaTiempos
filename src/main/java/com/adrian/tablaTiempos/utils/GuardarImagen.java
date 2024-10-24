package com.adrian.tablaTiempos.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
	public class GuardarImagen {
	
	
	public String guardarImagen(MultipartFile foto) {
		Path directorioImagenes  = Paths.get("src//main//resources//static"); //Lugar donde guardar la imagen
		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath(); //Pasaslo a String
		String[] extension = foto.getContentType().split("/"); //recoger el formatod e al foto
		String nombreDeLaFoto = getClaveFinal() + "."+ extension[1]; //Preparar el nombred 
		try {
			byte [] bytesImg = foto.getBytes(); //Pasar la boto a un array de bytes
			Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + nombreDeLaFoto); //Ruta completa donde voy a guardar la foto
			Files.write(rutaCompleta, bytesImg);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return nombreDeLaFoto;
	}
	
	public byte[] verImagen(String nombreDeLaFoto) {
		String ruta = "src//main//resources//static/"+ nombreDeLaFoto;
		byte[] byteArray ;
		try {
			InputStream in = new FileInputStream(ruta);
			byteArray = in.readAllBytes();
			in.close();
			return byteArray;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

		
		
		private  String crearNombreFoto() {
			String salida= "";
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			Date date = new Date();
			salida = dateFormat.format(date);
			return salida;
		}
		
		private  String crearClaveAleatoria() {
			Random random = new Random();
			int numero = random.nextInt(999 + 100) + 100;
			return String.valueOf(numero);
		}
		
		private String getClaveFinal() {
			return crearNombreFoto() + "-" + crearClaveAleatoria();
		}
	}

