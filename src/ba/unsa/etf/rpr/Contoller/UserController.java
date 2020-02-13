package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.User.Administrator;
import ba.unsa.etf.rpr.User.User;
import ba.unsa.etf.rpr.User.UsersModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;


public class UserController {
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldEmail;
    public TextField fldUsername;
    public ListView<User> listKorisnici;
    public PasswordField fldPassword;
    public PasswordField fldPasswordRepeat;
    public CheckBox cbAdmin;

    private UsersModel model;

    public UserController(UsersModel model) { this.model = model; }

    @FXML
    public void initialize() {
        listKorisnici.setItems(model.getUsers());
        listKorisnici.getSelectionModel().selectedItemProperty().addListener((obs, oldUser, newUser) -> {
            model.setCurrentUser(newUser);
            listKorisnici.refresh();
         });

        model.currentUserProperty().addListener((obs, oldUser, newUser) -> {
            if (oldUser != null) {
                fldIme.textProperty().unbindBidirectional(oldUser.firstNameProperty() );
                fldPrezime.textProperty().unbindBidirectional(oldUser.lastNameProperty() );
                fldEmail.textProperty().unbindBidirectional(oldUser.emailProperty() );
                fldUsername.textProperty().unbindBidirectional(oldUser.usernameProperty() );
                fldPassword.textProperty().unbindBidirectional(oldUser.passwordProperty() );
            }
            if (newUser == null) {
                fldIme.setText("");
                fldPrezime.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldPassword.setText("");
                cbAdmin.setSelected(false);
            }
            else {
                fldIme.textProperty().bindBidirectional( newUser.firstNameProperty() );
                fldPrezime.textProperty().bindBidirectional( newUser.lastNameProperty() );
                fldEmail.textProperty().bindBidirectional( newUser.emailProperty() );
                fldUsername.textProperty().bindBidirectional( newUser.usernameProperty() );
                fldPassword.textProperty().bindBidirectional( newUser.passwordProperty() );
                if(newUser instanceof Administrator)
                    cbAdmin.setSelected(true);
                else
                    cbAdmin.setSelected(false);
            }
            fldPasswordRepeat.setText(fldPassword.getText());
        });

        fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && validName(fldIme.getText())) {
                fldIme.getStyleClass().removeAll("fieldIncorrect");
                fldIme.getStyleClass().add("fieldCorrect");
            } else {
                fldIme.getStyleClass().removeAll("fieldCorrect");
                fldIme.getStyleClass().add("fieldIncorrect");
            }
        });

        fldPrezime.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && validName(fldPrezime.getText())) {
                fldPrezime.getStyleClass().removeAll("fieldIncorrect");
                fldPrezime.getStyleClass().add("fieldCorrect");
            } else {
                fldPrezime.getStyleClass().removeAll("fieldCorrect");
                fldPrezime.getStyleClass().add("fieldIncorrect");
            }
        });

        fldEmail.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && validEmail(fldEmail.getText())) {
                fldEmail.getStyleClass().removeAll("fieldIncorrect");
                fldEmail.getStyleClass().add("fieldCorrect");
            } else {
                fldEmail.getStyleClass().removeAll("fieldCorrect");
                fldEmail.getStyleClass().add("fieldIncorrect");
            }
        });

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && validUserName(fldUsername.getText())) {
                fldUsername.getStyleClass().removeAll("fieldIncorrect");
                fldUsername.getStyleClass().add("fieldCorrect");
            } else {
                fldUsername.getStyleClass().removeAll("fieldCorrect");
                fldUsername.getStyleClass().add("fieldIncorrect");
            }
        });

        fldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
            if(model.getCurrentUser()==null)
                return;

            if(newIme.equals(fldPasswordRepeat.getText())){
                model.getCurrentUser().setPassword(newIme);
            }

            if (!newIme.isEmpty() && fldPasswordRepeat.getText().equals(fldPassword.getText())
                    && model.getCurrentUser().checkPassword()) {
                passwordColor(true);
            } else {
                passwordColor(false);
            }
        });

        fldPasswordRepeat.textProperty().addListener((obs, oldIme, newIme) -> {
            if(model.getCurrentUser()==null)
                return;
            if (!newIme.isEmpty() && fldPasswordRepeat.getText().equals(fldPassword.getText())
                    && model.getCurrentUser().checkPassword()) {
                passwordColor(true);
            } else {
                passwordColor(false);
            }
        });

    }


    public void dodajAction(ActionEvent actionEvent) {
        model.getUsers().add(new User(0, "", "", "", "", "")); // Porebne prepravke
        listKorisnici.getSelectionModel().selectLast();
    }

    public void krajAction(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void obrisiAction(ActionEvent actionEvent){
        if(model.getCurrentUser()!=null) {
            model.getUsers().remove(model.getCurrentUser());
            model.setCurrentUser(null);
        }
    }

    public void generisiAction(ActionEvent actionEvent){
        if(model.getCurrentUser()==null)
            return;

        // Generate username
        String s = "";
        if(fldIme.getText().length()>0)
            s = s + getCSDZ(Character.toLowerCase(fldIme.getText().charAt(0)));

        String prezime = fldPrezime.getText();
        for(int i=0; i<prezime.length(); i++){
            s = s + getCSDZ(Character.toLowerCase(prezime.charAt(i)));
        }
        fldUsername.setText(s);
        model.getCurrentUser().setUsername(s);

        // Generate password
        int raspon = 127-33;
        // ako nije admin ide do 5 a ako jeste onda 4
        int j=5;
        if(model.getCurrentUser() instanceof Administrator)
            j=4;

        s = "";
        // Generate number, capital i small letter
        s = s + getRandChar(10, '0');
        s = s + getRandChar(26, 'A');
        s = s + getRandChar(26, 'a');
        if(model.getCurrentUser() instanceof Administrator){
            int raspon2 = 127-33-2*26-10;
            char c = getRandChar(raspon2, '!');
            if(Character.isLetterOrDigit(c))
                c += 10;
            if(Character.isLetter(c))
                c +=26;
            if(Character.isLetter(c))
                c +=26;
            s = s + c;
        }


        for(int i=0; i<j; i++){
            s += getRandChar(raspon, '!');
        }


        int rBroj;
        char[] pass = new char[8];
        for(int i=0; i<8; i++)
            pass[i] = ' ';

        for(int i=0; i<8; i++){
            rBroj = (int) getRandChar(8, (char) 0);
            while(pass[rBroj]!=' ')
                rBroj = (rBroj+1)%8;
            pass[rBroj] = s.charAt(i);
        }

        s = "";
        for(int i=0; i<8; i++)
            s = s + pass[i];

        fldPassword.setText(s);
        fldPasswordRepeat.setText(s);
        model.getCurrentUser().setPassword(s);


        // Dialog box for displaying password
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Vaša lozinka glasi:");
        alert.setContentText(s);

        alert.showAndWait();

    }

    public void adminAction(ActionEvent actionEvent){
        if(model.getCurrentUser()==null)
            return;

        if(model.getCurrentUser() instanceof Administrator){
            User a = model.getCurrentUser();
            ObservableList<User> users = model.getUsers();
            int i;
            // Trazimo sada indeks korisnika da bi ga mogli zamijeniti
            for(i=0; i<users.size(); i++){
                if(users.get(i)==a)
                    break;
            }
            users.set(i, new User(0, a.getFirstName(), a.getLastName(), a.getEmail(), a.getUsername(), a.getPassword())); // Potrebne prepravke
            model.setCurrentUser(users.get(i));
            cbAdmin.setSelected(false);

        }else{
            User k = model.getCurrentUser();
            ObservableList<User> users = model.getUsers();
            int i;
            // Trazimo sada indeks korisnika da bi ga mogli zamijeniti
            for(i=0; i<users.size(); i++){
                if(users.get(i)==k)
                    break;
            }
            users.set(i, new Administrator(0, k.getFirstName(), k.getLastName(), k.getEmail(), k.getUsername(), k.getPassword()));// Potrebne prepravke
            model.setCurrentUser(users.get(i));

            cbAdmin.setSelected(true);
        }
    }


    private void passwordColor(boolean correct){
        if(correct) {
            fldPassword.getStyleClass().removeAll("fieldIncorrect");
            fldPassword.getStyleClass().add("fieldCorrect");
            fldPasswordRepeat.getStyleClass().removeAll("fieldIncorrect");
            fldPasswordRepeat.getStyleClass().add("fieldCorrect");
        }
        else{
            fldPassword.getStyleClass().removeAll("fieldCorrect");
            fldPassword.getStyleClass().add("fieldIncorrect");
            fldPasswordRepeat.getStyleClass().removeAll("fieldCorrect");
            fldPasswordRepeat.getStyleClass().add("fieldIncorrect");
        }
    }

    private boolean validName(String s){
        if(s.length()<3)
                return false;
        char c;
        for(int i=0; i<s.length(); i++){
            c = s.charAt(i);
            if(!(Character.isLetter(c) || c==' ' || c=='-'))
                return false;
        }
        return true;
    }

    private boolean validEmail(String s){
        boolean JeLiSlovo = false;
        for(int i=0; i<s.length(); i++){
            if(Character.isLetter(s.charAt(i)))
                JeLiSlovo = true;
            else if(s.charAt(i)=='@') {
                if(!JeLiSlovo)
                    return false;

                i++;
                for(; i<s.length(); i++)
                    if(Character.isLetter(s.charAt(i)))
                        return true;
            }
        }
        return false;
    }

    private boolean validUserName(String s){
        if(s.length()>16 || s.length()==0)
                return false;

        char c = s.charAt(0);
        if(!(Character.isLetter(c) || c=='_' || c=='$'))
            return false;

        for(int i=1; i<s.length(); i++){
            c = s.charAt(i);
            if(!(Character.isLetterOrDigit(c) || c=='$' || c=='_'))
                return false;
        }
        return true;
    }

    private char getCSDZ(char c){
        if(c=='č' || c=='ć')
            return 'c';
        else if(c=='š')
            return 's';
        else if(c=='ž')
            return 'z';
        else if(c=='đ')
            return 'd';

        return c;
    }

    private char getRandChar(int range, char starts){
        return  (char) ((char)(Math.random() * range ) + starts);
    }
}
