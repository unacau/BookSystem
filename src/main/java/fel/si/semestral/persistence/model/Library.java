package fel.si.semestral.persistence.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nogoltsov on 24.10.2016.
 */
@Entity
@Table(name = "libraries", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Library implements Serializable {

    @Id
    @Column(name = "library_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min = 3, max = 64)
    private String name;
    @Size(min = 3, max = 256)
    private String address;
    //    TODO: change to lazy (add DTO objects etc...)
    @ManyToMany
    @JoinTable(name = "library_book",
            joinColumns = @JoinColumn(name = "library_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Book> books;

    public Library() {
    }

    public Library(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Library(String name, String address, List<Book> books) {
        this(name, address);
        this.books = books;
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
