package uk.ac.kcl.cch.diamm.model;

import org.hibernate.HibernateException;
import uk.ac.kcl.cch.diamm.dao.DAOFactory;
import uk.ac.kcl.cch.diamm.dao.DiammUserDAO;
import uk.ac.kcl.cch.diamm.dao.DiammUserDTO;
import uk.ac.kcl.cch.diamm.dao.UserRoleDAO;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.UserRole.Role;
import uk.ac.kcl.cch.util.Email;
import uk.ac.kcl.cch.util.Password;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

public class UserAccess {
    //Constants used by several methods
    private static final int passwordLength = 6;
    private static final String EMAIL_FROM = "cch-sysadmin@kcl.ac.uk";
    private static final String EMAIL_SUBJECT_REG = "Your DIAMM Registration";
    private static final String EMAIL_SUBJECT_RET = "Retrieval of your DIAMM username and password";
    private static final String EMAIL_MESSAGE = "Dear %d,\n\n" +
            "%s" +
            "\n\n" +
            "Username: %u\n" +
            "Password: %p\n\nwww.diamm.ac.uk";

    private static DAOFactory factory = DAOFactory.getFactory();


    public static void commitUser(DiammUser user) {
        final String MSG_ALREADY_REG = "already registered";


        try {
            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().update(user);

            //Persist the user
            HibernateUtil.commitTransaction();
        } catch (HibernateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }


    public static Map<String, String> registerNewUser(DiammUserDTO diammUserDTO) {
        final String MSG_ALREADY_REG = "already registered";

        //Check registration fields pass basic validation
        DiammUser newUser = new DiammUser();
        newUser.setUsername(diammUserDTO.getUsername());
        newUser.setEmail(diammUserDTO.getEmail());
        newUser.setDisplayName(diammUserDTO.getDisplayName());
        newUser.setAffiliation(diammUserDTO.getAffiliation());

        //If registration fields pass basic validation, proceed
        Map<String, String> errorMessages = newUser.getErrorMessages();
        if (diammUserDTO.getAgree1()==null||diammUserDTO.getAgree2()==null||diammUserDTO.getAgree3()==null||diammUserDTO.getAgree4()==null){
                errorMessages.put(DiammUser.MAP_KEY_TERMS, DiammUser.MSG_TERMS);
        }
        if (errorMessages.isEmpty()) {
            DiammUserDAO diammUserDao = factory.getDiammUserDAO();
            diammUserDao.beginTransaction();

            //Check whether username is already registered
            DiammUser existingUser = diammUserDao.findByUsername(newUser.getUsername());
            if (existingUser != null) {
                errorMessages.put(DiammUser.MAP_KEY_USERNAME, MSG_ALREADY_REG);
            }

            //Check whether email is already registered
            existingUser = diammUserDao.findByEmail(newUser.getEmail());
            if (existingUser != null) {
                errorMessages.put(DiammUser.MAP_KEY_EMAIL, MSG_ALREADY_REG);
            }

            if (errorMessages.isEmpty()) {
                //Generate password and register the new user
                String clearPassword = Password.generate(passwordLength);
                newUser.setPassword(Password.encrypt(clearPassword));
                diammUserDao.save(newUser);

                //Add role to the DiammUser
                UserRoleDAO userRoleDao = factory.getUserRoleDAO();
                UserRole userRole = new UserRole();
                userRole.setUsername(newUser.getUsername());
                userRole.setRolename(Role.REGISTERED.toString());
                userRoleDao.save(userRole);

                //Send email message to newly registered user
                String emailMessage = EMAIL_MESSAGE.replace("%d", newUser.getDisplayName());
                emailMessage = emailMessage.replace("%s", EMAIL_SUBJECT_REG);
                emailMessage = emailMessage.replace("%u", newUser.getUsername());
                emailMessage = emailMessage.replace("%p", clearPassword);

                //Persist the newly registered user
                diammUserDao.commitTransaction();

                try {
                    Email.send(diammUserDTO.getEmail(), EMAIL_FROM, EMAIL_SUBJECT_REG, emailMessage, false);
                } catch (MessagingException e) {
                    //If problem with further problem with email, add entry to error messages
                    e.printStackTrace();
                    errorMessages.put(DiammUser.MAP_KEY_EMAIL, DiammUser.MSG_INVALID);
                }
            }
        }
        return errorMessages;
    }

    public static Map<String, String> retrievePassword(String email) {
        DiammUser anyUser = new DiammUser();
        anyUser.setEmail(email);
        Map<String, String> errorMessages = anyUser.getErrorMessages();

        //If email is valid format, check if it is registered
        if (errorMessages.isEmpty()) {
            DiammUserDAO diammUserDao = factory.getDiammUserDAO();
            diammUserDao.beginTransaction();
            DiammUser diammUser = diammUserDao.findByEmail(email);

            //If email is not registered, add entry to error messages
            if (diammUser == null) {
                errorMessages.put(DiammUser.MAP_KEY_EMAIL, DiammUser.MSG_INVALID);
            }
            //If email is registered, send user details via email
            else {
                //Generate new password and assign to user
                String clearPassword = Password.generate(passwordLength);
                diammUser.setPassword(Password.encrypt(clearPassword));
                diammUserDao.save(diammUser);

                String emailMessage = EMAIL_MESSAGE.replace("%d", diammUser.getDisplayName());
                emailMessage = emailMessage.replace("%s", EMAIL_SUBJECT_RET);
                emailMessage = emailMessage.replace("%u", diammUser.getUsername());
                emailMessage = emailMessage.replace("%p", clearPassword);

                try {
                    Email.send(email, EMAIL_FROM, EMAIL_SUBJECT_RET, emailMessage, false);
                    diammUserDao.commitTransaction();
                } catch (MessagingException e) {
                    //If problem with further problem with email, add entry to error messages
                    e.printStackTrace();
                    errorMessages.put(DiammUser.MAP_KEY_EMAIL, DiammUser.MSG_INVALID);
                    diammUserDao.rollbackTransaction();
                }
            }
        }
        return errorMessages;
    }

    public static Map<String, String> changePassword(DiammUserDTO diammUserDTO) {
        final String MAP_KEY_NEW_PASSWORD1 = "newPassword1";
        final String MAP_KEY_NEW_PASSWORD2 = "newPassword2";
        final String MSG_INCORRECT_PASSWORD = "incorrect password";
        final String MSG_PASSWORD_MISMATCH = "these do not match";
        final String MSG_PASSWORD_UNCHANGED = "does not differ from the existing password";

        Map<String, String> errorMessages = new HashMap<String, String>();

        //Retrieve the user details
        DiammUserDAO diammUserDao = factory.getDiammUserDAO();
        diammUserDao.beginTransaction();
        DiammUser diammUser = diammUserDao.findByUsername(diammUserDTO.getUsername());

        //Check whether the current password has a value
        if (diammUserDTO.getPassword().length() == 0) {
            errorMessages.put(DiammUser.MAP_KEY_PASSWORD, DiammUser.MSG_REQUIRED);
        }

        //Check whether the current password is correct
        else if (!diammUser.getPassword().equals(Password.encrypt(diammUserDTO.getPassword()))) {
            errorMessages.put(DiammUser.MAP_KEY_PASSWORD, MSG_INCORRECT_PASSWORD);
        }

        //Check whether the two entries for the new password are equal
        else if (!diammUserDTO.getNewPassword1().equals(diammUserDTO.getNewPassword2())) {
            errorMessages.put(MAP_KEY_NEW_PASSWORD1, MSG_PASSWORD_MISMATCH);
            errorMessages.put(MAP_KEY_NEW_PASSWORD2, MSG_PASSWORD_MISMATCH);
        }

        //Check whether the new password differs from the existing password
        else if (diammUser.getPassword().equals(diammUserDTO.getNewPassword1())) {
            errorMessages.put(MAP_KEY_NEW_PASSWORD1, MSG_PASSWORD_UNCHANGED);
            errorMessages.put(MAP_KEY_NEW_PASSWORD2, MSG_PASSWORD_UNCHANGED);
        }

        //Check whether the new password satisfies any format criteria.
        //If valid, then save it.
        else {
            diammUser.validateClearPassword(diammUserDTO.getNewPassword1());
            errorMessages = diammUser.getErrorMessages();
            if (!errorMessages.isEmpty()) {
                String msg = errorMessages.get(DiammUser.MAP_KEY_PASSWORD);
                errorMessages.clear();
                errorMessages.put(MAP_KEY_NEW_PASSWORD1, msg);
                errorMessages.put(MAP_KEY_NEW_PASSWORD2, msg);
            } else {
                diammUser.setPassword(Password.encrypt(diammUserDTO.getNewPassword1()));
                diammUserDao.save(diammUser);
            }
        }

        diammUserDao.commitTransaction();
        return errorMessages;
    }
}
