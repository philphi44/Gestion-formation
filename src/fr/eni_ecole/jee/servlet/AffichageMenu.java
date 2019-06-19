package fr.eni_ecole.jee.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class AffichageMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		out.println("<div id=\"menu\">");
		out.print("<a href=\"");
		out.print(request.getContextPath());
		out.print("\">Accueil</a><br />");
		out.print("<a href=\"");
		out.print(request.getContextPath());
		out.println("/ListeDesFormations\">Liste des formations</a><br />");
		out.print("<a href=\"");
		out.print(request.getContextPath());
		out.println("/animateur/accesAnimateur.html\">Accès animateur</a><br />");
		out.print("<a href=\"");
		out.print(request.getContextPath());
		out.println("/stagiaire/accesStagiaire.html\">Accès stagiaire</a><br />");
		out.println("<br />");
		out.println("<form method=\"get\" action=\"http://www.google.fr/search\">");
		out.println("<input type=\"hidden\" name=\"hl\" value=\"fr\" />");
		out.println("<input class=\"champtexte\" type=\"text\" name=\"q\" />");
		out.println("<input type=\"submit\" value=\"Rechercher\" />");
		out.println("</form>");
		out.println("</div>");	
	}
}
