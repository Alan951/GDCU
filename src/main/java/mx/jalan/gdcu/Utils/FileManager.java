/*
Cabecera dañada -> cuando se modifica algo de la cabecera o todo el documento
Final inesperado del archivo -> Cuando se modifica algo del contenido del archivo
x archivo  no es un archivo rar -> Cuando se modifica el inicio del encabezado
 */
package mx.jalan.gdcu.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.exception.ZipExceptionConstants;
import net.lingala.zip4j.model.FileHeader;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class FileManager {
    
    private String unrarPath = null;
    //private final String unrarPath = "C:\\Program Files\\WinRAR\\UnRar.exe";
    //private final String unrarPath = this.getClass().getResource("UnRAR.exe").getPath();
    private String pathFile;
    private String extension;
    private String password;
    
    private int archivosRarConContraseña;
    private int archivosZipConContraseña;
    
    private Process p;
    
    private String[] argsPass = {"", "l", "-p-", "-y", "-o+", ""}; //Verifica si el archivo tiene contraseña y de si es valido
    private String[] argsValidPass = {"", "t", "-p", "-y", "-o+", ""}; //Verifica si la contraseña es valida
    
    private boolean contraseñaValida;
    
    public FileManager() throws URISyntaxException{
        //unrarPath = FileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        
        initUnrar();
        
        archivosRarConContraseña = 0;
        archivosZipConContraseña = 0;
    }
    
    /*
        Dado que para analizar un archivo rar requiere del programa UnRAR (https://www.winrar.es/descargas/unrar)
        Este programa esta dentro de los recursos del programa, initUnrar se encarga de verificar si dentro de la carpeta
        en la que se ejecuta el programa esta este archivo, en caso de que no lo este, entonces descomprime en unrar
        de los recursos a la carpeta Unrar/ utilizando rutas relativas, osease, donde sea que pongas el programa
        estara generando el UnRAR.exe este proceso sera modificado en un futuro.
    
        Si ejecutas el programa desde un IDE, asegurate de que en la carpeta GDCU.src.main.java.resources.unrar este el archivo UnRAR.exe
        ya que lo anterior mensionado solo funciona cuando ejecutas este programa desde el jar con (java -jar <nombredelprograma>.jar)
    
    TODO: Mejorar la generacion del unrar.exe
    */
    public void initUnrar(){
        File f = new File("Unrar");
        if(!f.exists()){
            f.mkdir();
        }
        
        f = new File("Unrar/UnRAR.exe");
        
        if(!f.exists()){
            JarFile jar = null;
            try {
                jar = new JarFile(new java.io.File(FileManager.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName());
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Enumeration enumEntries = jar.entries();
            
            while(enumEntries.hasMoreElements()){
                JarEntry file = (JarEntry)enumEntries.nextElement();
                System.out.println(file.getName());
                
                if(file.getName().equals("unrar/UnRAR.exe")){
                    InputStream is = null;
                    FileOutputStream fos = null;
                    try {
                        is = jar.getInputStream(file);
                        fos = new FileOutputStream(new File("Unrar/UnRAR.exe"));
                        while(is.available() > 0){
                            fos.write(is.read());
                        }   
                        
                    } catch (IOException ex) {
                        Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            fos.close();
                            is.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
            }
        }
        
        unrarPath = f.getAbsolutePath();
        argsPass[0] = unrarPath;
        argsValidPass[0] = unrarPath;
        
    }
    
    public Object[] analizarArchivo() throws IOException, ZipException{
        Object [] data = null;
        
        if(getExtension().equalsIgnoreCase("zip")){
            data = analizarZip();
        }else if(getExtension().equalsIgnoreCase("rar")){
            data = analizarRar();
        }
        
        return data;
    }
        
    public Object[] analizarZip() throws IOException, ZipException{
        ZipFile zipFile = new ZipFile(pathFile);
        
        boolean valido = false;
        String mensaje = null;
        boolean isEncrypted = false;
        
        if(zipFile.isValidZipFile()){
            valido = true;
            if(zipFile.isEncrypted()){
                archivosZipConContraseña++;
                isEncrypted = true;
            }else{
                isEncrypted = false;
            }
        }else{
            mensaje = "Zip no valido";
        }
        
        Object[] data = {valido, mensaje, isEncrypted};
        
        return data;
    }
    
    public Object[] analizarRar() throws IOException{
        boolean valido = true;
        String mensaje = null;
        boolean isEncrypted = false;
        
        ProcessBuilder pb = new ProcessBuilder(argsPass);
        Process p = pb.start();
        
        BufferedReader reader  = new BufferedReader(new InputStreamReader(p.getInputStream()));
        
        String line = null;
        String primerArchivo = null;
        
        int cont = 1;
        
        while((line = reader.readLine()) != null){
            if(cont == 4){
                if(line.endsWith("no es un archivo RAR") || line.endsWith("is not RAR archive")){
                    mensaje = "No es un archivo rar";
                    valido = false;
                }
            }
            if(cont == 5){
                if(line.contains("cabeceras cifradas") || line.contains("encrypted headers")){
                    archivosRarConContraseña++;
                    valido = true;
                    isEncrypted = true;
                }
            }
            
            if(cont == 9){
                primerArchivo = line;
                
                if(primerArchivo.startsWith("*")){
                    archivosRarConContraseña++;
                    isEncrypted = true;
                }
            }
            
            cont++;
        }
        pb = null;
        p = null;
        reader = null;
        //System.gc();
        Object[] data = {valido, mensaje, isEncrypted};
        
        return data;
    }
    
    public boolean validarContraseña()throws IOException, ZipException{
        boolean contraseñaValida = false;
        
        if(extension.equalsIgnoreCase("zip")){
            contraseñaValida = validarContraseñaZip();
        }else if(extension.equalsIgnoreCase("rar")){
            contraseñaValida = validarContraseñaRar();
        }
        
        return contraseñaValida;
    }
    
    //GRACIAS SrikanthLingala <3 http://stackoverflow.com/questions/19244137/check-password-correction-in-zip-file-using-zip4j
    public boolean validarContraseñaZip() throws ZipException{
        boolean contraseñaValida = true;
        
        ZipFile zipFile = new ZipFile(pathFile);
        
        zipFile.setPassword(password.toCharArray());
        
        List<FileHeader> fileHeaders = zipFile.getFileHeaders();
        
        for(FileHeader fileHeader : fileHeaders){
            try{
                InputStream is = zipFile.getInputStream(fileHeader);
                byte[] b= new byte[4*4096];
                while(is.read(b) != -1){}
                is.close();
            }catch(ZipException e){
                if(e.getCode() == ZipExceptionConstants.WRONG_PASSWORD){
                    System.out.println("[DG] Contraseña equivocada");
                    contraseñaValida = false;
                    break;
                }else{
                    e.printStackTrace();
                }
            }catch(IOException e){
                System.out.println("[DG] Probablemente contraseña equivocada");
                contraseñaValida = false;
                //e.printStackTrace();
            }
        }
        
        return contraseñaValida;
    }
    
    public boolean validarContraseñaRar() throws IOException{
        boolean contraseñaValida = false;
        
        ProcessBuilder pb = new ProcessBuilder(argsValidPass);
        cronometro(1);
        p = pb.start();
        
        //BufferedReader reader  = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader readerErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String lineError = null;
        
        lineError = readerErr.readLine();
        if(lineError != null){
            contraseñaValida = false;
        }else{
            contraseñaValida = true;
        }
        /*
        while((lineError = readerErr.readLine()) != null){
            System.out.println(lineError);
            if(lineError != null){
                if(lineError.endsWith("incorrecta.")){
                    contraseñaValida = false;
                }else{
                    contraseñaValida = true;
                }
            }
            
            if(cont == 1){
                if(lineError != null)
                    if(lineError.endsWith("incorrecta.")){
                        contraseñaValida = false;
                        break;
                    }else{
                        contraseñaValida = true;
                        break;
                    }
            }
            cont++;
        }
        */
        return contraseñaValida;
    }
    
    //TODO checar la velocidad con la que comprueba la contraseña
    
    public void cronometro(int segundos){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(segundos*500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(p.isAlive()){
                    System.out.println("No ha terminado de ejecutar");
                    p.destroy();
                    System.out.println("El proceso sigue activo = " + p.isAlive());
                }
                
            }
        });
        
        t.start();
    }
    
    public void setPathFile(String path){
        this.pathFile = path;
        this.extension = Utils.getExtension(pathFile);
        
        argsPass[5] = this.pathFile;
        argsValidPass[2] = "-p"+this.password;
        argsValidPass[5] = this.pathFile;
    }
    
    public void setPathFile(String path, String password){
        this.pathFile = path;
        this.extension = Utils.getExtension(pathFile);
        this.password = password;
        argsPass[5] = this.pathFile;
        argsValidPass[2] = "-p"+this.password;
        argsValidPass[5] = this.pathFile;
    }
    
    public void setPassword(String password){
        this.password = password;
        argsValidPass[2] = "-p"+this.password;
        argsValidPass[5] = this.pathFile;
    }
    
    public String getExtension(){
        return extension;
    }

    public int getArchivosRarConContraseña() {
        return archivosRarConContraseña;
    }

    public int getArchivosZipConContraseña() {
        return archivosZipConContraseña;
    }
}
