package it.unisa.control;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Servlet implementation class RegistraziomeServlet
 */
@WebServlet("/Registrazione")
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserDao dao = new UserDao();
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
		String dataNascita = request.getParameter("nascita");
		String username = request.getParameter("us");
		String pwd = request.getParameter("pw");

        String[] parti = dataNascita.split("-");
        dataNascita = parti[2] + "-" + parti[1] + "-" + parti[0];
		
		try {
			
			UserBean user = new UserBean();
			user.setNome(nome);
			user.setCognome(cognome);
			user.setEmail(email);
			user.setDataDiNascita(Date.valueOf(dataNascita));
			user.setUsername(username);
			user.setPassword(encrypt(pwd));
			user.setAmministratore(false);
			user.setCap(null);
			user.setIndirizzo(null);
			user.setCartaDiCredito(null);
			dao.doSave(user);
			
		}catch(SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
				
		response.sendRedirect(request.getContextPath() + "/Home.jsp");

	}
	
	public static String encrypt(String plaintext) {
	      try {
	         MessageDigest md = MessageDigest.getInstance("SHA-256");
	         byte[] hashedBytes = md.digest(plaintext.getBytes());
	         StringBuilder sb = new StringBuilder();
	         for (byte b : hashedBytes) {
	            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
	         }
	            return sb.toString();
	      } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	      }
	      return null;
	   }

}
