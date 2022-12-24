package de.tekup.studentsabsence.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.*;
import sibModel.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;


@Data
@AllArgsConstructor
public class SendInBlue {

    private String email;
    private String name;
    private String subject;
    private String htmlContent;

    public void sendEmail(){
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        // Configure API key authorization: api-key
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey("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");

        try {
            TransactionalEmailsApi api = new TransactionalEmailsApi();
            SendSmtpEmailSender sender = new SendSmtpEmailSender();
            sender.setEmail("noreply@tek-up.tn");
            sender.setName("Tek-Up");
            List<SendSmtpEmailTo> toList = new ArrayList<SendSmtpEmailTo>();
            SendSmtpEmailTo to = new SendSmtpEmailTo();
            to.setEmail(email);
            to.setName(name);
            toList.add(to);

            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
            sendSmtpEmail.setSender(sender);
            sendSmtpEmail.setTo(toList);

            sendSmtpEmail.setHtmlContent(htmlContent);
            sendSmtpEmail.setSubject(subject);

            CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println("Exception occurred:- " + e.getMessage());
        }
    }
}
