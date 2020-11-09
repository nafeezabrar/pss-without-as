package pss.deanonymizer.selection;

import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.deanonymization.Deanonymizable;
import pss.exception.PssException;

public interface DeanonymizerSelector {
    Deanonymizable selectDeanonymizer(User user) throws PssException;

    Deanonymizable selectDeanonymizer(PssGroup pssGroup) throws PssException;
}
