package pss.mapping.ooi_user_group;

import pss.data.dimension.Dimension;
import pss.data.ooi.Ooi;
import pss.data.ooi.combination.MultiOoiCombination;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.pssvariable.group.MultiPssGroup;
import pss.data.user.User;
import pss.exception.PssException;


public interface MultiOoiUserGroupMappable extends OoiUserGroupMappable<MultiOoiCombination, MultiLocalOoiCombination, MultiPssGroup> {
    Ooi getOoi(Dimension dimension, int localOoiId, User user) throws PssException;

    Ooi getOoi(Dimension dimension, int localOoiId, int User) throws PssException;
}
