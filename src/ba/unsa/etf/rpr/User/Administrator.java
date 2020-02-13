package ba.unsa.etf.rpr.User;

public class Administrator extends User {
    public Administrator(int id, String firstName, String lastName, String email, String username, String password) {
        super(id, firstName, lastName, email, username, password);
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
