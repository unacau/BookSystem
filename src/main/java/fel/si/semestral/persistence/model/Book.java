package fel.si.semestral.persistence.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by nogoltsov on 24.10.2016.
 */
@Entity
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = "isbn"))
public class Book implements Serializable {

    @Id @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Range(min = 0)
    private long isbn;
    @NotNull
    private Date publishDate;
    @NotNull @Size(max = 256)
    private String title;
    //    TODO: change to lazy (add DTO objects etc...)
    @ManyToMany(mappedBy = "books")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Author> authors;
    //    TODO: change to lazy (add DTO objects etc...)
    @ManyToMany(mappedBy = "books")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Library> libraries;
    @ManyToOne
    private Publisher publisher;

    public Book() {
    }

    public Book(long isbn, Date publishDate, String title) {
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.title = title;
    }

    public Book(long isbn, Date publishDate, String title, List<Author> authors) {
        this(isbn, publishDate, title);
        this.authors = authors;
    }

    public Book(long isbn, Date publishDate, String title, List<Author> authors, Publisher publisher) {
        this(isbn, publishDate, title, authors);
        this.publisher = publisher;
    }

    public void addLibrary(Library library) {
        if (libraries == null) {
            libraries = new ArrayList<>();
        }
        libraries.add(library);
    }

    public void addAuthor(Author author) {
        if (authors == null) {
            authors = new ArrayList<>();
        }
        authors.add(author);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Library> getLibraries() {
        if (libraries == null) {
            return Collections.emptyList();
        }
        return libraries;
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
