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
    private Licence licenceValide;
    private Licence licenceInvalide;
    
    private Moniteur albert;
    
    private Club fcPlouf;
        
    private Site palavas;
    
    private Plongee plongee;
    private final LocalDate datePlongee = LocalDate.of(2020, 01, 25);
    
    private Embauche embaucheAlbert;
    private final LocalDate dateEmbauche = LocalDate.of(2020, 01, 01);
    
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
        fcPlouf.setAdresse("2 rue de la paix");
        fcPlouf.setNom("FcPlouf");
        fcPlouf.setPresident(albert);
        fcPlouf.setTelephone("0656263212");
        
        licenceJon = new Licence(jon, "888", delivrance, fcPlouf);
        licenceValide = new Licence(jon, "888", datePlongee, fcPlouf);
        licenceInvalide = new Licence(jon, "888", datePlongee.plusDays(2), fcPlouf);
        
        palavas = new Site("Palavas", "Très joli");
        palavas.setNom("Palavas");
        palavas.setDetails("Très joli");
        
        plongee = new Plongee(palavas, albert, datePlongee, 20, 60);
        
        embaucheAlbert = new Embauche(dateEmbauche, albert, fcPlouf);
        
        
        
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
    
    @Test
    public void testDate(){
        assertEquals(LocalDate.of(2020, 01, 25), plongee.getDate());
    }
    
    // Test classe Licence
    
    @Test
    public void testLicenceInfo(){
        assertEquals(jon, licenceJon.getPossesseur(), "Le possesseur n'est pas le bon");
        assertEquals("888", licenceJon.getNumero(), "Le numéro n'est pas bon");
        assertEquals(delivrance, licenceJon.getDelivrance(), "La date n'est pas bonne");
        assertEquals(fcPlouf, licenceJon.getClub(), "Le club n'est pas bon");        
    }
    
    @Test
    public void testNEstPasValide1(){
        //Jon n'a pas de licence valide à la date de la plongee
        assertFalse(licenceJon.estValide(datePlongee));
    }
    
     @Test
    public void testNEstPasValide2(){
        
        assertFalse(licenceInvalide.estValide(datePlongee));
        
    }
    
    @Test
    public void testestValide(){
        assertTrue(licenceValide.estValide(datePlongee));
    }
    
    //Tests classe Club
    @Test
    public void testInfoClub(){
        assertEquals("FcPlouf", fcPlouf.getNom());
        assertEquals(albert, fcPlouf.getPresident());
        assertEquals("0656263212", fcPlouf.getTelephone());
        assertEquals("2 rue de la paix", fcPlouf.getAdresse());
    }
    
    
    @Test
    public void testPlongeeNonConforme(){
        //Jon n'a pas de licence conforme
        jon.ajouteLicense("888", delivrance, fcPlouf);
        plongee.ajouteParticipant(jon);
        fcPlouf.plongees.add(plongee);
        fcPlouf.organisePlongee(plongee);
        assertTrue(fcPlouf.plongeesNonConformes().contains(plongee));
    }
    
    @Test
    public void testPlongeeConforme(){
        //Jon n'a pas de licence conforme
        jon.ajouteLicense("000", LocalDate.of(2020, 01, 20), fcPlouf);
        plongee.ajouteParticipant(jon);
        fcPlouf.plongees.add(plongee);
        fcPlouf.organisePlongee(plongee);
        assertFalse(fcPlouf.plongeesNonConformes().contains(plongee));
    }
        
    @Test
    public void testOrganisePlongee(){
        fcPlouf.organisePlongee(plongee);
        assertTrue(fcPlouf.plongees.contains(plongee), "La plongee n'a pas été ajouté");
    }
    
    @Test
    public void testClubToString(){
        assertEquals("Club{président=Camus, nom=FcPlouf, adresse=2 rue de la paix, telephone=0656263212}", fcPlouf.toString(), "La conversion n'est pas faite");
    }   
    
    
    //Test classe embauche
    
    @Test
    public void testEmabaucheInfo(){
        assertEquals(albert, embaucheAlbert.getEmploye(), "L'employé n'est pas le bon");
        assertEquals(fcPlouf, embaucheAlbert.getEmployeur(), "Le club n'est pas le bon");
        assertEquals(dateEmbauche, embaucheAlbert.getDebut(), "La date de début n'est pas la bonne");
                    
    }
    
    @Test
    public void testEmbaucheTerminé(){
        assertFalse(embaucheAlbert.estTerminee(), "L'embauche n'est pas terminée"); //L'embauche n'est pas terminée tant qu'on a pas défini de date de fin
        embaucheAlbert.terminer(datePlongee);
        assertEquals(null, embaucheAlbert.getFin(), "L'embauche ne peut pas être terminée");
        embaucheAlbert.setFin(datePlongee);
        assertEquals(datePlongee, embaucheAlbert.getFin(), "La date de fin n'est pas la bonne");
        embaucheAlbert.terminer(datePlongee);
        assertEquals(LocalDate.now(), embaucheAlbert.getFin(), "La date de fin n'est pas la bonne");
    }
    
    //Test classe Moniteur
    
    @Test
    public void testEmploisEtEmbauche(){
        
        albert.nouvelleEmbauche(fcPlouf, datePlongee);
        assertFalse(fcPlouf.embauches.isEmpty(), "La liste ne peut être vide puisqu'on a ajouté une embauche");
        assertFalse(albert.emplois().isEmpty(), "La liste ne peut être vide puisqu'on a ajouté une embauche");
        assertEquals(fcPlouf, albert.employeurActuel().get(), "L'employeur n'est pas le bon");
    }
}

