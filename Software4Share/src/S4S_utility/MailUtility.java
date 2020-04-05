package S4S_utility;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.*;

import S4S_Database.UserDAO;

public class MailUtility
{	
	  public static void sendMail (String reciver,boolean admin)throws MessagingException, SQLException
		  {
		    // Creazione di una mail session
		    Properties props = new Properties();
		    props.put("mail.smtp.user","username"); 
		    props.put("mail.smtp.host", "smtp.gmail.com"); 
		    props.put("mail.smtp.port", "25"); 
		    //props.put("mail.debug", "true"); 
		    props.put("mail.smtp.auth", "true"); 
		    props.put("mail.smtp.starttls.enable","true"); 
		    props.put("mail.smtp.EnableSSL.enable","true");
		    props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
		    props.setProperty("mail.smtp.socketFactory.fallback", "false");   
		    props.setProperty("mail.smtp.port", "465");   
		    props.setProperty("mail.smtp.socketFactory.port", "465"); 
		    Session session = Session.getDefaultInstance(props, 
		    	    new javax.mail.Authenticator(){
		    	        protected PasswordAuthentication getPasswordAuthentication() {
		    	            return new PasswordAuthentication(
		    	                mailSender, senderPass);
		    	        }
		    	});

		    // Creazione del messaggio da inviare
		    String blockCode=generate();
		    MimeMessage message = new MimeMessage(session);
		    message.setSubject(mailObject);
		    message.setText(mailMessage +"  "+blockCode);
		    
		    // Aggiunta degli indirizzi del mittente e del destinatario
		    
		    InternetAddress fromAddress = new InternetAddress(mailSender);
		    InternetAddress toAddress = new InternetAddress(reciver);
		    message.setFrom(fromAddress);
		    message.setRecipient(Message.RecipientType.TO, toAddress);

		    // Invio del messaggio
		    Transport.send(message);
		    UserDAO.setBlockCode(reciver, blockCode, admin);
		  }
	  
	  static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    static Random rnd = new Random(System.currentTimeMillis());
	    static private final int LENGHT = 8;
	 
	    public static String generate() {
	        StringBuilder sb = new StringBuilder(LENGHT);
	        for (int i = 0; i < LENGHT; i++) {
	            sb.append(ALPHABET.charAt(rnd.nextInt(ALPHABET.length())));
	        }
	        return sb.toString();
	    }
	    
