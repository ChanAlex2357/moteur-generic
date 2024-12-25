package mg.jca.gfja.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class Utilitaire {
    /**
     * Retourne la valeur courante de la séquence
     *
     * @param nomProcedure
     * @param c
     * @return
     * @throws Exception
     */
    public static int getMaxSeq(String nomProcedure, Connection c) throws Exception {
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            cs = c.prepareCall(String.valueOf(String.valueOf((new StringBuffer("select ")).append(nomProcedure).append("()"))));
            rs = cs.executeQuery();
            rs.next();
            int i = rs.getInt(1);
            return i;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
        }
    }

    /**
     * Compléter un entier (ex: 67, 4 devient 0067)
     *
     * @param longuerChaine
     * @param nombre
     * @return
     */
    public static String completerInt(int longuerChaine, int nombre) {
        String zero = null;
        zero = "";
        for (int i = 0; i < longuerChaine - String.valueOf(nombre).length(); i++) {
            zero = String.valueOf(String.valueOf(zero)).concat("0");
        }

        return String.valueOf(zero) + String.valueOf(String.valueOf(nombre));
    }

    public static String completerInt(int longuerChaine, String nombre2) {
        int nombre = stringToInt(nombre2);
        return completerInt(longuerChaine, nombre);
    }

    private static int stringToInt(String nombre2) {
        int i;
        try {
            i = Integer.parseInt(nombre2);
        } catch (NumberFormatException ne) {
            i = 0;
        }
        return i;
    }
}
