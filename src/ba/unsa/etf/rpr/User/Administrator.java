package ba.unsa.etf.rpr.User;

public class Administrator extends User {
    public Administrator(String ime, String prezime, String email, String username, String password) {
        super(ime, prezime, email, username, password);
    }

    @Override
    public boolean checkPassword() {
        if(!super.checkPassword())
            return false;
        String s = getPassword();
        char c;
        for(int i=0; i<s.length(); i++){
            c = s.charAt(i);
            if(!Character.isLetterOrDigit(c))
                return true;
        }
        return false;
    }
}
