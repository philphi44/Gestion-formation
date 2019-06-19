package fr.eni_ecole.jee.servlet;

import java.io.*;
import java.sql.*;
import java.text.*;

import javax.servlet.*;
import javax.servlet.http.*;


 public class ListeDesFormations extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		processRequest(request, response);		
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
		
	}   	  	    
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		StringBuffer contenuReponse = new StringBuffer(); // Contenu de la réponse en cours de construction
		
		contenuReponse.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		contenuReponse.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		contenuReponse.append("<head>");
		contenuReponse.append("<meta content=\"text/html; charset=ISO-8859-1\" http-equiv=\"content-type\"/>");
		contenuReponse.append("<link media=\"all\" rel=\"stylesheet\" href=\"");
		contenuReponse.append(request.getContextPath());
		contenuReponse.append("/theme/basique/style.css\" type=\"text/css\"/>");
		contenuReponse.append("</head>");
		contenuReponse.append("<body>");
		contenuReponse.append("<div id=\"page\">");		
				
		contenuReponse.append("<div id=\"entete\">");
		contenuReponse.append("<h1>TP Web - Formations</h1>");
		contenuReponse.append("</div>");	
		
		PrintWriter out = response.getWriter();	// Corps de la réponse HTTP
		out.print(contenuReponse);
		
		
		// Inclusion du résultat de AffichageMenu
		RequestDispatcher dispatcher = getServletContext().getNamedDispatcher("AffichageMenu");
		dispatcher.include(request, response);

		contenuReponse = new StringBuffer();
		contenuReponse.append("<div id=\"contenu\">");
		out.print(contenuReponse);	
		
		Connection  cnx=null;
		Statement rqt=null;
		ResultSet rs=null;
		String driver=getInitParameter("dbDriver");
		String urlConnexion = getInitParameter("dbUrlConnexion");
		String user = getInitParameter("dbUser");
		String password = getInitParameter("dbPassword");
		
		try{
			try{
				// Connexion à la base
				Class.forName(driver);
				//DriverManager.registerDriver(new SQLServerDriver());
				cnx = DriverManager.getConnection(urlConnexion, user, password);
				
				// requetage
				rqt = cnx.createStatement();
				rs = rqt.executeQuery("select * from formations");
						
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				
				// Parcourir le result set				
				while (rs.next()){
					contenuReponse.append("<div class=\"formation\">");
					contenuReponse.append("<h2>");
					contenuReponse.append(rs.getString("libelle"));
					contenuReponse.append("</h2>");
					contenuReponse.append("<p> Du ");
					contenuReponse.append(df.format(rs.getDate("debut")));
					contenuReponse.append(" au ");
					contenuReponse.append(df.format(rs.getDate("fin")));
					contenuReponse.append("</p>");
					contenuReponse.append("<p>");
					contenuReponse.append(rs.getString("description"));
					
					contenuReponse.append("</p>");
					contenuReponse.append("</div>");
					contenuReponse.append("<br />");
				}				
				
			} finally {
				if (rs!=null) rs.close();
				if (rqt!=null) rqt.close();
				if (cnx!=null) cnx.close();
			}
			
		}catch (Exception e){
			System.err.println("Erreur : ");
			e.printStackTrace();
			
			contenuReponse = new StringBuffer();
			contenuReponse.append("<div class=\"formation\">");
			contenuReponse.append("<h2>Erreur</h2>");
			contenuReponse.append("<p>Une erreur est survenue : ");
			contenuReponse.append(e);
			contenuReponse.append("</p>");
			
			if (e.getMessage()!=null) {
				contenuReponse.append("<br />");
				contenuReponse.append("<p>");
				contenuReponse.append(e.getMessage());
				contenuReponse.append("</p>");
			}
				
			contenuReponse.append("</div>");
		}

		contenuReponse.append("</div>");
		contenuReponse.append("<div id=\"pieddepage\">TP <a href=\"http://www.eni-ecole.fr\">ENI Ecole</a></div>");
		contenuReponse.append("</div>");
		contenuReponse.append("</body>");
		contenuReponse.append("</html>");	
		
		
			
		out.print(contenuReponse);
		
	}
}