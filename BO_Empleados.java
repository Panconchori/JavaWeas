/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BO_Layer.Objects;

import BO_Layer.BussinesObjects.*;
import Data_Layer.MySql_Empleados;
import java.util.ArrayList;


/**
 *
 * @author Desarrollo
 */
public class BO_Empleados {
    
    

    public BO_Empleados()  {
      
       
    }
    
    public ArrayList<OEmpleados> Obtener_Empleados() throws Exception
    {
        try{
    
            MySql_Empleados capa_datos = new MySql_Empleados();
            
            ArrayList<OEmpleados> coleccion =  capa_datos.Obtener_Empleados();
               
            return coleccion;
        }catch(Exception ex){
            throw ex;
        }
    }
    
   public void incrementarSueldo( ArrayList<OEmpleados> plista) {
        try{
		for ( OEmpleados e : plista) {
			if (e instanceof OIngenieros)
				e.setSueldo(Math.round(e.getSueldo()*1.15));			
			else{ 
				if (e instanceof OGerentes)
					e.setSueldo(Math.round(e.getSueldo()*1.10));
				
                                else{ 
					if (e instanceof OSecretarios)
						e.setSueldo(Math.round(e.getSueldo()*1.05));
					
				}
                                
			}
		}
    }catch(Exception ex){
        throw ex;
  }
}
   public void Actualizar_Sueldo(OEmpleados empleado) throws Exception {
    try {
        MySql_Empleados capa_datos = new MySql_Empleados();
        capa_datos.Actualizar_Sueldo(empleado);
    } catch (Exception ex) {
        throw ex;
    }
}

}
