/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion;

import MySQL.Conexion;
import Objetos.Usuario;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import javax.swing.JOptionPane;

/**
 *
 * @author sebastian
 */
public class GestionUsuarios {

    private Usuario User;

    
    public GestionUsuarios() {
        User = new Usuario(0, null, null);
    }

    //Registra el usuario en la Base de datos
    public boolean Registrar(String usuario, String contrasena) throws SQLException, ClassNotFoundException, UnsupportedEncodingException {
        boolean pasa = true;
        try {
            PreparedStatement consulta;

            //Si el usuario no existe lo crea
            if (!this.existe(usuario)) {
                Connection conexion = Conexion.obtener();
                consulta = conexion.prepareStatement("INSERT INTO Usuarios (Usuario,Contrasena) VALUES(?, ?)");
                consulta.setString(1, usuario);
                String contrasenaEncriptada = encriptar(contrasena);
                consulta.setString(2, contrasenaEncriptada);
                consulta.executeUpdate();
                JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.");

            } else {
                JOptionPane.showMessageDialog(null, "Usuario ya registrado.", "Alert", JOptionPane.WARNING_MESSAGE);
                pasa = false;
            }

        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return pasa;
    }

    //Loguea al usuario 
    public void Loggin(String usuario, String contrasena) throws SQLException, ClassNotFoundException, UnsupportedEncodingException {
        try {
            //Si el usuario existe en la base de datos continua a coincidir la contrasena
            if (this.existe(usuario)) {
                //Desencripta la contrasena
                String cadenaDesencriptada = desencriptar(this.User.getContraseña());
                //Compara las contrasenas 
                if (cadenaDesencriptada.equals(contrasena)) {
                    JOptionPane.showMessageDialog(null, "Usuario y contrasena coinciden Bienvenido...", "Bienvenido",JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(null, "Contrasena NO coinciden...", "Error", JOptionPane.ERROR_MESSAGE);

                }

            } else {
                JOptionPane.showMessageDialog(null, "Usuario no registrado.", "Alert", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException ex) {
            throw new SQLException(ex);
        }

    }

    //Revisa en la BD si existe el usuario
    private boolean existe(String usuario) throws SQLException, ClassNotFoundException {
        boolean existe = true;
        Connection conexion = Conexion.obtener();

        try {
            Statement sta = conexion.createStatement();
            ResultSet resultado = sta.executeQuery("SELECT *from usuarios where usuario= '" + usuario + "'");
            resultado.last();
            if (resultado.getRow() == 0) {
                existe = false;
            } else {
                resultado = sta.executeQuery("SELECT *from usuarios where usuario= '" + usuario + "'");
                while (resultado.next()) {
                    this.User.setId_usuario(resultado.getInt(1));
                    this.User.setUsuario(resultado.getString(2));
                    this.User.setContraseña(resultado.getString(3));
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return existe;
    }

    //Encripta la contrasena en base 64
    private static String encriptar(String s) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(s.getBytes("utf-8"));
    }

    //Desencripta la contrasena en base64
    private static String desencriptar(String s) throws UnsupportedEncodingException {
        byte[] decode = Base64.getDecoder().decode(s.getBytes());
        return new String(decode, "utf-8");
    }
}
