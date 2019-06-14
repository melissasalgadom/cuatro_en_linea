/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuatro_en_linea.controlador;

import com.cuatro_en_linea.controlador.util.JsfUtil;
import com.cuatro_en_linea.modelo.Usuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author melis
 */
@Named(value = "loginBeanCuatroEnLinea")
@ViewScoped
public class loginBeanCuatroEnLinea implements Serializable {
    private String tipoUsuario;
    private Usuario usuario;
    @EJB   
    private UsuarioFacade usuarioFacade;    

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    

    public UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public void setUsuarioFacade(UsuarioFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    
    public loginBeanCuatroEnLinea() {
    }
    @PostConstruct
    private void inicializar()
    {
        usuario= new Usuario();
    }
    
    
     public String ingresar()
    {  
        Usuario usuarioEncontrado=usuarioFacade.find(usuario.getCorreo());
        if(usuarioEncontrado != null)
        {
            if(usuario.getContrasena().compareTo(usuarioEncontrado.getContrasena())==0)
            {
                if("administrador".equals(tipoUsuario)){
                    return "pagadmin";
                }
                return "jugar";
            }
            JsfUtil.addErrorMessage("Contraseña errada");
        }
        else
        {
            JsfUtil.addErrorMessage("El correo ingresado no existe");
        }
        return null;
}
    
}
