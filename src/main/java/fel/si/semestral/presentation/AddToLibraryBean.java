package fel.si.semestral.presentation;

import fel.si.semestral.business.BookService;
import fel.si.semestral.business.LibraryService;
import fel.si.semestral.persistence.model.Book;
import fel.si.semestral.persistence.model.Library;
import fel.si.semestral.util.exception.BookSystemException;
import org.slf4j.Logger;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Nikolai Ogoltsov on 12/26/2016.
 */
@ManagedBean
@ViewScoped
public class AddToLibraryBean {

    @Inject
    private BookService bookService;

    @Inject
    private LibraryService libraryService;

    @Inject
    private Logger logger;

    private Long selectedBookIsbn;
    private String selectedLibraryName;

    public List<Book> getBookList() {
        logger.info("Querying service for book list.");
        return bookService.list();
    }

    public List<Library> getLibraryList() {
        logger.info("Querying service for library list.");
        return libraryService.list();
    }

    public void addBookToLibrary() {
        if (selectedBookIsbn == null || selectedLibraryName == null) {
            FacesContext.getCurrentInstance().addMessage("addBookForm:confirmButton",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "You should select both book and library", null)
            );
            return;
        }

        try {
            logger.info("Querying service for add book to library.");
            bookService.addBookToLibrary(selectedBookIsbn, selectedLibraryName);
        } catch (EJBException ex) {
            BookSystemException bse = (BookSystemException) ex.getCausedByException();
            FacesContext.getCurrentInstance().addMessage("addBookForm:confirmButton",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, bse.getMessage(), null)
            );
        }
        FacesContext.getCurrentInstance().addMessage("addBookForm:confirmButton",
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Contract was successfully registered.", null));
        selectedLibraryName = null;
        selectedBookIsbn = null;
    }

    public Long getSelectedBookIsbn() {
        return selectedBookIsbn;
    }

    public void setSelectedBookIsbn(Long selectedBookIsbn) {
        this.selectedBookIsbn = selectedBookIsbn;
    }

    public String getSelectedLibraryName() {
        return selectedLibraryName;
    }

    public void setSelectedLibraryName(String selectedLibraryName) {
        this.selectedLibraryName = selectedLibraryName;
    }
}
