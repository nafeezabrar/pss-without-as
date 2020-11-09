package pss.mapping.ooi_user_group;

import pss.data.ooi.Ooi;
import pss.data.ooi.combination.OoiCombination;
import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.exception.PssException;

import java.util.Set;

public interface OoiUserGroupMappable<TOoiCombination extends OoiCombination, TLocalOoiCombination extends LocalOoiCombination, TPssGroup extends PssGroup> {
    TOoiCombination getOoiCombination(TLocalOoiCombination localOoiCombination, User user) throws PssException;

    TLocalOoiCombination getLocalOoiCombination(TOoiCombination ooiCombination) throws PssException;

    TOoiCombination getOoiCombination(TLocalOoiCombination localOoiCombination, TPssGroup pssGroup);

    TLocalOoiCombination getLocalOoiCombination(TOoiCombination ooiCombination, TPssGroup pssGroup);

    TPssGroup getPssGroup(User user) throws PssException;

    TPssGroup getPssGroup(int userId) throws PssException;

    TPssGroup getPssGroup(TOoiCombination ooiCombination) throws PssException;

    Set<User> getUsers(TPssGroup pssGroup) throws PssException;

    User getUser(int userId) throws PssException;

    Ooi getOoi(int localOoiId, User User) throws PssException;

    Ooi getOoi(int localOoiId, int userId) throws PssException;

}
