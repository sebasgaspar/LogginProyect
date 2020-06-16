/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MySQL;

/**
 *
 * @author sebastian
 */
import java.sql.*;

public class Conexion {

    private static Connection cnx = null;

    public static Connection obtener() throws SQLException, ClassNotFoundException {
        if (cnx == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                //Realizamos la conexion con la base de datos
                cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/usuarios?autoReconnect=true&useSSL=false", "sebasgaspar", "99061800642123");
                System.out.println("Conexion Satisfactoria con la base de datos");

            } catch (SQLException ex) {
                throw new SQLException(ex);
            } catch (ClassNotFoundException ex) {
                throw new ClassCastException(ex.getMessage());
            }
        }
        return cnx;
    }

    public static void cerrar() throws SQLException {
        if (cnx != null) {
            cnx.close();
        }
    }
}
