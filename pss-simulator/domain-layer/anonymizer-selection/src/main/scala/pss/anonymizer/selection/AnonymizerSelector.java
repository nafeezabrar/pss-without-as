package pss.anonymizer.selection;

import pss.anonymization.Anonymizable;
import pss.data.user.User;
import pss.exception.PssException;

public interface AnonymizerSelector {
    Anonymizable chooseAnonymizer(User user) throws PssException;
}
