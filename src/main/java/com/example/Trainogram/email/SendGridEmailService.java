package com.example.Trainogram.email;

import com.example.Trainogram.dto.ChangePasswordRequestDto;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import com.twilio.rest.verify.v2.service.VerificationCreator;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class SendGridEmailService {
    public static final String ACCOUNT_SID = "AC26811b3a81c2bf3fff076a53169abeb1";
    public static final String AUTH_TOKEN = "2c747eaeb1c18106edef983b3e56d359";

    public void sendMail(String to) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        VerificationCreator creator = Verification.creator("VA7de7c72b9e7be3d49aa5e53c67a11084", to, "email");
        creator.setChannelConfiguration(new HashMap<String, Object>() {{
                    put("template_id", "d-733d88eaf846440ebb7b6fd915eda2dc");
                    put("from", "kondralex222@gmail.com");
                    put("from_name", "Train-O-gram");
                }});
        Verification verification = creator.create();

    }

    public boolean validate(ChangePasswordRequestDto changePasswordRequestDto, String email){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        VerificationCheck verificationCheck = VerificationCheck.creator(
                        "VA7de7c72b9e7be3d49aa5e53c67a11084")
                .setTo(email)
                .setCode(changePasswordRequestDto.getCode())
                .create();
        return verificationCheck.getValid();
    }
}
