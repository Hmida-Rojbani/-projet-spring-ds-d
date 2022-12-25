package de.tekup.studentsabsence.services.impl;

import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.entities.Subject;
import de.tekup.studentsabsence.services.MailService;
import lombok.AllArgsConstructor;;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {
    private JavaMailSender javaMailSender;

    @Override
    public void sendEliminationEmail(Student student, Subject subject) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("bila5missi@gmail.com");
            mailMessage.setTo(student.getEmail());
            mailMessage.setText("bonjour " + student.getFirstName() + " " + student.getLastName() + ". " +
                    "Nous vous informos que vous etes eliminé de la matière: " + subject.getName()+ " " +
                    "à cause de vos abscences successives ...");
            mailMessage.setSubject("[elimination de la matiere: " + subject.getName() + " ]");
            javaMailSender.send(mailMessage);
            System.out.println("mail sent successfully");
        }   catch (Exception e){
            System.out.println("something went wrong ...");
        }
    }

}
