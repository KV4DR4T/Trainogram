package com.example.trainogram.email;

import com.example.trainogram.dto.ChangePasswordRequestDto;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import com.twilio.rest.verify.v2.service.VerificationCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class SendGridEmailService {
    @Value("${account.sid}")
    public static String accountSid;
    @Value("${auth.token}")
    public static String authToken;
    @Value("${path.service.sid}")
    public static String pathServiceSid;
    @Value("${template.id}")
    public static String templateId;
    @Value("${from}")
    public static String from;
    @Value("${from.name}")
    public static String fromName;

//    public static final String ACCOUNT_SID = "AC26811b3a81c2bf3fff076a53169abeb1";
//    public static final String AUTH_TOKEN = "2c747eaeb1c18106edef983b3e56d359";
//    public static final String PATH_SERVICE_SID = "VA7de7c72b9e7be3d49aa5e53c67a11084";
//    public static final String TEMPLATE_ID = "d-733d88eaf846440ebb7b6fd915eda2dc";
//    public static final String FROM = "kondralex222@gmail.com";
//    public static final String FROM_NAME = "Train-O-gram";
    public void sendMail(String to) {
        Twilio.init(accountSid, authToken);
        VerificationCreator creator = Verification.creator(pathServiceSid, to, "email");
        Map<String, Object> source = new HashMap<>();
        source.put("template_id", templateId);
        source.put("from", from);
        source.put("from_name", fromName);
        creator.setChannelConfiguration(source);
        /*Verification verification =*/ creator.create();

    }

    public boolean validate(ChangePasswordRequestDto changePasswordRequestDto, String email) {
        Twilio.init(accountSid, authToken);
        VerificationCheck verificationCheck = VerificationCheck.creator(
                        pathServiceSid)
                .setTo(email)
                .setCode(changePasswordRequestDto.getCode())
                .create();
        return verificationCheck.getValid();
    }
}
