package fel.si.semestral.persistence.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nogoltsov on 24.10.2016.
 */
@Entity
@Table(name = "publishers", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Publisher implements Serializable {

    @Id
    @Column(name = "publisher_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min = 3, max = 64)
    private String name;
    @Size(min = 3, max = 256)
    private String address;
    //    TODO: change to lazy (add DTO objects etc...)
    @ManyToMany(mappedBy = "publishers", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Author> authors;
    //    TODO: change to lazy (add DTO objects etc...)
    @OneToMany(mappedBy = "publisher")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Book> books;

    public Publisher() {
    }

    public Publisher(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void addAuthor(Author author) {
        if (authors == null) {
            authors = new ArrayList<>();
        }
        authors.add(author);
    }

    public void addBook(Book book) {
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(book);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Author> getAuthors() {
        if (authors == null) {
            return Collections.emptyList();
        }
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Book> getBooks() {
        if (books == null) {
            return Collections.emptyList();
        }
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
