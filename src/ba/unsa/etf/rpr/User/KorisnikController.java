package ba.unsa.etf.rpr.User;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;


public class KorisnikController {
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldEmail;
    public TextField fldUsername;
    public ListView<User> listKorisnici;
    public PasswordField fldPassword;
    public PasswordField fldPasswordRepeat;
    public Slider sliderGodinaRodjenja;
    public CheckBox cbAdmin;
    public Tooltip sldToolTip = new Tooltip();

    private UsersModel model;

    public KorisnikController(UsersModel model) { this.model = model; }

    @FXML
    public void initialize() {
        listKorisnici.setItems(model.getUsers());
        sliderGodinaRodjenja.setTooltip(sldToolTip);
        sldToolTip.setShowDelay(Duration.millis(10));

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
                sliderGodinaRodjenja.valueProperty().unbindBidirectional(oldUser.godinaRodjenjaProperty());
                sldToolTip.setText("2000");
            }
            if (newUser == null) {
                fldIme.setText("");
                fldPrezime.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldPassword.setText("");
                sliderGodinaRodjenja.setValue(2000);
                cbAdmin.setSelected(false);
                sldToolTip.setText("2000");
            }
            else {
                fldIme.textProperty().bindBidirectional( newUser.firstNameProperty() );
                fldPrezime.textProperty().bindBidirectional( newUser.lastNameProperty() );
                fldEmail.textProperty().bindBidirectional( newUser.emailProperty() );
                fldUsername.textProperty().bindBidirectional( newUser.usernameProperty() );
                fldPassword.textProperty().bindBidirectional( newUser.passwordProperty() );
                sliderGodinaRodjenja.valueProperty().bindBidirectional(newUser.godinaRodjenjaProperty());
                sldToolTip.setText(Integer.toString(newUser.getGodinaRodjenja()));
                if(newUser instanceof Administrator)
                    cbAdmin.setSelected(true);
                else
                    cbAdmin.setSelected(false);
            }
            fldPasswordRepeat.setText(fldPassword.getText());
        });

        fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && validName(fldIme.getText())) {
                fldIme.getStyleClass().removeAll("poljeNijeIspravno");
                fldIme.getStyleClass().add("poljeIspravno");
            } else {
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPrezime.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && validName(fldPrezime.getText())) {
                fldPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
            } else {
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldEmail.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && validEmail(fldEmail.getText())) {
                fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fldEmail.getStyleClass().add("poljeIspravno");
            } else {
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && validUserName(fldUsername.getText())) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
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

        sliderGodinaRodjenja.valueProperty().addListener((obs, oldGod, newGod) -> {
            newGod = newGod.intValue();
            sldToolTip.setText(newGod.toString());
        });

    }


    public void dodajAction(ActionEvent actionEvent) {
        model.getUsers().add(new User("", "", "", "", ""));
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

        // Generisi username
        String s = "";
        if(fldIme.getText().length()>0)
            s = s + getCSDZ(Character.toLowerCase(fldIme.getText().charAt(0)));

        String prezime = fldPrezime.getText();
        for(int i=0; i<prezime.length(); i++){
            s = s + getCSDZ(Character.toLowerCase(prezime.charAt(i)));
        }
        fldUsername.setText(s);
        model.getCurrentUser().setUsername(s);

        // Generisi password
        int raspon = 127-33;
        // ako nije admin ide do 5 a ako jeste onda 4
        int j=5;
        if(model.getCurrentUser() instanceof Administrator)
            j=4;

        s = "";
        // Generisi broj, veliko i malo slovo
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


        // Dijaloski prozor za prikaz sifre
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
            ObservableList<User> korisnici = model.getUsers();
            int i;
            // Trazimo sada indeks korisnika da bi ga mogli zamijeniti
            for(i=0; i<korisnici.size(); i++){
                if(korisnici.get(i)==a)
                    break;
            }
            korisnici.set(i, new User(a.getFirstName(), a.getLastName(), a.getEmail(), a.getUsername(), a.getPassword()));
            korisnici.get(i).setGodinaRodjenja(a.getGodinaRodjenja());
            model.setCurrentUser(korisnici.get(i));
            cbAdmin.setSelected(false);

        }else{
            User k = model.getCurrentUser();
            ObservableList<User> korisnici = model.getUsers();
            int i;
            // Trazimo sada indeks korisnika da bi ga mogli zamijeniti
            for(i=0; i<korisnici.size(); i++){
                if(korisnici.get(i)==k)
                    break;
            }
            korisnici.set(i, new Administrator(k.getFirstName(), k.getLastName(), k.getEmail(), k.getUsername(), k.getPassword()));
            korisnici.get(i).setGodinaRodjenja(k.getGodinaRodjenja());
            model.setCurrentUser(korisnici.get(i));

            cbAdmin.setSelected(true);
        }
    }


    private void passwordColor(boolean ispravno){
        if(ispravno) {
            fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
            fldPassword.getStyleClass().add("poljeIspravno");
            fldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
            fldPasswordRepeat.getStyleClass().add("poljeIspravno");
        }
        else{
            fldPassword.getStyleClass().removeAll("poljeIspravno");
            fldPassword.getStyleClass().add("poljeNijeIspravno");
            fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
            fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
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

    private char getRandChar(int raspon, char pocinje){
        return  (char) ((char)(Math.random() * raspon ) + pocinje);
    }
}
