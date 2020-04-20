package cl.gob.scj.sgdp.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

import cl.gob.scj.sgdp.model.UsuarioRol;
import cl.gob.scj.sgdp.service.UsuarioRolService;

public class LdapAuthoritiesPopulatorImpl implements LdapAuthoritiesPopulator {

	private static final Logger log = Logger.getLogger(LdapAuthoritiesPopulatorImpl.class);
	
	@Autowired
	private UsuarioRolService usuarioRolService;
	
	@Override
	public Collection<? extends GrantedAuthority> getGrantedAuthorities(
			DirContextOperations userData, String userName) {
		// TODO Auto-generated method stub
		log.debug(userName);
		Collection<GrantedAuthority> rolesConfig = new HashSet<GrantedAuthority>();
		List<UsuarioRol> usuarioRoles = usuarioRolService.getUsuarioRolesPorIdUsuario(userName);        
		for (UsuarioRol usuarioRol: usuarioRoles) {
			rolesConfig.add(new SimpleGrantedAuthority(usuarioRol.getRol().getNombreRol()));
		}
		return rolesConfig;
	}

}
