package fel.si.semestral.persistence.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nogoltsov on 24.10.2016.
 */
@Entity
@Table(name = "authors", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Author implements Serializable {

    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 64)
    private String name;
    @Size(min = 3, max = 64)
    private String surname;
    @Email
    @Column(unique = true)
    private String email;

    //    TODO: change to lazy (add DTO objects etc...)
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "author_book",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;
//    TODO: change to lazy (add DTO objects etc...)
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "author_publisher",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id"))
    private List<Publisher> publishers;

    public Author() {
    }

    public Author(String name, String surname, String email) {
        this();
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Author(String name, String surname, String email, List<Book> books) {
        this(name, surname, email);
        this.books = books;
    }

    public void addPublisher(Publisher publisher) {
        if (publishers == null) {
            publishers = new ArrayList<>();
        }
        publishers.add(publisher);
    }

    public void addBook(Book book) {
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(book);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Publisher> getPublishers() {
        if (publishers == null) {
            return Collections.emptyList();
        }
        return publishers;
    }

    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (!name.equals(author.name)) return false;
        if (!surname.equals(author.surname)) return false;
        return email.equals(author.email);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
