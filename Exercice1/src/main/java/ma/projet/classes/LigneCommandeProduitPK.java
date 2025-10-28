package ma.projet.classes;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LigneCommandeProduitPK implements Serializable {
    private int commandeId;
    private int produitId;

    // Constructeurs, equals et hashCode
    public LigneCommandeProduitPK() {}

    public LigneCommandeProduitPK(int commandeId, int produitId) {
        this.commandeId = commandeId;
        this.produitId = produitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LigneCommandeProduitPK that = (LigneCommandeProduitPK) o;
        return commandeId == that.commandeId && produitId == that.produitId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandeId, produitId);
    }

    public void setCommandeId(int i) {
        this.commandeId = i;
    }

    public void setProduitId(int i) {
        this.produitId = i;
    }
}