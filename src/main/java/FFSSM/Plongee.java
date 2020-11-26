/**
 * @(#) Plongee.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.*;

public class Plongee {

	public Site lieu;

	public Moniteur chefDePalanquee;

	public LocalDate date;

	public int profondeur;

	public int duree;
        
        public Set<Plongeur> participants = new HashSet<>();
        
        

	public Plongee(Site lieu, Moniteur chefDePalanquee, LocalDate date, int profondeur, int duree) {
		this.lieu = lieu;
		this.chefDePalanquee = chefDePalanquee;
		this.date = date;
		this.profondeur = profondeur;
		this.duree = duree;
	}

	public void ajouteParticipant(Plongeur participant) {
		participants.add(participant);
	}

	public LocalDate getDate() {
		return date;
	}

	/**
	 * Détermine si la plongée est conforme. 
	 * Une plongée est conforme si tous les plongeurs de la palanquée ont une
	 * licence valide à la date de la plongée
	 * @return vrai si la plongée est conforme
	 */
	public boolean estConforme() {
            if (!participants.isEmpty()){
		boolean conforme = true;
		
                Iterator<Plongeur> participantsIterator = participants.iterator();
                while (participantsIterator.hasNext()){
                    if (!participantsIterator.next().licence.estValide(date)){
                        conforme = false;
                        return conforme;
                    }
                }
                return conforme;
            }
            return false;
	}

}
