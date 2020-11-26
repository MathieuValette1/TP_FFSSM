package FFSSM;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.time.LocalDate;
import java.time.Month;

/**
 *
 * @author mathi
 */
public class FFSSMTest {
    private final LocalDate naissance = LocalDate.of(1968, 01, 25); // Tout le monde sera né le même jour
    
    private Personne jean;
    
    private Plongeur jon;
    
    private final LocalDate delivrance = LocalDate.of(2010, 01, 25);
    private Licence licenceJon;
    
    private Moniteur albert;
    
    private Club fcPlouf;
    
    private Site palavas;
    
    private Plongee plongee;
    private final LocalDate datePlongee = LocalDate.of(2020, 01, 25);
    @BeforeEach
    public void Setup() throws Exception{
        jean = new Personne("007", "Dupond", "Jean", "2 rue de l'église", "0656263212", naissance );
        jean.setNom("Dupond");
        jean.setPrenom("Jean");
        jean.setAdresse("2 rue de l'église");
        jean.setNaissance(naissance);
        jean.setTelephone("0656263212");
        
        jon = new Plongeur("008", "Bridge", "Jon", "2 rue de la paix", "0656263212", naissance, '3' );
        
        albert = new Moniteur("009", "Camus", "Albert", "2 rue de la paix", "0656263212", naissance, '3', 2);
        
        fcPlouf = new Club(albert, "FcPlouf", "0656263212");
        
        licenceJon = new Licence(jon, "888", delivrance, fcPlouf);
        
        palavas = new Site("Palavas", "Très joli");
        palavas.setNom("Palavas");
        palavas.setDetails("Très joli");
        
        plongee = new Plongee(palavas, albert, datePlongee, 20, 60);
        
        
    }
    
    //Test classe Personne
    @Test
    public void testNumInsee(){
        assertEquals("007", jean.getNumeroINSEE(), "Le numéro n'est pas le bon");
    }
    
    @Test
    public void testGetInfo(){
        assertEquals("Dupond", jean.getNom(), "Le nom n'est pas le bon");
        assertEquals("Jean", jean.getPrenom(), "Le prénom n'est pas le bon");
        assertEquals("2 rue de l'église", jean.getAdresse(), "L'adresse n'est pas la bonne");
        assertEquals("0656263212", jean.getTelephone(), "Le numéro de téléphone n'est pas le bon");
        assertEquals(naissance, jean.getNaissance(), "La date de naissance n'est pas la bonne");
    }
    
    @Test 
    public void testNumInseeNul() throws Exception{
        try{
            jean.setNumeroINSEE(null);
            fail();
        }
        catch (Exception e){
            //Si on arrive ici c'est que tout va bien
        }
        
    }
    
    //Test classe Plongeur
    @Test
    public void testAjouteLicence(){
        jon.ajouteLicense("888", delivrance, fcPlouf);
        assertFalse(jon.getLicence() == null);
        }
    
    @Test
    public void testNiveau(){
        assertEquals('3', jon.getNiveau(), "Le niveau n'est pas le bon");
    }
    
    //Test classe Site
    @Test
    public void testInfo(){
        assertEquals("Palavas", palavas.getNom(), "Le nom n'est pas bon");
        assertEquals("Très joli", palavas.getDetails(), "Les détails ne sont pas bons");
    }
    
    @Test
    public void testToString(){
        assertEquals("Site{nom=Palavas, details=Très joli}", palavas.toString(), "La conversion n'est pas faite");
    }
    
    //Test classe plongee
    @Test
    public void plongeeVide(){
        assertFalse(plongee.estConforme());
    }
    
    @Test
    public void plongeeNonConforme(){
        //jon n'a pas une licence valide
        jon.ajouteLicense("888", delivrance, fcPlouf);
        plongee.ajouteParticipant(jon);
        assertFalse(plongee.estConforme(), "La plongée n'est pas conforme");
    }
    
    @Test
    public void plongeeConforme(){
        jon.ajouteLicense("000", LocalDate.of(2020, 01, 20), fcPlouf);
        plongee.ajouteParticipant(jon);
        assertTrue(plongee.estConforme(), "La plongee est conforme");
    }
}

