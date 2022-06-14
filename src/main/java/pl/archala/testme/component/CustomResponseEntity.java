package pl.archala.testme.component;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class CustomResponseEntity {

    private CustomResponseEntity() {
    }

    //USER
    public static final ResponseEntity<?> USER_DOES_NOT_EXIST = new ResponseEntity<>("User does not exist.", HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> USER_REGISTERED_CHECK_MAILBOX = new ResponseEntity<>("User registered, but still not active - check your mailbox.", HttpStatus.CREATED);
    public static final ResponseEntity<?> USERNAME_ALREADY_TAKEN = new ResponseEntity<>("This username is already taken.", HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> EMAIL_ALREADY_TAKEN = new ResponseEntity<>("This e-mail is already taken.", HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> ACCOUNT_ENABLED = new ResponseEntity<>("User account is now enable. You can log in.", HttpStatus.OK);
    public static final ResponseEntity<?> USER_DELETED = new ResponseEntity<>("User deleted.", HttpStatus.OK);
    public static final ResponseEntity<?> DELETING_LAST_ADMIN_FORBIDDEN = new ResponseEntity<>("Deleting last admin is forbidden.", HttpStatus.FORBIDDEN);
    public static final ResponseEntity<?> USER_SAVED = new ResponseEntity<>("User saved.", HttpStatus.OK);
    public static final ResponseEntity<?> PASSWORD_DOES_NOT_MATCH = new ResponseEntity<>("The password and password provided for the account do not match", HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> USERNAME_DO_NOT_MATCH_WITH_EMAIL = new ResponseEntity<>("Username do not match with user email", HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> NEW_PASSWORD_CANNOT_BE_EQUAL_TO_OLD_PASSWORD = new ResponseEntity<>("New password cannot be equal to old password.", HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> USERNAME_AND_EMAIL_CANNOT_BE_EQUAL = new ResponseEntity<>("Username and email cannot be equal.", HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> PASSWORD_CANNOT_BE_EQUAL_TO_USERNAME = new ResponseEntity<>("Password cannot be equal to username.", HttpStatus.BAD_REQUEST);

    public static final ResponseEntity<?> PASSWORD_CANNOT_BE_EQUAL_TO_EMAIL = new ResponseEntity<>("Password cannot be equal to e-mail.", HttpStatus.BAD_REQUEST);

    //EXAM
    public static final ResponseEntity<?> EXAM_DOES_NOT_EXIST = new ResponseEntity<>("Exam does not exist.", HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> NO_EXAMS_FOUND = new ResponseEntity<>("No exams found.", HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> SAVING_EXAM_FAILED = new ResponseEntity<>("Saving exam failed.", HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> EXAM_SAVED = new ResponseEntity<>("Exam saved.", HttpStatus.OK);
    public static final ResponseEntity<?> EXAM_DELETED = new ResponseEntity<>("Exam deleted", HttpStatus.OK);
    public static final ResponseEntity<?> EXAM_NAME_ALREADY_TAKEN = new ResponseEntity<>("This exam name is already taken.", HttpStatus.BAD_REQUEST);

    public static final ResponseEntity<?> EXAM_ANY_QUESTION_DOES_NOT_CONTAIN_ANY_CORRECT_ANSWER = new ResponseEntity<>("Number of correct answer in any question cannot be less than 1.", HttpStatus.BAD_REQUEST);

    //TOKEN
    public static final ResponseEntity<?> TOKEN_DOES_NOT_EXIST = new ResponseEntity<>("Token does not exist.", HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> TOKEN_HAS_NO_USER = new ResponseEntity<>("Token has no user.", HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> TOKEN_HAS_EXPIRED = new ResponseEntity<>("Your token has expired.", HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> PASSWORD_RESET_LINK_WAS_SENT_CHECK_MAILBOX = new ResponseEntity<>("Password reset link was sent - check mailbox.", HttpStatus.BAD_REQUEST);

    public static final ResponseEntity<?> PASSWORD_RESETTING_FOR_DISABLED_USER_IS_FORBIDDEN = new ResponseEntity<>("Password resetting for disabled user is forbidden.", HttpStatus.FORBIDDEN);

    //OTHER

    public static final ResponseEntity<?> UNDEFINED_ERROR = new ResponseEntity<>("Undefined error.", HttpStatus.INTERNAL_SERVER_ERROR);
}
