package it.tylconsulting.vega.util;

public class TylIdentifier {
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TylIdentifier{" +
            "id=" + id +
            '}';
    }
}
