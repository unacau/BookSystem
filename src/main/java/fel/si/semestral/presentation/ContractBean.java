package fel.si.semestral.presentation;

import fel.si.semestral.business.AuthorService;
import fel.si.semestral.business.PublisherService;
import fel.si.semestral.persistence.model.Author;
import fel.si.semestral.persistence.model.Publisher;
import fel.si.semestral.util.exception.BookSystemException;
import org.slf4j.Logger;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Nikolai Ogoltsov on 12/24/2016.
 */
@ManagedBean
@ViewScoped
public class ContractBean {

    @Inject
    private AuthorService authorService;

    @Inject
    private PublisherService publisherService;

    @Inject
    private Logger logger;

    private String selectedAuthorEmail;
    private String selectedPublisherName;

    public List<Author> getAuthorList() {
        logger.info("Querying service for author list.");
        return authorService.list();
    }

    public List<Publisher> getPublisherList() {
        logger.info("Querying service for publisher list.");
        return publisherService.list();
    }


    public void contract() {
        if (selectedAuthorEmail == null || selectedPublisherName == null) {
            FacesContext.getCurrentInstance().addMessage("contractForm:confirmButton",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "You should select both author and publisher", null)
            );
            return;
        }

        try {
            logger.info("Querying service for contract publisher and author.");
            authorService.contractWithPublisher(selectedAuthorEmail, selectedPublisherName);
        } catch (EJBException ex) {
            BookSystemException bse = (BookSystemException) ex.getCausedByException();
            FacesContext.getCurrentInstance().addMessage("contractForm:confirmButton",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, bse.getMessage(), null)
            );
        }
        FacesContext.getCurrentInstance().addMessage("contractForm:confirmButton",
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Contract was successfully registered.", null));
        selectedAuthorEmail = selectedPublisherName = null;
    }

    public String getSelectedAuthorEmail() {
        return selectedAuthorEmail;
    }

    public void setSelectedAuthorEmail(String selectedAuthorEmail) {
        this.selectedAuthorEmail = selectedAuthorEmail;
    }

    public String getSelectedPublisherName() {
        return selectedPublisherName;
    }

    public void setSelectedPublisherName(String selectedPublisherName) {
        this.selectedPublisherName = selectedPublisherName;
    }
}