	    public static void sendMail (String reciver,String objet,String msg)throws MessagingException
		  {
		    // Creazione di una mail session
		    Properties props = new Properties();
		    props.put("mail.smtp.user","username"); 
		    props.put("mail.smtp.host", "smtp.gmail.com"); 
		    props.put("mail.smtp.port", "25"); 
		    props.put("mail.debug", "true"); 
		    props.put("mail.smtp.auth", "true"); 
		    props.put("mail.smtp.starttls.enable","true"); 
		    props.put("mail.smtp.EnableSSL.enable","true");
		    props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
		    props.setProperty("mail.smtp.socketFactory.fallback", "false");   
		    props.setProperty("mail.smtp.port", "465");   
		    props.setProperty("mail.smtp.socketFactory.port", "465"); 
		    Session session = Session.getDefaultInstance(props, 
		    	    new javax.mail.Authenticator(){
		    	        protected PasswordAuthentication getPasswordAuthentication() {
		    	            return new PasswordAuthentication(
		    	                mailSender, senderPass);// Specify the Username and the PassWord
		    	        }
		    	});

		    // Creazione del messaggio da inviare
		    MimeMessage message = new MimeMessage(session);
		    message.setSubject(objet);
		    message.setText(msg);

		    // Aggiunta degli indirizzi del mittente e del destinatario
		    InternetAddress fromAddress = new InternetAddress(mailSender);
		    InternetAddress toAddress = new InternetAddress(reciver);
		    message.setFrom(fromAddress);
		    message.setRecipient(Message.RecipientType.TO, toAddress);

		    // Invio del messaggio
		    Transport.send(message);
		  }
	    public static void sendMailFrmoUser(String sender,String objet,String msg)throws MessagingException
		  {
		    // Creazione di una mail session
		    Properties props = new Properties();
		    props.put("mail.smtp.user","username"); 
		    props.put("mail.smtp.host", "smtp.gmail.com"); 
		    props.put("mail.smtp.port", "25"); 
		    props.put("mail.debug", "true"); 
		    props.put("mail.smtp.auth", "true"); 
		    props.put("mail.smtp.starttls.enable","true"); 
		    props.put("mail.smtp.EnableSSL.enable","true");
		    props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
		    props.setProperty("mail.smtp.socketFactory.fallback", "false");   
		    props.setProperty("mail.smtp.port", "465");   
		    props.setProperty("mail.smtp.socketFactory.port", "465"); 
		    Session session = Session.getDefaultInstance(props, 
		    	    new javax.mail.Authenticator(){
		    	        protected PasswordAuthentication getPasswordAuthentication() {
		    	            return new PasswordAuthentication(
		    	                mailSender, senderPass);// Specify the Username and the PassWord
		    	        }
		    	});

		    // Creazione del messaggio da inviare
		    MimeMessage message = new MimeMessage(session);
		    message.setSubject(sender + " - " + objet);
		    message.setText(msg);

		    // Aggiunta degli indirizzi del mittente e del destinatario
		    InternetAddress fromAddress = new InternetAddress(mailSender);
		    InternetAddress toAddress = new InternetAddress(mailSender);
		    message.setFrom(fromAddress);
		    message.setRecipient(Message.RecipientType.TO, toAddress);

		    // Invio del messaggio
		    Transport.send(message);
		  }
	    public static void sendMailCode (String reciver,boolean admin)throws MessagingException, SQLException
		  {
		    // Creazione di una mail session
		    Properties props = new Properties();
		    props.put("mail.smtp.user","username"); 
		    props.put("mail.smtp.host", "smtp.gmail.com"); 
		    props.put("mail.smtp.port", "25"); 
		    //props.put("mail.debug", "true"); 
		    props.put("mail.smtp.auth", "true"); 
		    props.put("mail.smtp.starttls.enable","true"); 
		    props.put("mail.smtp.EnableSSL.enable","true");
		    props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
		    props.setProperty("mail.smtp.socketFactory.fallback", "false");   
		    props.setProperty("mail.smtp.port", "465");   
		    props.setProperty("mail.smtp.socketFactory.port", "465"); 
		    Session session = Session.getDefaultInstance(props, 
		    	    new javax.mail.Authenticator(){
		    	        protected PasswordAuthentication getPasswordAuthentication() {
		    	            return new PasswordAuthentication(
		    	                mailSender, senderPass);
		    	        }
		    	});

		    // Creazione del messaggio da inviare
		    String blockCode=generate();
		    MimeMessage message = new MimeMessage(session);
		    message.setSubject("Codice di verifica");
		    String msgText="Inserire il codice sottostante per poter verificare che sei tu l'utente che ha bisogno di cambiare la password in caso tu non abbia cliccato su password dimenticata ignora questa mail\nIl codice di sblocco è :"+blockCode+"\nCordiali saluti dal team Software 4 Share";
		    message.setText(msgText);
		    
		    // Aggiunta degli indirizzi del mittente e del destinatario
		    
		    InternetAddress fromAddress = new InternetAddress(mailSender);
		    InternetAddress toAddress = new InternetAddress(reciver);
		    message.setFrom(fromAddress);
		    message.setRecipient(Message.RecipientType.TO, toAddress);

		    // Invio del messaggio
		    Transport.send(message);
		    UserDAO.setBlockCode(reciver, blockCode, admin);
		  }
	  //variabili
	  private final static String mailObject  ="Avvertimento accesso account personale Software4Share";
	  private final static String mailSender  ="softwareforshare.esp@gmail.com";
	  private final static String senderPass  ="SonounaPassword";
	  private final static String mailMessage ="Sembrerebbe che qualcuno stia tentando di accedere al tuo profilo personale per questo motivo l'account è stato temporaneamente bloccato. Per poter accedere nuovamente dovra inserire il seguente codice nella schermata di login del sito.\n\nil codice è il seguente : ";
}
