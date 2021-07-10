package com.pragmatic.todoList.mysql.services;

import java.io.File;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.pragmatic.todoList.mysql.entities.UserEntity;

@Service
public class MailingService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SpringTemplateEngine thymeleafTemplateEngine;

	public void sendEmail(UserEntity user, String body, String subject) throws MessagingException {

		SimpleMailMessage message = new SimpleMailMessage();

		String to = user.getEmail();
		String from = "youcode.absence@gmail.com";

		message.setFrom(from);
		message.setTo(to);
		message.setText(body);
		message.setSubject(subject);

		mailSender.send(message);

		System.out.println("Message sent");
	}

	public void sendEmailWithAttachement(UserEntity user, String body, String subject, String attachement)
			throws MessagingException {

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		String to = user.getEmail();
		String from = "youcode.absence@gmail.com";

		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

		message.setFrom(from);
		message.setTo(to);
		message.setText(body, true);
		message.setSubject(subject);

		FileSystemResource fileResource = new FileSystemResource(new File(attachement));

		message.addAttachment(fileResource.getFilename(), fileResource);

		mailSender.send(mimeMessage);

		System.out.println("Message sent");
	}

	public void sendMessageUsingThymeleafTemplate(UserEntity user, String subject, String attachement,
			Map<String, Object> templateModel) throws MessagingException {
		Context thymeleafContext = new Context();

		thymeleafContext.setVariables(templateModel);

		String htmlBody = thymeleafTemplateEngine.process("notification-email.html", thymeleafContext);
		sendEmailWithAttachement(user, htmlBody, subject, attachement);
	}


}
