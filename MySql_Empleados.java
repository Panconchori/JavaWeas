package Data_Layer;

import BO_Layer.BussinesObjects.OEmpleados;
import BO_Layer.BussinesObjects.OGerentes;
import BO_Layer.BussinesObjects.OIngenieros;
import BO_Layer.BussinesObjects.OSecretarios;
import BO_Layer.BussinesObjects.OTabla;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Desarrollo
 */
public class MySql_Empleados {  
    
    private ConexionBaseDatos Conexion;
    
    public MySql_Empleados() throws Exception{
        
        Conexion = new ConexionBaseDatos();
              
        if(!Conexion.estaConectado())
        {
            try {
                throw new Exception("No se conecto a la BD");
            } catch (Exception ex) {
               // Logger.getLogger(MySql_Tabla.class.getName()).log(Level.SEVERE, null, ex);
               throw ex;
            }
        }
    
    }
    
    private  ArrayList<OEmpleados> Cargar_ResultSet(ResultSet rs) throws Exception
    {
        ArrayList<OEmpleados> _resultado = new ArrayList<OEmpleados>();
        try
        {
            int id_tipo_de_empleado = 0;
            
            while (rs.next()){
                switch(rs.getInt("fk_id_tipo_de_empleado")){
                    
                    case 1: //Es ingeniero
                        OIngenieros ing = new OIngenieros();
                        
                        ing.setId_empleado(rs.getInt("id_empleado"));
                        ing.setNombre(rs.getString("nombre"));
                        ing.setApellido(rs.getString("apellido"));
                        ing.setTipo_de_empleado(rs.getString("tipo_de_empleado"));
                        ing.setSueldo(rs.getFloat("sueldo"));
                        ing.setEspecialidad(rs.getString("especialidad"));
                        
                        _resultado.add(ing);
                        break;
                        
                    case 2://Es gerente
                        OGerentes g =  new OGerentes();
                        
                        g.setNombre(rs.getString("nombre"));
                        g.setApellido(rs.getString("apellido"));
                        g.setTipo_de_empleado(rs.getString("tipo_de_empleado"));
                        g.setArea(rs.getString("area"));
                        g.setId_empleado(rs.getInt("id_empleado"));
                        g.setSueldo(rs.getFloat("sueldo"));
                        
                        _resultado.add(g);
                        break;               
                        
                    case 3://Es Secretario
                        OSecretarios sec =  new OSecretarios();
                        
                        sec.setNombre(rs.getString("nombre"));
                        sec.setApellido(rs.getString("apellido"));
                        sec.setTipo_de_empleado(rs.getString("tipo_de_empleado"));
                        sec.setDespacho(rs.getString("area"));
                        sec.setId_empleado(rs.getInt("id_empleado"));
                        sec.setSueldo(rs.getFloat("sueldo"));
                        
                        _resultado.add(sec);
                        break;               
                }
                
                                    
            }

            return _resultado;
        }
        catch (Exception error)
        {
            throw new Exception("Ocurrio un error al cargar los datos en Cargar_ResultSet - MySql_Tabla " + error.getMessage());
        }
    }
    
    
    public  ArrayList<OEmpleados> Obtener_Empleados() throws Exception
    {
         ArrayList<OEmpleados> _resultado;
        StringBuilder sql;
        sql = new StringBuilder();
        try
        {
            sql.append("select em.id_empleado, em.fk_id_tipo_de_empleado, em.nombre, em.apellido, em.sueldo,ing.*, ge.*, esp.Nombre as especialidad, te.nombre as tipo_de_empleado");
            sql.append(" from empleados as em ");
            sql.append(" left join ingeniero as ing on em.id_empleado = ing.fk_id_empleado");
            sql.append(" left join gerente as ge on em.id_empleado = ge.fk_id_empleado");
            sql.append(" left join especialidades as esp on esp.id_especialidades = ing.fk_id_especialidad");
            sql.append(" left join tipodeempleado as te on te.Id_tipo_de_empleado = em.fk_id_tipo_de_empleado");

            _resultado = this.Cargar_ResultSet(getConexion().ejecutarConsulta(sql.toString()));

            return _resultado;
        }
        catch (Exception error)
        {
            throw error;
        }
    }
    
    
        
    public void Insertar_registro(String pCampo){
       try{
            getConexion().ejecutarUpDate("INSERT INTO tabla  (Campo) VALUES('"+pCampo+"')");
       }catch(Exception ex){
           throw ex;
       }
    }
    
    public void Actualizar_Sueldo(OEmpleados empleado) throws Exception {
    try {
        String sql = "UPDATE empleados SET sueldo = ? WHERE id_empleado = ?";
        PreparedStatement statement = Conexion.getConexion().prepareStatement(sql);
        statement.setFloat(1, empleado.getSueldo());
        statement.setInt(2, empleado.getId_empleado());
        statement.executeUpdate();
        statement.close();
    } catch (SQLException ex) {
        throw ex;
    }
}

    /**
     * @return the Conexion
     */
    public ConexionBaseDatos getConexion() {
        return Conexion;
    }

    /**
     * @param Conexion the Conexion to set
     */
    public void setConexion(ConexionBaseDatos Conexion) {
        this.Conexion = Conexion;
    }

}
