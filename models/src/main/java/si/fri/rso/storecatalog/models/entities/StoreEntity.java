package si.fri.rso.storecatalog.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "stores")
@NamedQueries(value = {@NamedQuery(name = "StoreEntity.getAll", query = "SELECT stores FROM StoreEntity stores")})

public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
