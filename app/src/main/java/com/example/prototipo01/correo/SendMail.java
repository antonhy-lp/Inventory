package com.example.prototipo01.correo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail extends AsyncTask<Void,Void,Void> {

    private Context context;
    private Session session;

    private String email;
    private String subject;
    private String message;

    private ProgressDialog progressDialog;

    public SendMail(Context context, String email, String subject, String message){
        this.context=context;
        this.email=email;
        this.subject=subject;
        this.message=message;
}
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=progressDialog.show(context,"Sending message","Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        Toast.makeText(context,"Message Sent",Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... params) {

        Properties props=new Properties();
//////PARA GMAIL
       props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port","465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.port","465");

//// //PARA GMAIL con imap
//        props.put("mail.imap.host","smtp.gmail.com");
//        props.put("mail.imap.socketFactory.port","465");
//        props.put("mail.imap.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//        props.put("mail.imap.auth","true");
//        props.put("mail.imap.port","465");

        //para hotmail con smtp
//        props.put("mail.smtp.host","smtp.live.com");
        // properties.put("mail.smtp.socketFactory.port","587");
        //  properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.auth","true");
//        props.put("mail.smtp.starttls.enable","true");

//////para hotmail con imap
//        props.put("mail.imap.host","imap.live.com");
//        // properties.put("mail.smtp.socketFactory.port","587");
//        //  properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//        props.put("mail.imap.auth","true");
//        props.put("mail.imap.starttls.enable","true");


        session=Session.getDefaultInstance(props,new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Config.EMAIL,Config.PASSWORD);
            }
        });

        try {
            MimeMessage mm=new MimeMessage(session);

            mm.setFrom(new InternetAddress(Config.EMAIL));
            mm.addRecipient(Message.RecipientType.TO,new InternetAddress(email));
            mm.setSubject(subject);
            mm.setText(message);

            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}