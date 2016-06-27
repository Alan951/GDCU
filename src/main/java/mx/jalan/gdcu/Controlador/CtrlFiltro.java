package mx.jalan.gdcu.Controlador;

import mx.jalan.gdcu.Modelo.FiltroBusqueda;
import mx.jalan.gdcu.Vista.Buscador.FiltroBuscadorView;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class CtrlFiltro {
    
    //private FiltroView filtroView;
    private FiltroBuscadorView filtroView;
    
    private FiltroBusqueda filtroBusqueda;
    
    public CtrlFiltro(FiltroBusqueda filtroBusqueda){
        this.filtroBusqueda = filtroBusqueda;
    }
    
    public void initConfig(FiltroBusqueda filtro){
        if(filtro != null){
            //Init Extensiones
            for(String ext : filtro.getExtensiones()){
                if(ext.equalsIgnoreCase("zip"))
                    filtroView.getCBZip().setSelected(true);
                else if(ext.equalsIgnoreCase("rar"))
                    filtroView.getCBRar().setSelected(true);
                else{
                    filtroView.getCBExt().addItem(ext);
                }
            }
            
            //Init tamaño archivos
            if(filtro.getTamañoInf() != -1){
                filtroView.getTxtTamañoInf().setText(filtro.getTamañoInf()+"");
                filtroView.getCBUnidadInf().setSelectedItem(filtro.getUnidadInf());
            }if(filtro.getTamañoSup() != -1){
                filtroView.getTxtTamañoSup().setText(filtro.getTamañoSup()+"");
                filtroView.getCBUnidadSup().setSelectedItem(filtro.getUnidadSup());
            }
            
            //Init date
            if(filtro.getFechaInf() != null){
                filtroView.getModeloDateInf().setValue(filtro.getFechaInf());
            }if(filtro.getFechaSup() != null){
                filtroView.getModeloDateSup().setValue(filtro.getFechaSup());
            }
            
            //Init atributos ocultos
            if(filtro.archivoPuedeSerOcuto()){
                filtroView.getCBarchivos().setSelected(true);
            }if(filtro.carpetaPuedeEstarOculta()){
                filtroView.getCBcarpetas().setSelected(true);
            }
            
        }else{
            filtroView.getCBRar().setSelected(true);
            filtroView.getCBZip().setSelected(true);
        }
    }
    
    public boolean verificarExtension(String ext){
        if(ext.trim().isEmpty()){
            return false;
        }
        
        return true;
    }
    
    public FiltroBusqueda getFiltroBusqueda(){
        return filtroBusqueda;
    }
    
    
    
}
