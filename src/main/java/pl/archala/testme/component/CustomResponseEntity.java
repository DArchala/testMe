package pl.archala.testme.component;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class CustomResponseEntity {

    //USER
    public static final ResponseEntity<?> USER_DOES_NOT_EXIST = new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> USER_REGISTERED_CHECK_MAILBOX = new ResponseEntity<>("User registered, but still not active - check your mailbox.", HttpStatus.CREATED);
    public static final ResponseEntity<?> USERNAME_ALREADY_TAKEN = new ResponseEntity<>("This username is already taken.", HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> EMAIL_ALREADY_TAKEN = new ResponseEntity<>("This e-mail is already taken.", HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> ACCOUNT_ENABLED = new ResponseEntity<>("User account is now enable. You can log in.", HttpStatus.OK);

    //EXAM
    public static final ResponseEntity<?> EXAM_DOES_NOT_EXIST = new ResponseEntity<>("Exam does not exist", HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> NO_EXAMS_FOUND = new ResponseEntity<>("No exams found", HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> SAVING_EXAM_FAILED = new ResponseEntity<>("Saving exam failed", HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> EXAM_SAVED = new ResponseEntity<>("Exam saved", HttpStatus.OK);
    public static final ResponseEntity<?> EXAM_DELETED = new ResponseEntity<>("Exam deleted", HttpStatus.OK);

    //TOKEN
    public static final ResponseEntity<?> TOKEN_DOES_NOT_EXIST = new ResponseEntity<>("Token does not exist.", HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> TOKEN_HAS_NO_USER = new ResponseEntity<>("Token has no user.", HttpStatus.NOT_FOUND);


    //OTHER
    public static final ResponseEntity<?> UNDEFINED_ERROR = new ResponseEntity<>("Undefined error.", HttpStatus.INTERNAL_SERVER_ERROR);
}
