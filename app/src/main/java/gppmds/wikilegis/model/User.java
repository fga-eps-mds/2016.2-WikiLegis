
package gppmds.wikilegis.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gppmds.wikilegis.exception.UserException;

public class User {
    private static final int MAX_LENGTH_FIRST_NAME = 30;
    private static final int MAX_LENGTH_LAST_NAME = 30;
    private static final int MAX_LENGTH_EMAIL = 150;
    private static final int MAX_LENGTH_PASSWORD = 10;
    private static final int MIN_LENGTH_PASSWORD = 6;

    public static final String FIRST_NAME_CANT_BE_EMPTY = "Inválido, o nome "
            + "não pode ser vazio.";
    public static final String FIRST_NAME_CANT_BE_HIGHER_THAN_30 = "Inválido, "
            + "o nome deve ter no máximo 30 caractéres";
    public static final String LAST_NAME_CANT_BE_EMPTY = "Inválido, o sobrenome "
            + "não pode ser vazio.";
    public static final String LAST_NAME_CANT_BE_HIGHER_THAN_30 = "Inválido, "
            + "o sobrenome deve ter no máximo 30 caractéres";
    public static final String EMAIL_CANT_BE_EMPTY_EMAIL = "Inválido, o email "
            + "não pode ser vazio.";
    public static final String EMAIL_CANT_BE_HIGHER_THAN_150 = "Inválido, o "
            + "email deve ter no máximo 150 caractéres";
    public static final String INVALID_EMAIL = "Ops, esse e-mail é inválido.";
    public static final String PASSWORD_CANT_BE_EMPTY = "Inválido, a senha não "
            + "pode ser vazia.";
    public static final String PASSWORD_CANT_BE_LESS_THAN_6 = "Inválido, a senha"
            + " deve conter no mínimo 6 caractéres.";
    public static final String PASSWORD_CANT_BE_HIGHER_THAN_10 = "Inválido, a senha"
            + " deve ter no máximo 10 caractéres";
    public static final String PASSWORD_ISNT_EQUALS = "As senhas digitadas sao diferentes";
    public static final String NAME_CONTAINS_SPECIAL_CHARACTERS = "O nome deve ter apenas letras.";
    public static final String LAST_NAME_CONTAINS_SPECIAL_CHARACTERS = "O sobrenome"
            + " deve ter apenas letras.";

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User(final String firstName, final String lastName, final String email,
                final String password, final String passwordConfimation) throws UserException {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPassword(password, passwordConfimation);
    }

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(final String firstName) throws UserException {
        if (stringIsNull(firstName)) {
            if (validateNameContainsOnlyLetters(firstName)) {
                if (validateStringLengthLessThanMax(firstName, MAX_LENGTH_FIRST_NAME)) {
                    this.firstName = firstName;
                } else {
                    throw new UserException(FIRST_NAME_CANT_BE_HIGHER_THAN_30);
                }
            } else {
                throw new  UserException(NAME_CONTAINS_SPECIAL_CHARACTERS);
            }
        } else {
            throw  new UserException(FIRST_NAME_CANT_BE_EMPTY);
        }
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(final String lastName) throws UserException {
        if (stringIsNull(lastName)) {
            if (validateNameContainsOnlyLetters(lastName)) {
                if (validateStringLengthLessThanMax(lastName, MAX_LENGTH_LAST_NAME)) {
                    this.lastName = lastName;
                } else {
                    throw new UserException(LAST_NAME_CANT_BE_HIGHER_THAN_30);
                }
            } else {
               throw new UserException(LAST_NAME_CONTAINS_SPECIAL_CHARACTERS);
            }
        } else {
            throw  new UserException(LAST_NAME_CANT_BE_EMPTY);
        }
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(final String email) throws UserException {
        if (stringIsNull(email)) {
            if (validateStringLengthLessThanMax(email, MAX_LENGTH_EMAIL)) {
                if (validateEmailFormat(email)) {
                        this.email = email;
                } else {
                    throw new UserException(INVALID_EMAIL);
                }
            } else {
                throw new UserException(EMAIL_CANT_BE_HIGHER_THAN_150);
            }
        } else {
            throw new UserException(EMAIL_CANT_BE_EMPTY_EMAIL);
        }
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(final String password,
                             final String passwordConfirmation) throws UserException {
        if (stringIsNull(password)) {
            if (validateStringLengthLessThanMax(password, MAX_LENGTH_PASSWORD)) {
                if (validateStringLengthMoreThanMin(password, MIN_LENGTH_PASSWORD)) {
                    if (passwordIsEqual(password, passwordConfirmation)) {
                        this.password = password;
                    } else {
                        throw new UserException(PASSWORD_ISNT_EQUALS);
                    }
                } else {
                    throw  new UserException(PASSWORD_CANT_BE_LESS_THAN_6);
                }
            } else {
                throw  new UserException(PASSWORD_CANT_BE_HIGHER_THAN_10);
            }
        } else {
            throw  new UserException(PASSWORD_CANT_BE_EMPTY);
        }
    }

    //Validation methods

    private boolean stringIsNull(final String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        return  true;
    }


    private boolean validateStringLengthLessThanMax(final String string, final int MAX) {
        if (string.length() > MAX) {
            return false;
        }
        return  true;
    }

    private boolean validateStringLengthMoreThanMin(final String string, final int MIN) {
        if (string.length() < MIN) {
            return false;
        }
        return  true;
    }


    private boolean passwordIsEqual(final String password, final String passwordConfimation) {

        if (password.equals(passwordConfimation)) {
            return true;
        }
        return  false;
    }

    private boolean validateEmailFormat(final String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    private boolean validateNameContainsOnlyLetters(final String name) {

        int countSpaces = 0;

        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ') {
                return false;
            } else if (name.charAt(i) == ' ') {
                countSpaces++;
            }
        }
        if (countSpaces != name.length()) {
            return true;
        }
        return false;
    }
}
