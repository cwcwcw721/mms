package org.yuxing.javaweb.filecounter.sevlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.yuxing.javaweb.filecounter.dao.FileDao;

/**
 * Servlet implementation class FileCounter
 */
@WebServlet("/FileCounter")
public class FileCounter extends HttpServlet {

	private static final long serialVersionUID = 1L;
	int count;
	private FileDao dao;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Set a cookie for the user, so that the counter does not increate
		// every time the user press refresh
		HttpSession session = request.getSession(true);
		// Set the session valid for 5 secs
		session.setMaxInactiveInterval(5);
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		if (session.isNew()) {
			count++;
		}
		try {
			out.println("This site has been accessed " + count + " times. The session token is " + generateSessionToken("tPu0KpZZKDVyezwMcQ", 1759, "2f20e2a553de52164af4794dd44aedd8"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		try {
			dao.save(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() throws ServletException {
		dao = new FileDao();
		try {
			count = dao.getCount();
		} catch (Exception e) {
			getServletContext().log("An exception occurred in FileCounter", e);
			throw new ServletException("An exception occurred in FileCounter"
					+ e.getMessage());
		}
	}
	
	private String generateSessionToken(String apiKey, int gameId, String userId)
	        throws NoSuchAlgorithmException, UnsupportedEncodingException {
	 
	    String timestamp = Long.toString((new Date().getTime()) / 1000);
	 
	    byte[] bytesOfMessage = (userId + timestamp + apiKey).getBytes("UTF-8");
	    MessageDigest md5 = MessageDigest.getInstance("MD5");
	    byte[] hash = md5.digest(bytesOfMessage);
	 
	    StringBuilder hexDigest = new StringBuilder();
	    for (int i = 0; i < hash.length; i++) {
	        if ((0xFF & hash[i]) < 0x10) {
	            hexDigest.append("0");
	        }
	        hexDigest.append(Integer.toHexString(0xFF & hash[i]));
	    }
	    return String.format("%d=%s=%s=%s", gameId, userId, timestamp, hexDigest.toString());
	}
}
