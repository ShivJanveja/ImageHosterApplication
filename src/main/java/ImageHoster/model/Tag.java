package ImageHoster.model;

import javax.persistence.*;
import java.util.List;


@Entity

@Table(name = "Tags")
public class Tag {


    @Id

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    private List<Image> images;

    public Tag() {
    }

    public Tag(String tagName) {
        this.name = tagName;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}