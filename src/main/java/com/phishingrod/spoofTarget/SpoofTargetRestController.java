package com.phishingrod.spoofTarget;

import com.phishingrod.api.EntityRestController;
import com.phishingrod.api.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spoofTarget")
public class SpoofTargetRestController extends EntityRestController<SpoofTarget, SpoofTargetService>
{
    @Autowired
    public SpoofTargetRestController(SpoofTargetService service)
    {
        super(service);
    }

    @Override
    public void validateAdd(SpoofTarget toAdd)
    {
        String emailAddress = toAdd.getEmailAddress();
        if (emailAddress == null || emailAddress.trim().isEmpty())
            throw new ValidationException("required emailAddress is missing or empty", "Missing");
        else if (service.exist(emailAddress))
        {
            throw new ValidationException("spoof Target with the same email Address already exist on the database", "Conflict");
        }
    }

    @Override
    public void validateGet(long id)
    {
        checkExist(id);
    }

    @Override
    public void validateDelete(long id)
    {
        checkExist(id);
    }

    @Override
    public void validateModify(SpoofTarget toModify)
    {
        checkExist(toModify.getId());

        String emailAddress = toModify.getEmailAddress();
        if (emailAddress == null)
        {
            if (toModify.getParameterMap() == null)
            {
                throw new ValidationException("Either emailAddress and/or parameters should be supplied in the request body", "Missing");
            }
        } else if (emailAddress.trim().isEmpty())
        {
            throw new ValidationException("The emailAddress field is empty.", "Blank Email Address");
        } else if (service.exist(emailAddress))
        {
            SpoofTarget old = service.find(emailAddress);
            if (old != null && old.getId() != toModify.getId())
            {
                throw new ValidationException("The email address: " + emailAddress + " is associated with another entity", "Concflict");
            }
        }
    }

    private void checkExist(long id)
    {
        if (!service.exist(id))
        {
            throw new ValidationException("No phishing target exist with Id: " + id, "Not found");
        }
    }

}
