package fel.si.semestral.presentation;

import fel.si.semestral.business.BookService;
import fel.si.semestral.business.PublisherService;
import fel.si.semestral.persistence.model.Book;
import fel.si.semestral.persistence.model.Publisher;
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
 * Created by Nikolai Ogoltsov on 12/25/2016.
 */
@ManagedBean
@ViewScoped
public class PublishBookBean {

    @Inject
    private BookService bookService;

    @Inject
    private PublisherService publisherService;

    @Inject
    private Logger logger;

    private Long selectedBookIsbn;
    private String selectedPublisherName;

    public List<Book> getBookList() {
        logger.info("Querying service for book list.");
        return bookService.list();
    }

    public List<Publisher> getPublisherList() {
        logger.info("Querying service for publisher list.");
        return publisherService.list();
    }

    public void publishBook() {
        if (selectedBookIsbn == null || selectedPublisherName == null) {
            FacesContext.getCurrentInstance().addMessage("publishForm:confirmButton",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "You should select both book and publisher", null)
            );
            return;
        }
        try {
            logger.info("Querying service for publish book.");
            publisherService.publishBook(selectedPublisherName, selectedBookIsbn);
        } catch (EJBException ex) {
            BookSystemException bse = (BookSystemException) ex.getCausedByException();
            FacesContext.getCurrentInstance().addMessage("publishForm:confirmButton",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, bse.getMessage(), null)
            );
        }
        selectedBookIsbn = null;
        selectedPublisherName = null;
        FacesContext.getCurrentInstance().addMessage("publishForm:confirmButton",
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Book was successfully published.", null));
    }

    public Long getSelectedBookIsbn() {
        return selectedBookIsbn;
    }

    public void setSelectedBookIsbn(Long selectedBookIsbn) {
        this.selectedBookIsbn = selectedBookIsbn;
    }

    public String getSelectedPublisherName() {
        return selectedPublisherName;
    }

    public void setSelectedPublisherName(String selectedPublisherName) {
        this.selectedPublisherName = selectedPublisherName;
    }
}
